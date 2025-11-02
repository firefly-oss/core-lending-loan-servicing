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

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.core.services.LoanBalanceService;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanBalanceDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loan-servicing-cases/{caseId}/balances")
@Tag(name = "LoanBalance", description = "Operations for Loan Balance tracking and history")
@RequiredArgsConstructor
public class LoanBalanceController {

    private final LoanBalanceService service;

    @GetMapping
    @Operation(summary = "List/Search balance history for a servicing case")
    public Mono<ResponseEntity<PaginationResponse<LoanBalanceDTO>>> findAllBalances(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @ModelAttribute FilterRequest<LoanBalanceDTO> filterRequest) {

        return service.findAll(loanServicingCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/current")
    @Operation(summary = "Get the current balance for a servicing case")
    public Mono<ResponseEntity<LoanBalanceDTO>> getCurrentBalance(
            @PathVariable("caseId") UUID loanServicingCaseId) {

        return service.getCurrentBalance(loanServicingCaseId)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new balance snapshot")
    public Mono<ResponseEntity<LoanBalanceDTO>> createBalance(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Valid @RequestBody LoanBalanceDTO dto) {

        return service.create(loanServicingCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{balanceId}")
    @Operation(summary = "Get a balance snapshot by ID")
    public Mono<ResponseEntity<LoanBalanceDTO>> getBalanceById(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("balanceId") UUID loanBalanceId) {

        return service.getById(loanServicingCaseId, loanBalanceId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{balanceId}")
    @Operation(summary = "Update a balance snapshot")
    public Mono<ResponseEntity<LoanBalanceDTO>> updateBalance(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("balanceId") UUID loanBalanceId,
            @Valid @RequestBody LoanBalanceDTO dto) {

        return service.update(loanServicingCaseId, loanBalanceId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{balanceId}")
    @Operation(summary = "Delete a balance snapshot")
    public Mono<ResponseEntity<Void>> deleteBalance(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("balanceId") UUID loanBalanceId) {

        return service.delete(loanServicingCaseId, loanBalanceId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}

