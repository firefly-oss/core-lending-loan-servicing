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
import io.fireflysoftware.core.lending.servicing.interfaces.dtos.FacilityParticipantDTO;
import io.fireflysoftware.core.lending.servicing.interfaces.dtos.LoanDisbursementDTO;
import io.fireflysoftware.core.lending.servicing.interfaces.dtos.SyndicatedFacilityDTO;
import io.fireflysoftware.core.lending.servicing.interfaces.enums.DisbursementMethodEnum;
import io.fireflysoftware.core.lending.servicing.interfaces.enums.DisbursementStatusEnum;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SyndicatedFacilityServiceTest {

    @Mock private SyndicatedFacilityRepository facilities;
    @Mock private FacilityParticipantRepository participants;
    @Mock private FacilityStatusHistoryRepository history;
    @Mock private FacilityRepaymentAllocationRepository allocations;
    @Mock private FacilityAllocationLineRepository lines;
    @Mock private LoanDisbursementRepository disbursements;
    @Mock private LoanDisbursementMapper disbursementMapper;

    private final ConcurrentHashMap<UUID, SyndicatedFacility> facilityRows = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, FacilityParticipant> participantRows = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, FacilityRepaymentAllocation> allocationRows = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, FacilityAllocationLine> lineRows = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, LoanDisbursement> drawdowns = new ConcurrentHashMap<>();

    private SyndicatedFacilityService service;
    private final UUID caseId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        lenient().when(facilities.save(any())).thenAnswer(invocation -> {
            SyndicatedFacility row = invocation.getArgument(0);
            facilityRows.put(row.getFacilityId(), row);
            return Mono.just(row);
        });
        lenient().when(facilities.findById(any(UUID.class))).thenAnswer(invocation ->
                Mono.justOrEmpty(facilityRows.get(invocation.getArgument(0))));
        lenient().when(participants.save(any())).thenAnswer(invocation -> {
            FacilityParticipant row = invocation.getArgument(0);
            participantRows.put(row.getParticipantId(), row);
            return Mono.just(row);
        });
        lenient().when(participants.findByFacilityId(any())).thenAnswer(invocation -> {
            UUID facilityId = invocation.getArgument(0);
            return Flux.fromIterable(participantRows.values().stream()
                    .filter(row -> facilityId.equals(row.getFacilityId())).toList());
        });
        lenient().when(allocations.save(any())).thenAnswer(invocation -> {
            FacilityRepaymentAllocation row = invocation.getArgument(0);
            allocationRows.put(row.getAllocationId(), row);
            return Mono.just(row);
        });
        lenient().when(lines.save(any())).thenAnswer(invocation -> {
            FacilityAllocationLine row = invocation.getArgument(0);
            lineRows.put(row.getLineId(), row);
            return Mono.just(row);
        });
        service = new SyndicatedFacilityService(facilities, participants, history, allocations, lines,
                disbursements, disbursementMapper);
    }

    @Test
    void facilityParticipantsAndAllocationAreRecordedByShare() {
        SyndicatedFacilityDTO opened = service.open(caseId, new SyndicatedFacilityDTO(
                null, caseId, UUID.randomUUID(), "USD", new BigDecimal("250000.00"), null)).block();
        assertThat(opened.status()).isEqualTo(FacilityStatus.OPEN);
        assertThat(opened.currency()).isEqualTo("USD");

        UUID agentParty = UUID.randomUUID();
        UUID lenderParty = UUID.randomUUID();
        FacilityParticipantDTO agent = service.addParticipant(caseId, opened.facilityId(),
                new FacilityParticipantDTO(null, null, agentParty, new BigDecimal("10"), FacilityParticipantRole.AGENT)).block();
        FacilityParticipantDTO lender = service.addParticipant(caseId, opened.facilityId(),
                new FacilityParticipantDTO(null, null, lenderParty, new BigDecimal("40"), FacilityParticipantRole.LENDER)).block();

        StepVerifier.create(service.addParticipant(caseId, opened.facilityId(),
                        new FacilityParticipantDTO(null, null, UUID.randomUUID(), new BigDecimal("5"), FacilityParticipantRole.AGENT)))
                .expectErrorMessage("A facility has one agent")
                .verify();

        when(disbursementMapper.toEntity(any())).thenAnswer(invocation -> new LoanDisbursement());
        when(disbursementMapper.toDTO(any())).thenAnswer(invocation -> {
            LoanDisbursement saved = invocation.getArgument(0);
            return LoanDisbursementDTO.builder().loanDisbursementId(saved.getLoanDisbursementId())
                    .loanServicingCaseId(saved.getLoanServicingCaseId()).facilityId(saved.getFacilityId())
                    .disbursementAmount(saved.getDisbursementAmount()).build();
        });
        when(disbursements.save(any())).thenAnswer(invocation -> {
            LoanDisbursement row = invocation.getArgument(0);
            drawdowns.put(row.getLoanDisbursementId(), row);
            return Mono.just(row);
        });

        LoanDisbursementDTO request = LoanDisbursementDTO.builder()
                .loanServicingCaseId(caseId)
                .transactionId(UUID.randomUUID())
                .disbursementAmount(new BigDecimal("1000.00"))
                .disbursementDate(LocalDate.now())
                .isFinalDisbursement(false)
                .disbursementMethod(DisbursementMethodEnum.INTERNAL)
                .disbursementStatus(DisbursementStatusEnum.COMPLETED)
                .build();
        LoanDisbursementDTO draw = service.draw(caseId, opened.facilityId(), request).block();
        assertThat(draw.getFacilityId()).isEqualTo(opened.facilityId());
        assertThat(drawdowns.get(draw.getLoanDisbursementId()).getFacilityId()).isEqualTo(opened.facilityId());

        FacilityAllocationDTO allocation = service.allocate(caseId, opened.facilityId(),
                new AllocateFacilityRepaymentRequest(UUID.randomUUID(), new BigDecimal("1000.00"), "USD")).block();
        assertThat(allocation.unallocatedAmount()).isEqualByComparingTo("500.00");
        assertThat(allocation.lines()).hasSize(2);
        assertThat(allocation.lines()).anySatisfy(line -> {
            assertThat(line.partyId()).isEqualTo(agent.partyId());
            assertThat(line.role()).isEqualTo(FacilityParticipantRole.AGENT);
            assertThat(line.allocatedAmount()).isEqualByComparingTo("100.00");
        });
        assertThat(allocation.lines()).anySatisfy(line -> {
            assertThat(line.partyId()).isEqualTo(lender.partyId());
            assertThat(line.allocatedAmount()).isEqualByComparingTo("400.00");
        });
        BigDecimal recorded = allocation.lines().stream()
                .map(line -> line.allocatedAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(allocation.unallocatedAmount());
        assertThat(recorded).isEqualByComparingTo("1000.00");
        assertThat(participantRows.values()).allSatisfy(row -> assertThat(row.getPartyId()).isNotNull());
    }

    @Test
    void sharesOutsideZeroToOneHundredAreRejected() {
        List<ParticipantAllocation.Share> shares = List.of(
                new ParticipantAllocation.Share(UUID.randomUUID(), UUID.randomUUID(), FacilityParticipantRole.AGENT, new BigDecimal("101")));
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> ParticipantAllocation.allocate(new BigDecimal("10"), "EUR", shares));
    }
}
