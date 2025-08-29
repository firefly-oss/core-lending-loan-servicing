package com.firefly.core.lending.servicing.web.controllers.servicing.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.core.services.servicing.v1.LoanServicingEventService;
import com.firefly.core.lending.servicing.interfaces.dtos.servicing.v1.LoanServicingEventDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/loan-servicing-cases/{caseId}/events")
@Tag(name = "LoanServicingEvent", description = "Operations for Loan Servicing Events")
@RequiredArgsConstructor
public class LoanServicingEventController {

    private final LoanServicingEventService service;

    @GetMapping
    @Operation(summary = "List/Search loan servicing events")
    public Mono<ResponseEntity<PaginationResponse<LoanServicingEventDTO>>> findAll(
            @PathVariable("caseId") Long loanServicingCaseId,
            @ModelAttribute FilterRequest<LoanServicingEventDTO> filterRequest) {

        return service.findAll(loanServicingCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new loan servicing event")
    public Mono<ResponseEntity<LoanServicingEventDTO>> create(
            @PathVariable("caseId") Long loanServicingCaseId,
            @RequestBody LoanServicingEventDTO dto) {

        return service.create(loanServicingCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{eventId}")
    @Operation(summary = "Get a loan servicing event by ID")
    public Mono<ResponseEntity<LoanServicingEventDTO>> getById(
            @PathVariable("caseId") Long loanServicingCaseId,
            @PathVariable("eventId") Long loanServicingEventId) {

        return service.getById(loanServicingCaseId, loanServicingEventId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{eventId}")
    @Operation(summary = "Update a loan servicing event")
    public Mono<ResponseEntity<LoanServicingEventDTO>> update(
            @PathVariable("caseId") Long loanServicingCaseId,
            @PathVariable("eventId") Long loanServicingEventId,
            @RequestBody LoanServicingEventDTO dto) {

        return service.update(loanServicingCaseId, loanServicingEventId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{eventId}")
    @Operation(summary = "Delete a loan servicing event")
    public Mono<ResponseEntity<Void>> delete(
            @PathVariable("caseId") Long loanServicingCaseId,
            @PathVariable("eventId") Long loanServicingEventId) {

        return service.delete(loanServicingCaseId, loanServicingEventId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
