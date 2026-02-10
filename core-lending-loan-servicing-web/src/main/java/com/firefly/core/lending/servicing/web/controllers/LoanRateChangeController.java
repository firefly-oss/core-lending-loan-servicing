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


package com.firefly.core.lending.servicing.web.controllers;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.core.services.LoanRateChangeService;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanRateChangeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loan-servicing-cases/{caseId}/rate-changes")
@Tag(name = "LoanRateChange", description = "Operations for Loan Rate Changes")
@RequiredArgsConstructor
public class LoanRateChangeController {

    private final LoanRateChangeService service;

    @GetMapping
    @Operation(summary = "List/Search rate changes for a servicing case")
    public Mono<ResponseEntity<PaginationResponse<LoanRateChangeDTO>>> findAllRateChanges(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @ModelAttribute FilterRequest<LoanRateChangeDTO> filterRequest) {

        return service.findAll(loanServicingCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new loan rate change")
    public Mono<ResponseEntity<LoanRateChangeDTO>> createRateChange(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Valid @RequestBody LoanRateChangeDTO dto) {

        return service.create(loanServicingCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{rateChangeId}")
    @Operation(summary = "Get a loan rate change by ID")
    public Mono<ResponseEntity<LoanRateChangeDTO>> getRateChangeById(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("rateChangeId") UUID loanRateChangeId) {

        return service.getById(loanServicingCaseId, loanRateChangeId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{rateChangeId}")
    @Operation(summary = "Update a loan rate change")
    public Mono<ResponseEntity<LoanRateChangeDTO>> updateRateChange(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("rateChangeId") UUID loanRateChangeId,
            @Valid @RequestBody LoanRateChangeDTO dto) {

        return service.update(loanServicingCaseId, loanRateChangeId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{rateChangeId}")
    @Operation(summary = "Delete a loan rate change record")
    public Mono<ResponseEntity<Void>> deleteRateChange(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("rateChangeId") UUID loanRateChangeId) {

        return service.delete(loanServicingCaseId, loanRateChangeId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}