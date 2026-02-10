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
import com.firefly.core.lending.servicing.core.services.LoanRebateService;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanRebateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loan-servicing-cases/{caseId}/rebates")
@Tag(name = "Loan Rebate", description = "Manage loan rebates including borrower rebates, distributor commissions, and promotional incentives")
@RequiredArgsConstructor
public class LoanRebateController {

    private final LoanRebateService service;

    @GetMapping
    @Operation(
            summary = "List rebates",
            description = "Retrieve all rebates for a specific loan servicing case with pagination and filtering support"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved rebates",
                    content = @Content(schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content),
            @ApiResponse(responseCode = "404", description = "Loan servicing case not found", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<LoanRebateDTO>>> findAllRebates(
            @Parameter(description = "Loan servicing case ID", required = true)
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Parameter(description = "Filter and pagination parameters")
            @ModelAttribute FilterRequest<LoanRebateDTO> filterRequest) {

        return service.findAll(loanServicingCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
            summary = "Create rebate",
            description = "Create a new rebate for a loan servicing case"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rebate created successfully",
                    content = @Content(schema = @Schema(implementation = LoanRebateDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Loan servicing case not found", content = @Content)
    })
    public Mono<ResponseEntity<LoanRebateDTO>> createRebate(
            @Parameter(description = "Loan servicing case ID", required = true)
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Parameter(description = "Rebate data", required = true)
            @Valid @RequestBody LoanRebateDTO dto) {

        return service.create(loanServicingCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{rebateId}")
    @Operation(
            summary = "Get rebate by ID",
            description = "Retrieve a specific rebate by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rebate found",
                    content = @Content(schema = @Schema(implementation = LoanRebateDTO.class))),
            @ApiResponse(responseCode = "404", description = "Rebate not found", content = @Content)
    })
    public Mono<ResponseEntity<LoanRebateDTO>> getRebateById(
            @Parameter(description = "Loan servicing case ID", required = true)
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Parameter(description = "Rebate ID", required = true)
            @PathVariable("rebateId") UUID loanRebateId) {

        return service.getById(loanServicingCaseId, loanRebateId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{rebateId}")
    @Operation(
            summary = "Update rebate",
            description = "Update an existing rebate"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rebate updated successfully",
                    content = @Content(schema = @Schema(implementation = LoanRebateDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rebate not found", content = @Content)
    })
    public Mono<ResponseEntity<LoanRebateDTO>> updateRebate(
            @Parameter(description = "Loan servicing case ID", required = true)
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Parameter(description = "Rebate ID", required = true)
            @PathVariable("rebateId") UUID loanRebateId,
            @Parameter(description = "Updated rebate data", required = true)
            @Valid @RequestBody LoanRebateDTO dto) {

        return service.update(loanServicingCaseId, loanRebateId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{rebateId}")
    @Operation(
            summary = "Delete rebate",
            description = "Delete a rebate"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Rebate deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Rebate not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> deleteRebate(
            @Parameter(description = "Loan servicing case ID", required = true)
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Parameter(description = "Rebate ID", required = true)
            @PathVariable("rebateId") UUID loanRebateId) {

        return service.delete(loanServicingCaseId, loanRebateId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}

