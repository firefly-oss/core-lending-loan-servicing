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


package io.fireflysoftware.core.lending.servicing.web.controllers;

import io.fireflysoftware.core.lending.servicing.core.syndicate.SyndicatedFacilityService;
import io.fireflysoftware.core.lending.servicing.interfaces.dtos.AllocateFacilityRepaymentRequest;
import io.fireflysoftware.core.lending.servicing.interfaces.dtos.AttachFacilityPaymentRequest;
import io.fireflysoftware.core.lending.servicing.interfaces.dtos.FacilityAllocationDTO;
import io.fireflysoftware.core.lending.servicing.interfaces.dtos.FacilityParticipantDTO;
import io.fireflysoftware.core.lending.servicing.interfaces.dtos.LoanDisbursementDTO;
import io.fireflysoftware.core.lending.servicing.interfaces.dtos.SyndicatedFacilityDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loan-servicing-cases/{caseId}/syndicated-facilities")
@Tag(name = "SyndicatedFacility", description = "Syndicated facility, participants, drawdowns, and repayment allocation")
public class SyndicatedFacilityController {

    private final SyndicatedFacilityService service;

    public SyndicatedFacilityController(SyndicatedFacilityService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(operationId = "listSyndicatedFacilities", summary = "List syndicated facilities on a servicing case")
    public Flux<SyndicatedFacilityDTO> listSyndicatedFacilities(@PathVariable UUID caseId,
                                                                @RequestHeader(value = "X-Idempotency-Key", required = false) String idempotencyKey) {
        return service.list(caseId);
    }

    @PostMapping
    @Operation(operationId = "open", summary = "Open a syndicated facility on a servicing case")
    public Mono<ResponseEntity<SyndicatedFacilityDTO>> open(@PathVariable UUID caseId,
                                                            @RequestBody SyndicatedFacilityDTO body,
                                                            @RequestHeader(value = "X-Idempotency-Key", required = false) String idempotencyKey) {
        return service.open(caseId, body).map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @GetMapping("/{facilityId}")
    @Operation(operationId = "get", summary = "Read a syndicated facility")
    public Mono<ResponseEntity<SyndicatedFacilityDTO>> get(@PathVariable UUID caseId,
                                                           @PathVariable UUID facilityId,
                                                           @RequestHeader(value = "X-Idempotency-Key", required = false) String idempotencyKey) {
        return service.get(caseId, facilityId).map(ResponseEntity::ok);
    }

    @DeleteMapping("/{facilityId}")
    @Operation(operationId = "delete", summary = "Delete a syndicated facility and its participants")
    public Mono<ResponseEntity<Void>> delete(@PathVariable UUID caseId,
                                             @PathVariable UUID facilityId,
                                             @RequestHeader(value = "X-Idempotency-Key", required = false) String idempotencyKey) {
        return service.deleteFacility(caseId, facilityId).thenReturn(ResponseEntity.noContent().build());
    }

    @PostMapping("/{facilityId}/closures")
    @Operation(operationId = "close", summary = "Close a syndicated facility")
    public Mono<ResponseEntity<SyndicatedFacilityDTO>> close(@PathVariable UUID caseId,
                                                             @PathVariable UUID facilityId,
                                                             @RequestHeader(value = "X-Idempotency-Key", required = false) String idempotencyKey) {
        return service.close(caseId, facilityId).map(ResponseEntity::ok);
    }

    @PostMapping("/{facilityId}/status-restores/{historyId}")
    @Operation(operationId = "restore", summary = "Restore the facility status recorded in a history row")
    public Mono<ResponseEntity<SyndicatedFacilityDTO>> restore(@PathVariable UUID caseId,
                                                               @PathVariable UUID facilityId,
                                                               @PathVariable UUID historyId,
                                                               @RequestHeader(value = "X-Idempotency-Key", required = false) String idempotencyKey) {
        return service.restoreStatus(caseId, facilityId, historyId).map(ResponseEntity::ok);
    }

    @GetMapping("/{facilityId}/participants")
    @Operation(operationId = "listParticipants", summary = "List participants on a facility")
    public Flux<FacilityParticipantDTO> listParticipants(@PathVariable UUID caseId,
                                                         @PathVariable UUID facilityId,
                                                         @RequestHeader(value = "X-Idempotency-Key", required = false) String idempotencyKey) {
        return service.listParticipants(caseId, facilityId);
    }

    @PostMapping("/{facilityId}/participants")
    @Operation(operationId = "addParticipant", summary = "Add a participant")
    public Mono<ResponseEntity<FacilityParticipantDTO>> addParticipant(@PathVariable UUID caseId,
                                                                       @PathVariable UUID facilityId,
                                                                       @RequestBody FacilityParticipantDTO body,
                                                                       @RequestHeader(value = "X-Idempotency-Key", required = false) String idempotencyKey) {
        return service.addParticipant(caseId, facilityId, body).map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @PutMapping("/{facilityId}/participants/{participantId}")
    @Operation(operationId = "updateParticipant", summary = "Update a participant")
    public Mono<ResponseEntity<FacilityParticipantDTO>> updateParticipant(@PathVariable UUID caseId,
                                                                          @PathVariable UUID facilityId,
                                                                          @PathVariable UUID participantId,
                                                                          @RequestBody FacilityParticipantDTO body,
                                                                          @RequestHeader(value = "X-Idempotency-Key", required = false) String idempotencyKey) {
        return service.updateParticipant(caseId, facilityId, participantId, body).map(ResponseEntity::ok);
    }

    @DeleteMapping("/{facilityId}/participants/{participantId}")
    @Operation(operationId = "deleteParticipant", summary = "Remove a participant")
    public Mono<ResponseEntity<Void>> deleteParticipant(@PathVariable UUID caseId,
                                                        @PathVariable UUID facilityId,
                                                        @PathVariable UUID participantId,
                                                        @RequestHeader(value = "X-Idempotency-Key", required = false) String idempotencyKey) {
        return service.deleteParticipant(caseId, facilityId, participantId).thenReturn(ResponseEntity.noContent().build());
    }

    @PostMapping("/{facilityId}/drawdowns")
    @Operation(operationId = "draw", summary = "Draw the facility through the existing disbursement")
    public Mono<ResponseEntity<LoanDisbursementDTO>> draw(@PathVariable UUID caseId,
                                                          @PathVariable UUID facilityId,
                                                          @RequestBody LoanDisbursementDTO body,
                                                          @RequestHeader(value = "X-Idempotency-Key", required = false) String idempotencyKey) {
        return service.draw(caseId, facilityId, body).map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @PostMapping("/{facilityId}/repayment-allocations")
    @Operation(operationId = "allocate", summary = "Allocate a repayment across participants by share")
    public Mono<ResponseEntity<FacilityAllocationDTO>> allocate(@PathVariable UUID caseId,
                                                                @PathVariable UUID facilityId,
                                                                @RequestBody AllocateFacilityRepaymentRequest body,
                                                                @RequestHeader(value = "X-Idempotency-Key", required = false) String idempotencyKey) {
        return service.allocate(caseId, facilityId, body).map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @PutMapping("/{facilityId}/repayment-allocations/{allocationId}/payment")
    @Operation(operationId = "attachPayment", summary = "Record the payment id accepted for an allocation")
    public Mono<ResponseEntity<FacilityAllocationDTO>> attachPayment(@PathVariable UUID caseId,
                                                                     @PathVariable UUID facilityId,
                                                                     @PathVariable UUID allocationId,
                                                                     @RequestBody AttachFacilityPaymentRequest body,
                                                                     @RequestHeader(value = "X-Idempotency-Key", required = false) String idempotencyKey) {
        return service.attachPayment(caseId, facilityId, allocationId, body.paymentId()).map(ResponseEntity::ok);
    }

    @DeleteMapping("/{facilityId}/repayment-allocations/{allocationId}")
    @Operation(operationId = "deleteAllocation", summary = "Delete a repayment allocation")
    public Mono<ResponseEntity<Void>> deleteAllocation(@PathVariable UUID caseId,
                                                       @PathVariable UUID facilityId,
                                                       @PathVariable UUID allocationId,
                                                       @RequestHeader(value = "X-Idempotency-Key", required = false) String idempotencyKey) {
        return service.deleteAllocation(caseId, facilityId, allocationId).thenReturn(ResponseEntity.noContent().build());
    }
}
