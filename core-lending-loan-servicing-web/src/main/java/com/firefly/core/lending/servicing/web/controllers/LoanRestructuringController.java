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
import com.firefly.core.lending.servicing.core.services.LoanRestructuringService;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanRestructuringDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loan-servicing-cases/{caseId}/restructurings")
@Tag(name = "LoanRestructuring", description = "Operations for Loan Restructuring events")
@RequiredArgsConstructor
public class LoanRestructuringController {

    private final LoanRestructuringService service;

    @GetMapping
    @Operation(summary = "List/Search restructuring events for a servicing case")
    public Mono<ResponseEntity<PaginationResponse<LoanRestructuringDTO>>> findAllRestructurings(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @ModelAttribute FilterRequest<LoanRestructuringDTO> filterRequest) {

        return service.findAll(loanServicingCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a loan restructuring record")
    public Mono<ResponseEntity<LoanRestructuringDTO>> createRestructuring(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Valid @RequestBody LoanRestructuringDTO dto) {

        return service.create(loanServicingCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{restructuringId}")
    @Operation(summary = "Get a loan restructuring by ID")
    public Mono<ResponseEntity<LoanRestructuringDTO>> getRestructuringById(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("restructuringId") UUID loanRestructuringId) {

        return service.getById(loanServicingCaseId, loanRestructuringId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{restructuringId}")
    @Operation(summary = "Update a loan restructuring record")
    public Mono<ResponseEntity<LoanRestructuringDTO>> updateRestructuring(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("restructuringId") UUID loanRestructuringId,
            @Valid @RequestBody LoanRestructuringDTO dto) {

        return service.update(loanServicingCaseId, loanRestructuringId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{restructuringId}")
    @Operation(summary = "Delete a loan restructuring record")
    public Mono<ResponseEntity<Void>> deleteRestructuring(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("restructuringId") UUID loanRestructuringId) {

        return service.delete(loanServicingCaseId, loanRestructuringId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}

