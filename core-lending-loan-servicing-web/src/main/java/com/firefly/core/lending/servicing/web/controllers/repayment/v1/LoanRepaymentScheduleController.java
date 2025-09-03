package com.firefly.core.lending.servicing.web.controllers.repayment.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.core.services.repayment.v1.LoanRepaymentScheduleService;
import com.firefly.core.lending.servicing.interfaces.dtos.repayment.v1.LoanRepaymentScheduleDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loan-servicing-cases/{caseId}/repayment-schedules")
@Tag(name = "LoanRepaymentSchedule", description = "Operations for Loan Repayment Schedules")
@RequiredArgsConstructor
public class LoanRepaymentScheduleController {

    private final LoanRepaymentScheduleService service;

    @GetMapping
    @Operation(summary = "List/Search repayment schedules for a servicing case")
    public Mono<ResponseEntity<PaginationResponse<LoanRepaymentScheduleDTO>>> findAll(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @ModelAttribute FilterRequest<LoanRepaymentScheduleDTO> filterRequest) {

        return service.findAll(loanServicingCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a repayment schedule entry")
    public Mono<ResponseEntity<LoanRepaymentScheduleDTO>> create(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Valid @RequestBody LoanRepaymentScheduleDTO dto) {

        return service.create(loanServicingCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{scheduleId}")
    @Operation(summary = "Get a repayment schedule entry by ID")
    public Mono<ResponseEntity<LoanRepaymentScheduleDTO>> getById(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("scheduleId") UUID loanRepaymentScheduleId) {

        return service.getById(loanServicingCaseId, loanRepaymentScheduleId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{scheduleId}")
    @Operation(summary = "Update a repayment schedule entry")
    public Mono<ResponseEntity<LoanRepaymentScheduleDTO>> update(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("scheduleId") UUID loanRepaymentScheduleId,
            @Valid @RequestBody LoanRepaymentScheduleDTO dto) {

        return service.update(loanServicingCaseId, loanRepaymentScheduleId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{scheduleId}")
    @Operation(summary = "Delete a repayment schedule entry")
    public Mono<ResponseEntity<Void>> delete(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("scheduleId") UUID loanRepaymentScheduleId) {

        return service.delete(loanServicingCaseId, loanRepaymentScheduleId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
