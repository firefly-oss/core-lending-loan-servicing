package com.catalis.core.lending.servicing.core.services.servicing.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.lending.servicing.interfaces.dtos.servicing.v1.LoanServicingCaseDTO;
import reactor.core.publisher.Mono;

public interface LoanServicingCaseService {
    /**
     * Retrieves a paginated list of LoanServicingCaseDTO records based on the given filter criteria.
     *
     * @param filterRequest the filtering criteria and pagination details encapsulated in a FilterRequest object
     * @return a Mono containing a PaginationResponse with the list of LoanServicingCaseDTO records
     */
    Mono<PaginationResponse<LoanServicingCaseDTO>> findAll(FilterRequest<LoanServicingCaseDTO> filterRequest);

    /**
     * Creates a new loan servicing case record.
     *
     * @param dto the data transfer object containing loan servicing case information to be created
     * @return a Mono emitting the created LoanServicingCaseDTO instance
     */
    Mono<LoanServicingCaseDTO> create(LoanServicingCaseDTO dto);

    /**
     * Retrieves the loan servicing case details by its unique identifier.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @return a Mono emitting the {@code LoanServicingCaseDTO} corresponding to the provided ID,
     *         or completes empty if no such loan servicing case exists
     */
    Mono<LoanServicingCaseDTO> getById(Long loanServicingCaseId);

    /**
     * Updates an existing loan servicing case with the provided data.
     *
     * @param loanServicingCaseId The unique identifier of the loan servicing case to be updated.
     * @param dto The data transfer object containing the updated values for the loan servicing case.
     * @return A Mono containing the updated LoanServicingCaseDTO.
     */
    Mono<LoanServicingCaseDTO> update(Long loanServicingCaseId, LoanServicingCaseDTO dto);

    /**
     * Deletes a loan servicing case by its unique identifier.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case to be deleted
     * @return a Mono signaling the completion of the delete operation
     */
    Mono<Void> delete(Long loanServicingCaseId);
}