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


package com.firefly.core.lending.servicing.web.controllers.repayment.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.core.services.repayment.v1.LoanInstallmentPlanService;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanInstallmentPlanDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loan-servicing-cases/{caseId}/installment-plans")
@Tag(name = "LoanInstallmentPlan", description = "Operations for Loan Installment Plans")
@RequiredArgsConstructor
public class LoanInstallmentPlanController {

    private final LoanInstallmentPlanService service;

    @GetMapping
    @Operation(summary = "List/Search installment plans for a servicing case")
    public Mono<ResponseEntity<PaginationResponse<LoanInstallmentPlanDTO>>> findAllInstallmentPlans(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @ModelAttribute FilterRequest<LoanInstallmentPlanDTO> filterRequest) {

        return service.findAll(loanServicingCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create an installment plan entry")
    public Mono<ResponseEntity<LoanInstallmentPlanDTO>> createInstallmentPlan(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Valid @RequestBody LoanInstallmentPlanDTO dto) {

        return service.create(loanServicingCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{planId}")
    @Operation(summary = "Get an installment plan entry by ID")
    public Mono<ResponseEntity<LoanInstallmentPlanDTO>> getInstallmentPlanById(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("planId") UUID loanInstallmentPlanId) {

        return service.getById(loanServicingCaseId, loanInstallmentPlanId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{planId}")
    @Operation(summary = "Update an installment plan entry")
    public Mono<ResponseEntity<LoanInstallmentPlanDTO>> updateInstallmentPlan(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("planId") UUID loanInstallmentPlanId,
            @Valid @RequestBody LoanInstallmentPlanDTO dto) {

        return service.update(loanServicingCaseId, loanInstallmentPlanId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{planId}")
    @Operation(summary = "Delete an installment plan entry")
    public Mono<ResponseEntity<Void>> deleteInstallmentPlan(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("planId") UUID loanInstallmentPlanId) {

        return service.delete(loanServicingCaseId, loanInstallmentPlanId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}

