package com.firefly.core.lending.servicing.web.controllers.accrual.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.core.services.accrual.v1.LoanAccrualService;
import com.firefly.core.lending.servicing.interfaces.dtos.accrual.v1.LoanAccrualDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/loan-servicing-cases/{caseId}/accruals")
@Tag(name = "LoanAccrual", description = "Operations for Loan Accruals (interest, fees, etc.)")
@RequiredArgsConstructor
public class LoanAccrualController {

    private final LoanAccrualService service;

    @GetMapping
    @Operation(summary = "List/Search accruals for a servicing case")
    public Mono<ResponseEntity<PaginationResponse<LoanAccrualDTO>>> findAll(
            @PathVariable("caseId") Long loanServicingCaseId,
            @ModelAttribute FilterRequest<LoanAccrualDTO> filterRequest) {

        return service.findAll(loanServicingCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a loan accrual record")
    public Mono<ResponseEntity<LoanAccrualDTO>> create(
            @PathVariable("caseId") Long loanServicingCaseId,
            @RequestBody LoanAccrualDTO dto) {

        return service.create(loanServicingCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{accrualId}")
    @Operation(summary = "Get a loan accrual by ID")
    public Mono<ResponseEntity<LoanAccrualDTO>> getById(
            @PathVariable("caseId") Long loanServicingCaseId,
            @PathVariable("accrualId") Long loanAccrualId) {

        return service.getById(loanServicingCaseId, loanAccrualId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{accrualId}")
    @Operation(summary = "Update a loan accrual record")
    public Mono<ResponseEntity<LoanAccrualDTO>> update(
            @PathVariable("caseId") Long loanServicingCaseId,
            @PathVariable("accrualId") Long loanAccrualId,
            @RequestBody LoanAccrualDTO dto) {

        return service.update(loanServicingCaseId, loanAccrualId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{accrualId}")
    @Operation(summary = "Delete a loan accrual record")
    public Mono<ResponseEntity<Void>> delete(
            @PathVariable("caseId") Long loanServicingCaseId,
            @PathVariable("accrualId") Long loanAccrualId) {

        return service.delete(loanServicingCaseId, loanAccrualId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
