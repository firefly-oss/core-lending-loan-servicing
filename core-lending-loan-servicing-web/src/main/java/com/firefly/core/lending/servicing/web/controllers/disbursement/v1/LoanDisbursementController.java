package com.firefly.core.lending.servicing.web.controllers.disbursement.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.core.services.disbursement.v1.LoanDisbursementService;
import com.firefly.core.lending.servicing.interfaces.dtos.disbursement.v1.LoanDisbursementDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/loan-servicing-cases/{caseId}/disbursements")
@Tag(name = "LoanDisbursement", description = "Operations for Loan Disbursements")
@RequiredArgsConstructor
public class LoanDisbursementController {

    private final LoanDisbursementService service;

    @GetMapping
    @Operation(summary = "List/Search disbursements for a specific servicing case")
    public Mono<ResponseEntity<PaginationResponse<LoanDisbursementDTO>>> findAll(
            @PathVariable("caseId") Long loanServicingCaseId,
            @ModelAttribute FilterRequest<LoanDisbursementDTO> filterRequest) {

        return service.findAll(loanServicingCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new disbursement under a servicing case")
    public Mono<ResponseEntity<LoanDisbursementDTO>> create(
            @PathVariable("caseId") Long loanServicingCaseId,
            @RequestBody LoanDisbursementDTO dto) {

        return service.create(loanServicingCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{disbursementId}")
    @Operation(summary = "Get a disbursement by ID")
    public Mono<ResponseEntity<LoanDisbursementDTO>> getById(
            @PathVariable("caseId") Long loanServicingCaseId,
            @PathVariable("disbursementId") Long loanDisbursementId) {

        return service.getById(loanServicingCaseId, loanDisbursementId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{disbursementId}")
    @Operation(summary = "Update a disbursement record")
    public Mono<ResponseEntity<LoanDisbursementDTO>> update(
            @PathVariable("caseId") Long loanServicingCaseId,
            @PathVariable("disbursementId") Long loanDisbursementId,
            @RequestBody LoanDisbursementDTO dto) {

        return service.update(loanServicingCaseId, loanDisbursementId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{disbursementId}")
    @Operation(summary = "Delete a disbursement record")
    public Mono<ResponseEntity<Void>> delete(
            @PathVariable("caseId") Long loanServicingCaseId,
            @PathVariable("disbursementId") Long loanDisbursementId) {

        return service.delete(loanServicingCaseId, loanDisbursementId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
