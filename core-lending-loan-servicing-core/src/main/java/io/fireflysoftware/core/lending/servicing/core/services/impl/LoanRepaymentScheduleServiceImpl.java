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


package io.fireflysoftware.core.lending.servicing.core.services.impl;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.lending.servicing.core.mappers.LoanRepaymentScheduleMapper;
import io.fireflysoftware.core.lending.servicing.core.schedule.AmortizationCalendarCalculator;
import io.fireflysoftware.core.lending.servicing.core.services.LoanRepaymentScheduleService;
import io.fireflysoftware.core.lending.servicing.interfaces.dtos.LoanRepaymentScheduleDTO;
import io.fireflysoftware.core.lending.servicing.interfaces.enums.ScheduleStatus;
import io.fireflysoftware.core.lending.servicing.models.entities.LoanRepaymentSchedule;
import io.fireflysoftware.core.lending.servicing.models.repositories.LoanRepaymentScheduleRepository;
import io.fireflysoftware.core.lending.servicing.models.repositories.LoanServicingCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Transactional
public class LoanRepaymentScheduleServiceImpl implements LoanRepaymentScheduleService {

    @Autowired
    private LoanRepaymentScheduleRepository repository;

    @Autowired
    private LoanServicingCaseRepository cases;

    @Autowired
    private LoanRepaymentScheduleMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanRepaymentScheduleDTO>> findAll(UUID loanServicingCaseId, FilterRequest<LoanRepaymentScheduleDTO> filterRequest) {
        filterRequest.getFilters().setLoanServicingCaseId(loanServicingCaseId);
        return FilterUtils.createFilter(
                LoanRepaymentSchedule.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<LoanRepaymentScheduleDTO> create(UUID loanServicingCaseId, LoanRepaymentScheduleDTO dto) {
        dto.setLoanServicingCaseId(loanServicingCaseId);
        LoanRepaymentSchedule entity = mapper.toEntity(dto);
        if (entity.getScheduleStatus() == null) {
            entity.setScheduleStatus(ScheduleStatus.CURRENT);
        }
        return applyRemainingBalance(entity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanRepaymentScheduleDTO> getById(UUID loanServicingCaseId, UUID loanRepaymentScheduleId) {
        return repository.findById(loanRepaymentScheduleId)
                .filter(entity -> entity.getLoanServicingCaseId().equals(loanServicingCaseId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanRepaymentScheduleDTO> update(UUID loanServicingCaseId, UUID loanRepaymentScheduleId, LoanRepaymentScheduleDTO dto) {
        return repository.findById(loanRepaymentScheduleId)
                .filter(entity -> entity.getLoanServicingCaseId().equals(loanServicingCaseId))
                .flatMap(existingEntity -> {
                    LoanRepaymentSchedule updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setLoanRepaymentScheduleId(loanRepaymentScheduleId);
                    updatedEntity.setLoanServicingCaseId(loanServicingCaseId);
                    if (updatedEntity.getRemainingBalance() == null) {
                        updatedEntity.setRemainingBalance(existingEntity.getRemainingBalance());
                    }
                    if (updatedEntity.getScheduleStatus() == null) {
                        updatedEntity.setScheduleStatus(existingEntity.getScheduleStatus());
                    }
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID loanServicingCaseId, UUID loanRepaymentScheduleId) {
        return repository.findById(loanRepaymentScheduleId)
                .filter(entity -> entity.getLoanServicingCaseId().equals(loanServicingCaseId))
                .flatMap(repository::delete);
    }

    private Mono<LoanRepaymentSchedule> applyRemainingBalance(LoanRepaymentSchedule entity) {
        if (entity.getRemainingBalance() != null) {
            return Mono.just(entity);
        }
        return cases.findById(entity.getLoanServicingCaseId())
                .flatMap(loan -> repository.findByLoanServicingCaseId(entity.getLoanServicingCaseId())
                        .filter(row -> row.getScheduleStatus() != ScheduleStatus.SUPERSEDED)
                        .filter(row -> row.getInstallmentNumber() != null
                                && entity.getInstallmentNumber() != null
                                && row.getInstallmentNumber() < entity.getInstallmentNumber())
                        .map(row -> row.getPrincipalDue() == null ? BigDecimal.ZERO : row.getPrincipalDue())
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .map(prior -> {
                            entity.setRemainingBalance(AmortizationCalendarCalculator.remaining(
                                    loan.getPrincipalAmount(), prior, entity.getPrincipalDue()));
                            return entity;
                        }))
                .switchIfEmpty(Mono.fromSupplier(() -> {
                    entity.setRemainingBalance(AmortizationCalendarCalculator.remaining(
                            BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
                    return entity;
                }));
    }
}