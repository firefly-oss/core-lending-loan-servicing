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
import com.firefly.core.lending.servicing.core.services.LoanRepaymentScheduleService;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanRepaymentScheduleDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loan-servicing-cases/{caseId}/repayment-schedules")
@Tag(name = "LoanRepaymentSchedule", description = "Operations for Loan Repayment Schedules")
@RequiredArgsConstructor
public class LoanRepaymentScheduleController {

    private final LoanRepaymentScheduleService service;

    @GetMapping
    @Operation(summary = "List/Search repayment schedules for a servicing case")
    public Mono<ResponseEntity<PaginationResponse<LoanRepaymentScheduleDTO>>> findAllRepaymentSchedules(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @ModelAttribute FilterRequest<LoanRepaymentScheduleDTO> filterRequest) {

        return service.findAll(loanServicingCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a repayment schedule entry")
    public Mono<ResponseEntity<LoanRepaymentScheduleDTO>> createRepaymentSchedule(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Valid @RequestBody LoanRepaymentScheduleDTO dto) {

        return service.create(loanServicingCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{scheduleId}")
    @Operation(summary = "Get a repayment schedule entry by ID")
    public Mono<ResponseEntity<LoanRepaymentScheduleDTO>> getRepaymentScheduleById(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("scheduleId") UUID loanRepaymentScheduleId) {

        return service.getById(loanServicingCaseId, loanRepaymentScheduleId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{scheduleId}")
    @Operation(summary = "Update a repayment schedule entry")
    public Mono<ResponseEntity<LoanRepaymentScheduleDTO>> updateRepaymentSchedule(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("scheduleId") UUID loanRepaymentScheduleId,
            @Valid @RequestBody LoanRepaymentScheduleDTO dto) {

        return service.update(loanServicingCaseId, loanRepaymentScheduleId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{scheduleId}")
    @Operation(summary = "Delete a repayment schedule entry")
    public Mono<ResponseEntity<Void>> deleteRepaymentSchedule(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("scheduleId") UUID loanRepaymentScheduleId) {

        return service.delete(loanServicingCaseId, loanRepaymentScheduleId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
