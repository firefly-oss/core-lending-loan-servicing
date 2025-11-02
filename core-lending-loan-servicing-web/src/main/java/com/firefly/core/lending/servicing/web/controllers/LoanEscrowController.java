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
import com.firefly.core.lending.servicing.core.services.LoanEscrowService;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanEscrowDTO;
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
@RequestMapping("/api/v1/loan-servicing-cases/{caseId}/escrows")
@Tag(name = "Loan Escrow", description = "Manage loan escrow accounts for all lending products including insurance, taxes, maintenance reserves, freight/shipping, debt service reserves, and other escrow purposes")
@RequiredArgsConstructor
public class LoanEscrowController {

    private final LoanEscrowService service;

    @GetMapping
    @Operation(
            summary = "List escrow accounts",
            description = "Retrieve all escrow accounts for a specific loan servicing case with pagination and filtering support"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved escrow accounts",
                    content = @Content(schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content),
            @ApiResponse(responseCode = "404", description = "Loan servicing case not found", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<LoanEscrowDTO>>> findAllEscrows(
            @Parameter(description = "Loan servicing case ID", required = true)
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Parameter(description = "Filter and pagination parameters")
            @ModelAttribute FilterRequest<LoanEscrowDTO> filterRequest) {

        return service.findAll(loanServicingCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
            summary = "Create escrow account",
            description = "Create a new escrow account for a loan servicing case"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Escrow account created successfully",
                    content = @Content(schema = @Schema(implementation = LoanEscrowDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Loan servicing case not found", content = @Content)
    })
    public Mono<ResponseEntity<LoanEscrowDTO>> createEscrow(
            @Parameter(description = "Loan servicing case ID", required = true)
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Parameter(description = "Escrow account data", required = true)
            @Valid @RequestBody LoanEscrowDTO dto) {

        return service.create(loanServicingCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{escrowId}")
    @Operation(
            summary = "Get escrow account by ID",
            description = "Retrieve a specific escrow account by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Escrow account found",
                    content = @Content(schema = @Schema(implementation = LoanEscrowDTO.class))),
            @ApiResponse(responseCode = "404", description = "Escrow account not found", content = @Content)
    })
    public Mono<ResponseEntity<LoanEscrowDTO>> getEscrowById(
            @Parameter(description = "Loan servicing case ID", required = true)
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Parameter(description = "Escrow account ID", required = true)
            @PathVariable("escrowId") UUID loanEscrowId) {

        return service.getById(loanServicingCaseId, loanEscrowId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{escrowId}")
    @Operation(
            summary = "Update escrow account",
            description = "Update an existing escrow account"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Escrow account updated successfully",
                    content = @Content(schema = @Schema(implementation = LoanEscrowDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Escrow account not found", content = @Content)
    })
    public Mono<ResponseEntity<LoanEscrowDTO>> updateEscrow(
            @Parameter(description = "Loan servicing case ID", required = true)
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Parameter(description = "Escrow account ID", required = true)
            @PathVariable("escrowId") UUID loanEscrowId,
            @Parameter(description = "Updated escrow account data", required = true)
            @Valid @RequestBody LoanEscrowDTO dto) {

        return service.update(loanServicingCaseId, loanEscrowId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{escrowId}")
    @Operation(
            summary = "Delete escrow account",
            description = "Delete an escrow account"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Escrow account deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Escrow account not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> deleteEscrow(
            @Parameter(description = "Loan servicing case ID", required = true)
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Parameter(description = "Escrow account ID", required = true)
            @PathVariable("escrowId") UUID loanEscrowId) {

        return service.delete(loanServicingCaseId, loanEscrowId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}

