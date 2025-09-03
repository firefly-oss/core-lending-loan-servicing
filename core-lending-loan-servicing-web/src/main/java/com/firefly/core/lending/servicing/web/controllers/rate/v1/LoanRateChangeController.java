package com.firefly.core.lending.servicing.web.controllers.rate.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.core.services.rate.v1.LoanRateChangeService;
import com.firefly.core.lending.servicing.interfaces.dtos.rate.v1.LoanRateChangeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loan-servicing-cases/{caseId}/rate-changes")
@Tag(name = "LoanRateChange", description = "Operations for Loan Rate Changes")
@RequiredArgsConstructor
public class LoanRateChangeController {

    private final LoanRateChangeService service;

    @GetMapping
    @Operation(summary = "List/Search rate changes for a servicing case")
    public Mono<ResponseEntity<PaginationResponse<LoanRateChangeDTO>>> findAll(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @ModelAttribute FilterRequest<LoanRateChangeDTO> filterRequest) {

        return service.findAll(loanServicingCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new loan rate change")
    public Mono<ResponseEntity<LoanRateChangeDTO>> create(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Valid @RequestBody LoanRateChangeDTO dto) {

        return service.create(loanServicingCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{rateChangeId}")
    @Operation(summary = "Get a loan rate change by ID")
    public Mono<ResponseEntity<LoanRateChangeDTO>> getById(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("rateChangeId") UUID loanRateChangeId) {

        return service.getById(loanServicingCaseId, loanRateChangeId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{rateChangeId}")
    @Operation(summary = "Update a loan rate change")
    public Mono<ResponseEntity<LoanRateChangeDTO>> update(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("rateChangeId") UUID loanRateChangeId,
            @Valid @RequestBody LoanRateChangeDTO dto) {

        return service.update(loanServicingCaseId, loanRateChangeId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{rateChangeId}")
    @Operation(summary = "Delete a loan rate change record")
    public Mono<ResponseEntity<Void>> delete(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @PathVariable("rateChangeId") UUID loanRateChangeId) {

        return service.delete(loanServicingCaseId, loanRateChangeId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}