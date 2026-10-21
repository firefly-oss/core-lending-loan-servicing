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


package io.fireflysoftware.core.lending.servicing.core.syndicate;

import io.fireflysoftware.core.lending.servicing.core.mappers.LoanDisbursementMapper;
import io.fireflysoftware.core.lending.servicing.interfaces.dtos.AllocateFacilityRepaymentRequest;
import io.fireflysoftware.core.lending.servicing.interfaces.dtos.FacilityAllocationDTO;
import io.fireflysoftware.core.lending.servicing.interfaces.dtos.FacilityAllocationLineDTO;
import io.fireflysoftware.core.lending.servicing.interfaces.dtos.FacilityParticipantDTO;
import io.fireflysoftware.core.lending.servicing.interfaces.dtos.LoanDisbursementDTO;
import io.fireflysoftware.core.lending.servicing.interfaces.dtos.SyndicatedFacilityDTO;
import io.fireflysoftware.core.lending.servicing.interfaces.enums.FacilityParticipantRole;
import io.fireflysoftware.core.lending.servicing.interfaces.enums.FacilityStatus;
import io.fireflysoftware.core.lending.servicing.models.entities.FacilityAllocationLine;
import io.fireflysoftware.core.lending.servicing.models.entities.FacilityParticipant;
import io.fireflysoftware.core.lending.servicing.models.entities.FacilityRepaymentAllocation;
import io.fireflysoftware.core.lending.servicing.models.entities.FacilityStatusHistory;
import io.fireflysoftware.core.lending.servicing.models.entities.LoanDisbursement;
import io.fireflysoftware.core.lending.servicing.models.entities.SyndicatedFacility;
import io.fireflysoftware.core.lending.servicing.models.repositories.FacilityAllocationLineRepository;
import io.fireflysoftware.core.lending.servicing.models.repositories.FacilityParticipantRepository;
import io.fireflysoftware.core.lending.servicing.models.repositories.FacilityRepaymentAllocationRepository;
import io.fireflysoftware.core.lending.servicing.models.repositories.FacilityStatusHistoryRepository;
import io.fireflysoftware.core.lending.servicing.models.repositories.LoanDisbursementRepository;
import io.fireflysoftware.core.lending.servicing.models.repositories.SyndicatedFacilityRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Book of a syndicated facility: participants, a drawdown on the existing disbursement, and the allocation of a repayment.
 */
@Service
public class SyndicatedFacilityService {

    private final SyndicatedFacilityRepository facilities;
    private final FacilityParticipantRepository participants;
    private final FacilityStatusHistoryRepository history;
    private final FacilityRepaymentAllocationRepository allocations;
    private final FacilityAllocationLineRepository lines;
    private final LoanDisbursementRepository disbursements;
    private final LoanDisbursementMapper disbursementMapper;

    public SyndicatedFacilityService(SyndicatedFacilityRepository facilities,
                                     FacilityParticipantRepository participants,
                                     FacilityStatusHistoryRepository history,
                                     FacilityRepaymentAllocationRepository allocations,
                                     FacilityAllocationLineRepository lines,
                                     LoanDisbursementRepository disbursements,
                                     LoanDisbursementMapper disbursementMapper) {
        this.facilities = facilities;
        this.participants = participants;
        this.history = history;
        this.allocations = allocations;
        this.lines = lines;
        this.disbursements = disbursements;
        this.disbursementMapper = disbursementMapper;
    }

    public Mono<SyndicatedFacilityDTO> open(UUID caseId, SyndicatedFacilityDTO request) {
        if (caseId == null) {
            return Mono.error(new IllegalArgumentException("Loan servicing case is required"));
        }
        if (request == null || request.commitmentAmount() == null || request.commitmentAmount().signum() <= 0) {
            return Mono.error(new IllegalArgumentException("Commitment amount must be positive"));
        }
        String currency = ParticipantAllocation.requireCurrency(request.currency());
        LocalDateTime now = LocalDateTime.now();
        SyndicatedFacility facility = SyndicatedFacility.builder()
                .facilityId(UUID.randomUUID())
                .loanServicingCaseId(caseId)
                .originationFacilityId(request.originationFacilityId())
                .currency(currency)
                .commitmentAmount(request.commitmentAmount())
                .status(FacilityStatus.OPEN)
                .createdAt(now)
                .updatedAt(now)
                .build();
        return facilities.save(facility).map(this::toDto);
    }

    public Flux<SyndicatedFacilityDTO> list(UUID caseId) {
        if (caseId == null) {
            return Flux.error(new IllegalArgumentException("Loan servicing case is required"));
        }
        return facilities.findAllByLoanServicingCaseId(caseId).map(this::toDto);
    }

    public Mono<SyndicatedFacilityDTO> get(UUID caseId, UUID facilityId) {
        return requireFacility(caseId, facilityId).map(this::toDto);
    }

    public Flux<FacilityParticipantDTO> listParticipants(UUID caseId, UUID facilityId) {
        return requireFacility(caseId, facilityId)
                .flatMapMany(facility -> participants.findByFacilityId(facility.getFacilityId()))
                .map(this::toDto);
    }

    public Mono<FacilityParticipantDTO> addParticipant(UUID caseId, UUID facilityId, FacilityParticipantDTO request) {
        if (request == null || request.partyId() == null || request.role() == null) {
            return Mono.error(new IllegalArgumentException("Participant party and role are required"));
        }
        BigDecimal share = ParticipantAllocation.requireShare(request.sharePercent());
        return requireOpen(caseId, facilityId).flatMap(facility ->
                participants.findByFacilityId(facility.getFacilityId()).collectList().flatMap(existing -> {
                    if (request.role() == FacilityParticipantRole.AGENT
                            && existing.stream().anyMatch(row -> row.getRole() == FacilityParticipantRole.AGENT)) {
                        return Mono.error(new IllegalArgumentException("A facility has one agent"));
                    }
                    LocalDateTime now = LocalDateTime.now();
                    FacilityParticipant row = FacilityParticipant.builder()
                            .participantId(UUID.randomUUID())
                            .facilityId(facility.getFacilityId())
                            .partyId(request.partyId())
                            .sharePercent(share)
                            .role(request.role())
                            .createdAt(now)
                            .updatedAt(now)
                            .build();
                    return participants.save(row).map(this::toDto);
                }));
    }

    public Mono<FacilityParticipantDTO> updateParticipant(UUID caseId, UUID facilityId, UUID participantId,
                                                         FacilityParticipantDTO request) {
        if (request == null || request.partyId() == null || request.role() == null) {
            return Mono.error(new IllegalArgumentException("Participant party and role are required"));
        }
        BigDecimal share = ParticipantAllocation.requireShare(request.sharePercent());
        return requireOpen(caseId, facilityId).flatMap(facility ->
                participants.findById(participantId)
                        .filter(row -> facility.getFacilityId().equals(row.getFacilityId()))
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("Participant was not found")))
                        .flatMap(row -> participants.findByFacilityId(facility.getFacilityId()).collectList()
                                .flatMap(existing -> {
                                    boolean otherAgent = existing.stream().anyMatch(candidate ->
                                            candidate.getRole() == FacilityParticipantRole.AGENT
                                                    && !candidate.getParticipantId().equals(participantId));
                                    if (request.role() == FacilityParticipantRole.AGENT && otherAgent) {
                                        return Mono.error(new IllegalArgumentException("A facility has one agent"));
                                    }
                                    row.setPartyId(request.partyId());
                                    row.setSharePercent(share);
                                    row.setRole(request.role());
                                    row.setUpdatedAt(LocalDateTime.now());
                                    return participants.save(row).map(this::toDto);
                                })));
    }

    public Mono<Void> deleteParticipant(UUID caseId, UUID facilityId, UUID participantId) {
        return requireFacility(caseId, facilityId).flatMap(facility ->
                participants.findById(participantId)
                        .filter(row -> facility.getFacilityId().equals(row.getFacilityId()))
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("Participant was not found")))
                        .flatMap(participants::delete));
    }

    public Mono<Void> deleteFacility(UUID caseId, UUID facilityId) {
        return requireFacility(caseId, facilityId).flatMap(facilities::delete);
    }

    public Mono<SyndicatedFacilityDTO> close(UUID caseId, UUID facilityId) {
        return requireOpen(caseId, facilityId).flatMap(facility -> {
            FacilityStatusHistory entry = FacilityStatusHistory.builder()
                    .historyId(UUID.randomUUID())
                    .facilityId(facility.getFacilityId())
                    .previousStatus(facility.getStatus())
                    .status(FacilityStatus.CLOSED)
                    .changedAt(LocalDateTime.now())
                    .build();
            facility.setStatus(FacilityStatus.CLOSED);
            facility.setUpdatedAt(entry.getChangedAt());
            return history.save(entry).then(facilities.save(facility)).map(this::toDto);
        });
    }

    public Mono<SyndicatedFacilityDTO> restoreStatus(UUID caseId, UUID facilityId, UUID historyId) {
        return requireFacility(caseId, facilityId).flatMap(facility ->
                history.findById(historyId)
                        .filter(entry -> facility.getFacilityId().equals(entry.getFacilityId()))
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("Status history was not found")))
                        .flatMap(entry -> {
                            facility.setStatus(entry.getPreviousStatus());
                            facility.setUpdatedAt(LocalDateTime.now());
                            return facilities.save(facility).flatMap(saved -> history.delete(entry).thenReturn(toDto(saved)));
                        }));
    }

    public Mono<LoanDisbursementDTO> draw(UUID caseId, UUID facilityId, LoanDisbursementDTO request) {
        if (request == null || request.getDisbursementAmount() == null || request.getDisbursementAmount().signum() <= 0) {
            return Mono.error(new IllegalArgumentException("Drawdown amount must be positive"));
        }
        return requireOpen(caseId, facilityId).flatMap(facility -> {
            LoanDisbursement entity = disbursementMapper.toEntity(request);
            if (entity.getLoanDisbursementId() == null) {
                entity.setLoanDisbursementId(UUID.randomUUID());
            }
            entity.setLoanServicingCaseId(caseId);
            entity.setFacilityId(facility.getFacilityId());
            if (entity.getCreatedAt() == null) {
                entity.setCreatedAt(LocalDateTime.now());
            }
            entity.setUpdatedAt(LocalDateTime.now());
            return disbursements.save(entity).map(disbursementMapper::toDTO);
        });
    }

    public Mono<FacilityAllocationDTO> allocate(UUID caseId, UUID facilityId, AllocateFacilityRepaymentRequest request) {
        if (request == null) {
            return Mono.error(new IllegalArgumentException("Repayment is required"));
        }
        return requireOpen(caseId, facilityId).flatMap(facility ->
                participants.findByFacilityId(facility.getFacilityId()).collectList().flatMap(rows -> {
                    ParticipantAllocation.Plan plan = ParticipantAllocation.allocate(
                            request.paymentAmount(),
                            request.currency() == null ? facility.getCurrency() : request.currency(),
                            rows.stream().map(row -> new ParticipantAllocation.Share(
                                    row.getParticipantId(), row.getPartyId(), row.getRole(), row.getSharePercent())).toList());
                    if (!facility.getCurrency().equals(plan.currency())) {
                        return Mono.error(new IllegalArgumentException("Repayment currency must match the facility"));
                    }
                    FacilityRepaymentAllocation header = FacilityRepaymentAllocation.builder()
                            .allocationId(UUID.randomUUID())
                            .facilityId(facility.getFacilityId())
                            .loanRepaymentRecordId(request.loanRepaymentRecordId())
                            .paymentAmount(plan.paymentAmount())
                            .unallocatedAmount(plan.unallocatedAmount())
                            .currency(plan.currency())
                            .createdAt(LocalDateTime.now())
                            .build();
                    return allocations.save(header).flatMap(saved -> Flux.fromIterable(plan.lines()).concatMap(line -> {
                        FacilityAllocationLine stored = FacilityAllocationLine.builder()
                                .lineId(UUID.randomUUID())
                                .allocationId(saved.getAllocationId())
                                .participantId(line.participantId())
                                .partyId(line.partyId())
                                .role(line.role())
                                .sharePercent(line.sharePercent())
                                .allocatedAmount(line.allocatedAmount())
                                .currency(plan.currency())
                                .build();
                        return lines.save(stored);
                    }).collectList().map(storedLines -> toDto(saved, storedLines)));
                }));
    }

    public Mono<FacilityAllocationDTO> attachPayment(UUID caseId, UUID facilityId, UUID allocationId, UUID paymentId) {
        if (paymentId == null) {
            return Mono.error(new IllegalArgumentException("Payment id is required"));
        }
        return requireFacility(caseId, facilityId).flatMap(facility ->
                allocations.findById(allocationId)
                        .filter(row -> facility.getFacilityId().equals(row.getFacilityId()))
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("Allocation was not found")))
                        .flatMap(row -> {
                            row.setPaymentId(paymentId);
                            return allocations.save(row);
                        })
                        .flatMap(saved -> lines.findByAllocationId(saved.getAllocationId()).collectList()
                                .map(storedLines -> toDto(saved, storedLines))));
    }

    public Mono<Void> deleteAllocation(UUID caseId, UUID facilityId, UUID allocationId) {
        return requireFacility(caseId, facilityId).flatMap(facility ->
                allocations.findById(allocationId)
                        .filter(row -> facility.getFacilityId().equals(row.getFacilityId()))
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("Allocation was not found")))
                        .flatMap(row -> lines.findByAllocationId(row.getAllocationId())
                                .flatMap(lines::delete)
                                .then(allocations.delete(row))));
    }

    private Mono<SyndicatedFacility> requireOpen(UUID caseId, UUID facilityId) {
        return requireFacility(caseId, facilityId).flatMap(facility -> facility.getStatus() == FacilityStatus.OPEN
                ? Mono.just(facility)
                : Mono.error(new IllegalArgumentException("Facility is closed")));
    }

    private Mono<SyndicatedFacility> requireFacility(UUID caseId, UUID facilityId) {
        if (caseId == null || facilityId == null) {
            return Mono.error(new IllegalArgumentException("Facility is required"));
        }
        return facilities.findById(facilityId)
                .filter(facility -> caseId.equals(facility.getLoanServicingCaseId()))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Facility was not found")));
    }

    private SyndicatedFacilityDTO toDto(SyndicatedFacility facility) {
        return new SyndicatedFacilityDTO(facility.getFacilityId(), facility.getLoanServicingCaseId(),
                facility.getOriginationFacilityId(), facility.getCurrency(), facility.getCommitmentAmount(),
                facility.getStatus());
    }

    private FacilityParticipantDTO toDto(FacilityParticipant row) {
        return new FacilityParticipantDTO(row.getParticipantId(), row.getFacilityId(), row.getPartyId(),
                row.getSharePercent(), row.getRole());
    }

    private FacilityAllocationDTO toDto(FacilityRepaymentAllocation header, java.util.List<FacilityAllocationLine> storedLines) {
        return new FacilityAllocationDTO(header.getAllocationId(), header.getFacilityId(), header.getLoanRepaymentRecordId(),
                header.getPaymentAmount(), header.getUnallocatedAmount(), header.getCurrency(), header.getPaymentId(),
                storedLines.stream().map(line -> new FacilityAllocationLineDTO(line.getLineId(), line.getParticipantId(),
                        line.getPartyId(), line.getRole(), line.getSharePercent(), line.getAllocatedAmount(),
                        line.getCurrency())).toList());
    }
}
