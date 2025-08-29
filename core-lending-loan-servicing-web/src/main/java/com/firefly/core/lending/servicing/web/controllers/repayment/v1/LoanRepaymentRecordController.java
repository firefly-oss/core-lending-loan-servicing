package com.firefly.core.lending.servicing.web.controllers.repayment.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.core.services.repayment.v1.LoanRepaymentRecordService;
import com.firefly.core.lending.servicing.interfaces.dtos.repayment.v1.LoanRepaymentRecordDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/loan-servicing-cases/{caseId}/repayment-records")
@Tag(name = "LoanRepaymentRecord", description = "Operations for Loan Repayment Records")
@RequiredArgsConstructor
public class LoanRepaymentRecordController {

    private final LoanRepaymentRecordService service;

    @GetMapping
    @Operation(summary = "List/Search repayment records for a servicing case")
    public Mono<ResponseEntity<PaginationResponse<LoanRepaymentRecordDTO>>> findAll(
            @PathVariable("caseId") Long loanServicingCaseId,
            @ModelAttribute FilterRequest<LoanRepaymentRecordDTO> filterRequest) {

        return service.findAll(loanServicingCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a repayment record")
    public Mono<ResponseEntity<LoanRepaymentRecordDTO>> create(
            @PathVariable("caseId") Long loanServicingCaseId,
            @RequestBody LoanRepaymentRecordDTO dto) {

        return service.create(loanServicingCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{recordId}")
    @Operation(summary = "Get a repayment record by ID")
    public Mono<ResponseEntity<LoanRepaymentRecordDTO>> getById(
            @PathVariable("caseId") Long loanServicingCaseId,
            @PathVariable("recordId") Long loanRepaymentRecordId) {

        return service.getById(loanServicingCaseId, loanRepaymentRecordId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{recordId}")
    @Operation(summary = "Update a repayment record")
    public Mono<ResponseEntity<LoanRepaymentRecordDTO>> update(
            @PathVariable("caseId") Long loanServicingCaseId,
            @PathVariable("recordId") Long loanRepaymentRecordId,
            @RequestBody LoanRepaymentRecordDTO dto) {

        return service.update(loanServicingCaseId, loanRepaymentRecordId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{recordId}")
    @Operation(summary = "Delete a repayment record")
    public Mono<ResponseEntity<Void>> delete(
            @PathVariable("caseId") Long loanServicingCaseId,
            @PathVariable("recordId") Long loanRepaymentRecordId) {

        return service.delete(loanServicingCaseId, loanRepaymentRecordId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}