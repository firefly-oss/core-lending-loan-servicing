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


package com.firefly.core.lending.servicing.web.controllers.accrual.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.core.services.accrual.v1.LoanAccrualService;
import com.firefly.core.lending.servicing.interfaces.dtos.accrual.v1.LoanAccrualDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loan-servicing-cases/{caseId}/accruals")
@Tag(name = "LoanAccrual", description = "Operations for Loan Accruals (interest, fees, etc.)")
@RequiredArgsConstructor
public class LoanAccrualController {

    private final LoanAccrualService service;

    @GetMapping
    @Operation(summary = "List/Search accruals for a servicing case")
    public Mono<ResponseEntity<PaginationResponse<LoanAccrualDTO>>> findAll(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @ModelAttribute FilterRequest<LoanAccrualDTO> filterRequest) {

        return service.findAll(loanServicingCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a loan accrual record")
    public Mono<ResponseEntity<LoanAccrualDTO>> create(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Valid @RequestBody LoanAccrualDTO dto) {

        return service.create(loanServicingCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{accrualId}")
    @Operation(summary = "Get a loan accrual by ID")
    public Mono<ResponseEntity<LoanAccrualDTO>> getById(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("accrualId") UUID loanAccrualId) {

        return service.getById(loanServicingCaseId, loanAccrualId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{accrualId}")
    @Operation(summary = "Update a loan accrual record")
    public Mono<ResponseEntity<LoanAccrualDTO>> update(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("accrualId") UUID loanAccrualId,
            @Valid @RequestBody LoanAccrualDTO dto) {

        return service.update(loanServicingCaseId, loanAccrualId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{accrualId}")
    @Operation(summary = "Delete a loan accrual record")
    public Mono<ResponseEntity<Void>> delete(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("accrualId") UUID loanAccrualId) {

        return service.delete(loanServicingCaseId, loanAccrualId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
