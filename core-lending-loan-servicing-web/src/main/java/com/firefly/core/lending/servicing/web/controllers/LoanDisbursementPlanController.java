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
import com.firefly.core.lending.servicing.core.services.LoanDisbursementPlanService;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanDisbursementPlanDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loan-servicing-cases/{caseId}/disbursement-plans")
@Tag(name = "Loan Disbursement Plan", description = "Manage loan disbursement plans for construction loans, staged funding, and line of credit draws")
@RequiredArgsConstructor
public class LoanDisbursementPlanController {

    private final LoanDisbursementPlanService service;

    @GetMapping
    @Operation(
            summary = "List disbursement plans",
            description = "Retrieve all disbursement plans for a specific loan servicing case with pagination and filtering support"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved disbursement plans",
                    content = @Content(schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content),
            @ApiResponse(responseCode = "404", description = "Loan servicing case not found", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<LoanDisbursementPlanDTO>>> findAllDisbursementPlans(
            @Parameter(description = "Loan servicing case ID", required = true)
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Parameter(description = "Filter and pagination parameters")
            @ModelAttribute FilterRequest<LoanDisbursementPlanDTO> filterRequest) {

        return service.findAll(loanServicingCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
            summary = "Create disbursement plan",
            description = "Create a new disbursement plan for a loan servicing case"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disbursement plan created successfully",
                    content = @Content(schema = @Schema(implementation = LoanDisbursementPlanDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Loan servicing case not found", content = @Content)
    })
    public Mono<ResponseEntity<LoanDisbursementPlanDTO>> createDisbursementPlan(
            @Parameter(description = "Loan servicing case ID", required = true)
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Parameter(description = "Disbursement plan data", required = true)
            @Valid @RequestBody LoanDisbursementPlanDTO dto) {

        return service.create(loanServicingCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{planId}")
    @Operation(
            summary = "Get disbursement plan by ID",
            description = "Retrieve a specific disbursement plan by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disbursement plan found",
                    content = @Content(schema = @Schema(implementation = LoanDisbursementPlanDTO.class))),
            @ApiResponse(responseCode = "404", description = "Disbursement plan not found", content = @Content)
    })
    public Mono<ResponseEntity<LoanDisbursementPlanDTO>> getDisbursementPlanById(
            @Parameter(description = "Loan servicing case ID", required = true)
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Parameter(description = "Disbursement plan ID", required = true)
            @PathVariable("planId") UUID loanDisbursementPlanId) {

        return service.getById(loanServicingCaseId, loanDisbursementPlanId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{planId}")
    @Operation(
            summary = "Update disbursement plan",
            description = "Update an existing disbursement plan"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disbursement plan updated successfully",
                    content = @Content(schema = @Schema(implementation = LoanDisbursementPlanDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Disbursement plan not found", content = @Content)
    })
    public Mono<ResponseEntity<LoanDisbursementPlanDTO>> updateDisbursementPlan(
            @Parameter(description = "Loan servicing case ID", required = true)
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Parameter(description = "Disbursement plan ID", required = true)
            @PathVariable("planId") UUID loanDisbursementPlanId,
            @Parameter(description = "Updated disbursement plan data", required = true)
            @Valid @RequestBody LoanDisbursementPlanDTO dto) {

        return service.update(loanServicingCaseId, loanDisbursementPlanId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{planId}")
    @Operation(
            summary = "Delete disbursement plan",
            description = "Delete a disbursement plan"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Disbursement plan deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Disbursement plan not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> deleteDisbursementPlan(
            @Parameter(description = "Loan servicing case ID", required = true)
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Parameter(description = "Disbursement plan ID", required = true)
            @PathVariable("planId") UUID loanDisbursementPlanId) {

        return service.delete(loanServicingCaseId, loanDisbursementPlanId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}

