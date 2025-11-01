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
import com.firefly.core.lending.servicing.core.services.repayment.v1.LoanRepaymentRecordService;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanRepaymentRecordDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loan-servicing-cases/{caseId}/repayment-records")
@Tag(name = "LoanRepaymentRecord", description = "Operations for Loan Repayment Records")
@RequiredArgsConstructor
public class LoanRepaymentRecordController {

    private final LoanRepaymentRecordService service;

    @GetMapping
    @Operation(summary = "List/Search repayment records for a servicing case")
    public Mono<ResponseEntity<PaginationResponse<LoanRepaymentRecordDTO>>> findAllRepaymentRecords(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @ModelAttribute FilterRequest<LoanRepaymentRecordDTO> filterRequest) {

        return service.findAll(loanServicingCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a repayment record")
    public Mono<ResponseEntity<LoanRepaymentRecordDTO>> createRepaymentRecord(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Valid @RequestBody LoanRepaymentRecordDTO dto) {

        return service.create(loanServicingCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{recordId}")
    @Operation(summary = "Get a repayment record by ID")
    public Mono<ResponseEntity<LoanRepaymentRecordDTO>> getRepaymentRecordById(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("recordId") UUID loanRepaymentRecordId) {

        return service.getById(loanServicingCaseId, loanRepaymentRecordId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{recordId}")
    @Operation(summary = "Update a repayment record")
    public Mono<ResponseEntity<LoanRepaymentRecordDTO>> updateRepaymentRecord(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("recordId") UUID loanRepaymentRecordId,
            @Valid @RequestBody LoanRepaymentRecordDTO dto) {

        return service.update(loanServicingCaseId, loanRepaymentRecordId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{recordId}")
    @Operation(summary = "Delete a repayment record")
    public Mono<ResponseEntity<Void>> deleteRepaymentRecord(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("recordId") UUID loanRepaymentRecordId) {

        return service.delete(loanServicingCaseId, loanRepaymentRecordId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}