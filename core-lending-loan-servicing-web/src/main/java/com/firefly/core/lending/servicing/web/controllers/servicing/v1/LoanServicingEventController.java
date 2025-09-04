/*
 * Copyright 2025 Firefly Software Solutions Inc
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


package com.firefly.core.lending.servicing.web.controllers.servicing.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.core.services.servicing.v1.LoanServicingEventService;
import com.firefly.core.lending.servicing.interfaces.dtos.servicing.v1.LoanServicingEventDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loan-servicing-cases/{caseId}/events")
@Tag(name = "LoanServicingEvent", description = "Operations for Loan Servicing Events")
@RequiredArgsConstructor
public class LoanServicingEventController {

    private final LoanServicingEventService service;

    @GetMapping
    @Operation(summary = "List/Search loan servicing events")
    public Mono<ResponseEntity<PaginationResponse<LoanServicingEventDTO>>> findAll(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @ModelAttribute FilterRequest<LoanServicingEventDTO> filterRequest) {

        return service.findAll(loanServicingCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new loan servicing event")
    public Mono<ResponseEntity<LoanServicingEventDTO>> create(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Valid @RequestBody LoanServicingEventDTO dto) {

        return service.create(loanServicingCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{eventId}")
    @Operation(summary = "Get a loan servicing event by ID")
    public Mono<ResponseEntity<LoanServicingEventDTO>> getById(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("eventId") UUID loanServicingEventId) {

        return service.getById(loanServicingCaseId, loanServicingEventId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{eventId}")
    @Operation(summary = "Update a loan servicing event")
    public Mono<ResponseEntity<LoanServicingEventDTO>> update(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("eventId") UUID loanServicingEventId,
            @Valid @RequestBody LoanServicingEventDTO dto) {

        return service.update(loanServicingCaseId, loanServicingEventId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{eventId}")
    @Operation(summary = "Delete a loan servicing event")
    public Mono<ResponseEntity<Void>> delete(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("eventId") UUID loanServicingEventId) {

        return service.delete(loanServicingCaseId, loanServicingEventId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
