package com.firefly.core.lending.servicing.core.services.servicing.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.interfaces.dtos.servicing.v1.LoanServicingEventDTO;
import reactor.core.publisher.Mono;

public interface LoanServicingEventService {

    /**
     * Retrieves a paginated list of LoanServicingEventDTO records associated with a specific loan servicing case ID,
     * based on the provided filter criteria.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param filterRequest the filtering criteria and pagination details encapsulated in a FilterRequest object
     * @return a Mono containing a PaginationResponse with the list of LoanServicingEventDTO records
     */
    Mono<PaginationResponse<LoanServicingEventDTO>> findAll(Long loanServicingCaseId,
                                                            FilterRequest<LoanServicingEventDTO> filterRequest);

    /**
     * Creates a new loan servicing event for the specified loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case to which the event belongs
     * @param dto the data transfer object containing details of the loan servicing event to be created
     * @return a Mono emitting the created LoanServicingEventDTO instance
     */
    Mono<LoanServicingEventDTO> create(Long loanServicingCaseId, LoanServicingEventDTO dto);

    /**
     * Retrieves the details of a specific loan servicing event for a given loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanServicingEventId the unique identifier of the loan servicing event
     * @return a Mono emitting the LoanServicingEventDTO corresponding to the specified identifiers,
     *         or completes empty if no such loan servicing event exists
     */
    Mono<LoanServicingEventDTO> getById(Long loanServicingCaseId, Long loanServicingEventId);

    /**
     * Updates an existing loan servicing event record with the provided data.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case associated with the loan servicing event
     * @param loanServicingEventId the unique identifier of the loan servicing event to be updated
     * @param dto the data transfer object containing the updated values for the loan servicing event
     * @return a Mono containing the updated LoanServicingEventDTO
     */
    Mono<LoanServicingEventDTO> update(Long loanServicingCaseId, Long loanServicingEventId,
                                       LoanServicingEventDTO dto);

    /**
     * Deletes a loan servicing event associated with a specific loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanServicingEventId the unique identifier of the loan servicing event to be deleted
     * @return a Mono signaling the completion of the delete operation
     */
    Mono<Void> delete(Long loanServicingCaseId, Long loanServicingEventId);
}
