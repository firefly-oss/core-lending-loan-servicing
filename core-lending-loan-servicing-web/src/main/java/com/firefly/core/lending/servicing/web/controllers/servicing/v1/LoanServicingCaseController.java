package com.firefly.core.lending.servicing.web.controllers.servicing.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.core.services.servicing.v1.LoanServicingCaseService;
import com.firefly.core.lending.servicing.interfaces.dtos.servicing.v1.LoanServicingCaseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loan-servicing-cases")
@Tag(name = "LoanServicingCase", description = "Operations for Loan Servicing Cases")
@RequiredArgsConstructor
public class LoanServicingCaseController {

    private final LoanServicingCaseService service;

    @GetMapping
    @Operation(summary = "List/Search loan servicing cases")
    public Mono<ResponseEntity<PaginationResponse<LoanServicingCaseDTO>>> findAll(
            @ModelAttribute FilterRequest<LoanServicingCaseDTO> filterRequest) {

        return service.findAll(filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a loan servicing case")
    public Mono<ResponseEntity<LoanServicingCaseDTO>> create(@Valid @RequestBody LoanServicingCaseDTO dto) {
        return service.create(dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{caseId}")
    @Operation(summary = "Get a loan servicing case by ID")
    public Mono<ResponseEntity<LoanServicingCaseDTO>> getById(@PathVariable("caseId") UUID loanServicingCaseId) {
        return service.getById(loanServicingCaseId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{caseId}")
    @Operation(summary = "Update a loan servicing case")
    public Mono<ResponseEntity<LoanServicingCaseDTO>> update(
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Valid @RequestBody LoanServicingCaseDTO dto) {

        return service.update(loanServicingCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{caseId}")
    @Operation(summary = "Delete a loan servicing case")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("caseId") UUID loanServicingCaseId) {
        return service.delete(loanServicingCaseId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}