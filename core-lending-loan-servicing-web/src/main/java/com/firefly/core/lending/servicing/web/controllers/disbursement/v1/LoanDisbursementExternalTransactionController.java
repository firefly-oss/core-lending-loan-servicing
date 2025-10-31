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


package com.firefly.core.lending.servicing.web.controllers.disbursement.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.core.services.disbursement.v1.LoanDisbursementExternalTransactionService;
import com.firefly.core.lending.servicing.interfaces.dtos.disbursement.v1.LoanDisbursementExternalTransactionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loan-servicing-cases/{caseId}/disbursements/{disbursementId}/external-transactions")
@Tag(name = "LoanDisbursementExternalTransaction", description = "Operations for external PSP disbursement transactions")
@RequiredArgsConstructor
public class LoanDisbursementExternalTransactionController {

    private final LoanDisbursementExternalTransactionService service;

    @GetMapping
    @Operation(summary = "List/Search external transactions for a disbursement")
    public Mono<ResponseEntity<PaginationResponse<LoanDisbursementExternalTransactionDTO>>> findAllExternalTransactions(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("disbursementId") UUID loanDisbursementId,
            @ModelAttribute FilterRequest<LoanDisbursementExternalTransactionDTO> filterRequest) {

        return service.findAll(loanDisbursementId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new external transaction for a disbursement")
    public Mono<ResponseEntity<LoanDisbursementExternalTransactionDTO>> createExternalTransaction(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("disbursementId") UUID loanDisbursementId,
            @Valid @RequestBody LoanDisbursementExternalTransactionDTO dto) {

        return service.create(loanDisbursementId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{transactionId}")
    @Operation(summary = "Get an external transaction by ID")
    public Mono<ResponseEntity<LoanDisbursementExternalTransactionDTO>> getExternalTransactionById(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("disbursementId") UUID loanDisbursementId,
            @PathVariable("transactionId") UUID transactionId) {

        return service.getById(loanDisbursementId, transactionId)
                .map(ResponseEntity::ok);
    }
}

