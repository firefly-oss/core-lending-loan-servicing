/*
 * Copyright 2025 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package io.fireflysoftware.core.lending.servicing.core.schedule;

import io.fireflysoftware.core.lending.servicing.interfaces.dtos.LoanRepaymentScheduleDTO;
import io.fireflysoftware.core.lending.servicing.interfaces.dtos.RestoreScheduleRequest;
import io.fireflysoftware.core.lending.servicing.interfaces.dtos.ScheduleRevisionDTO;
import io.fireflysoftware.core.lending.servicing.interfaces.dtos.StoreAmortizationCalendarRequest;
import io.fireflysoftware.core.lending.servicing.interfaces.enums.ScheduleStatus;
import io.fireflysoftware.core.lending.servicing.models.entities.LoanRepaymentSchedule;
import io.fireflysoftware.core.lending.servicing.models.repositories.LoanRepaymentScheduleRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Stores and reads the amortization calendar on the existing repayment-schedule table.
 * A restructure keeps the previous rows on the same servicing case.
 */
@Service
public class AmortizationCalendarService {

    private final LoanRepaymentScheduleRepository schedules;

    public AmortizationCalendarService(LoanRepaymentScheduleRepository schedules) {
        this.schedules = schedules;
    }

    public Mono<List<LoanRepaymentScheduleDTO>> store(UUID caseId, StoreAmortizationCalendarRequest request) {
        List<AmortizationCalendarCalculator.Installment> rows = AmortizationCalendarCalculator.generate(
                request.getPrincipal(),
                request.getAnnualRatePercent(),
                request.getTerm(),
                request.getPaymentFrequency(),
                request.getAmortizationMethod(),
                request.getFirstDueDate(),
                request.getInstallmentAmount());
        return Flux.fromIterable(rows)
                .concatMap(row -> schedules.save(newRow(caseId, row)))
                .map(this::toDto)
                .collectList();
    }

    public Mono<List<LoanRepaymentScheduleDTO>> read(UUID caseId, boolean includeSuperseded) {
        return schedules.findByLoanServicingCaseId(caseId)
                .filter(row -> includeSuperseded || row.getScheduleStatus() != ScheduleStatus.SUPERSEDED)
                .sort(Comparator.comparing(LoanRepaymentSchedule::getInstallmentNumber, Comparator.nullsLast(Integer::compareTo)))
                .map(this::toDto)
                .collectList();
    }

    public Mono<ScheduleRevisionDTO> replace(UUID caseId, StoreAmortizationCalendarRequest request) {
        return schedules.findByLoanServicingCaseId(caseId)
                .collectList()
                .flatMap(existing -> {
                    List<LoanRepaymentSchedule> previous = existing.stream()
                            .filter(row -> row.getScheduleStatus() != ScheduleStatus.SUPERSEDED)
                            .filter(row -> !Boolean.TRUE.equals(row.getIsPaid()))
                            .toList();
                    return Flux.fromIterable(previous)
                            .concatMap(row -> {
                                row.setScheduleStatus(ScheduleStatus.SUPERSEDED);
                                row.setUpdatedAt(LocalDateTime.now());
                                return schedules.save(row);
                            })
                            .collectList()
                            .flatMap(kept -> store(caseId, request)
                                    .map(current -> new ScheduleRevisionDTO(caseId, kept.stream().map(this::toDto).toList(), current)));
                });
    }

    public Mono<List<LoanRepaymentScheduleDTO>> restore(UUID caseId, RestoreScheduleRequest request) {
        Mono<Void> removeReplacement = Flux.fromIterable(request.getReplacementScheduleIds())
                .concatMap(id -> schedules.findById(id)
                        .filter(row -> caseId.equals(row.getLoanServicingCaseId()))
                        .flatMap(row -> schedules.deleteById(row.getLoanRepaymentScheduleId())))
                .then();
        Mono<Void> reactivate = Flux.fromIterable(request.getPreviousScheduleIds())
                .concatMap(id -> schedules.findById(id)
                        .filter(row -> caseId.equals(row.getLoanServicingCaseId()))
                        .flatMap(row -> {
                            row.setScheduleStatus(ScheduleStatus.CURRENT);
                            row.setUpdatedAt(LocalDateTime.now());
                            return schedules.save(row);
                        }))
                .then();
        return removeReplacement.then(reactivate).then(Mono.defer(() -> read(caseId, false)));
    }

    private static LoanRepaymentSchedule newRow(UUID caseId, AmortizationCalendarCalculator.Installment row) {
        LocalDateTime now = LocalDateTime.now();
        return LoanRepaymentSchedule.builder()
                .loanRepaymentScheduleId(UUID.randomUUID())
                .loanServicingCaseId(caseId)
                .installmentNumber(row.number())
                .dueDate(row.dueDate())
                .principalDue(row.principalDue())
                .interestDue(row.interestDue())
                .feeDue(row.feeDue())
                .totalDue(row.totalDue())
                .remainingBalance(row.remainingBalance())
                .scheduleStatus(ScheduleStatus.CURRENT)
                .isPaid(false)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    private LoanRepaymentScheduleDTO toDto(LoanRepaymentSchedule row) {
        return LoanRepaymentScheduleDTO.builder()
                .loanRepaymentScheduleId(row.getLoanRepaymentScheduleId())
                .loanServicingCaseId(row.getLoanServicingCaseId())
                .installmentNumber(row.getInstallmentNumber())
                .dueDate(row.getDueDate())
                .principalDue(row.getPrincipalDue())
                .interestDue(row.getInterestDue())
                .feeDue(row.getFeeDue())
                .totalDue(row.getTotalDue())
                .remainingBalance(row.getRemainingBalance())
                .scheduleStatus(row.getScheduleStatus())
                .isPaid(row.getIsPaid())
                .paidDate(row.getPaidDate())
                .paidAmount(row.getPaidAmount())
                .createdAt(row.getCreatedAt())
                .updatedAt(row.getUpdatedAt())
                .build();
    }
}
