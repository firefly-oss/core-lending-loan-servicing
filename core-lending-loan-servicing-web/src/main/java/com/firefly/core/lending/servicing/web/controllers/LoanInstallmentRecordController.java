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
import com.firefly.core.lending.servicing.core.services.repayment.v1.LoanInstallmentRecordService;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanInstallmentRecordDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loan-servicing-cases/{caseId}/installment-records")
@Tag(name = "LoanInstallmentRecord", description = "Operations for Loan Installment Records")
@RequiredArgsConstructor
public class LoanInstallmentRecordController {

    private final LoanInstallmentRecordService service;

    @GetMapping
    @Operation(summary = "List/Search installment records for a servicing case")
    public Mono<ResponseEntity<PaginationResponse<LoanInstallmentRecordDTO>>> findAllInstallmentRecords(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @ModelAttribute FilterRequest<LoanInstallmentRecordDTO> filterRequest) {

        return service.findAll(loanServicingCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create an installment record")
    public Mono<ResponseEntity<LoanInstallmentRecordDTO>> createInstallmentRecord(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Valid @RequestBody LoanInstallmentRecordDTO dto) {

        return service.create(loanServicingCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{recordId}")
    @Operation(summary = "Get an installment record by ID")
    public Mono<ResponseEntity<LoanInstallmentRecordDTO>> getInstallmentRecordById(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("recordId") UUID loanInstallmentRecordId) {

        return service.getById(loanServicingCaseId, loanInstallmentRecordId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{recordId}")
    @Operation(summary = "Update an installment record")
    public Mono<ResponseEntity<LoanInstallmentRecordDTO>> updateInstallmentRecord(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("recordId") UUID loanInstallmentRecordId,
            @Valid @RequestBody LoanInstallmentRecordDTO dto) {

        return service.update(loanServicingCaseId, loanInstallmentRecordId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{recordId}")
    @Operation(summary = "Delete an installment record")
    public Mono<ResponseEntity<Void>> deleteInstallmentRecord(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("recordId") UUID loanInstallmentRecordId) {

        return service.delete(loanServicingCaseId, loanInstallmentRecordId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}

