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
import com.firefly.core.lending.servicing.core.services.LoanDisbursementService;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanDisbursementDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loan-servicing-cases/{caseId}/disbursements")
@Tag(name = "LoanDisbursement", description = "Operations for Loan Disbursements")
@RequiredArgsConstructor
public class LoanDisbursementController {

    private final LoanDisbursementService service;

    @GetMapping
    @Operation(summary = "List/Search disbursements for a specific servicing case")
    public Mono<ResponseEntity<PaginationResponse<LoanDisbursementDTO>>> findAllDisbursements(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @ModelAttribute FilterRequest<LoanDisbursementDTO> filterRequest) {

        return service.findAll(loanServicingCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new disbursement under a servicing case")
    public Mono<ResponseEntity<LoanDisbursementDTO>> createDisbursement(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Valid @RequestBody LoanDisbursementDTO dto) {

        return service.create(loanServicingCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{disbursementId}")
    @Operation(summary = "Get a disbursement by ID")
    public Mono<ResponseEntity<LoanDisbursementDTO>> getDisbursementById(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("disbursementId") UUID loanDisbursementId) {

        return service.getById(loanServicingCaseId, loanDisbursementId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{disbursementId}")
    @Operation(summary = "Update a disbursement record")
    public Mono<ResponseEntity<LoanDisbursementDTO>> updateDisbursement(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("disbursementId") UUID loanDisbursementId,
            @Valid @RequestBody LoanDisbursementDTO dto) {

        return service.update(loanServicingCaseId, loanDisbursementId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{disbursementId}")
    @Operation(summary = "Delete a disbursement record")
    public Mono<ResponseEntity<Void>> deleteDisbursement(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("disbursementId") UUID loanDisbursementId) {

        return service.delete(loanServicingCaseId, loanDisbursementId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
