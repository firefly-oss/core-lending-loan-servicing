package com.firefly.core.lending.servicing.core.services.rate.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.interfaces.dtos.rate.v1.LoanRateChangeDTO;
import reactor.core.publisher.Mono;

public interface LoanRateChangeService {

    /**
     * Retrieves a paginated list of LoanRateChangeDTO records associated with a specific loan servicing case ID,
     * based on the provided filter criteria.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param filterRequest the filtering criteria and pagination details encapsulated in a FilterRequest object
     * @return a Mono containing a PaginationResponse with the list of LoanRateChangeDTO records
     */
    Mono<PaginationResponse<LoanRateChangeDTO>> findAll(Long loanServicingCaseId, FilterRequest<LoanRateChangeDTO> filterRequest);

    /**
     * Creates a new loan rate change record for the specified loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case to which the rate change belongs
     * @param dto the data transfer object containing details of the loan rate change to be created
     * @return a Mono emitting the created LoanRateChangeDTO instance
     */
    Mono<LoanRateChangeDTO> create(Long loanServicingCaseId, LoanRateChangeDTO dto);

    /**
     * Retrieves the details of a specific loan rate change for a given loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanRateChangeId the unique identifier of the loan rate change
     * @return a Mono emitting the LoanRateChangeDTO corresponding to the specified identifiers,
     *         or completes empty if no such loan rate change exists
     */
    Mono<LoanRateChangeDTO> getById(Long loanServicingCaseId, Long loanRateChangeId);

    /**
     * Updates an existing loan rate change record associated with the specified loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case associated with the loan rate change
     * @param loanRateChangeId the unique identifier of the loan rate change record to be updated
     * @param dto the data transfer object containing the updated values for the loan rate change
     * @return a Mono containing the updated LoanRateChangeDTO
     */
    Mono<LoanRateChangeDTO> update(Long loanServicingCaseId, Long loanRateChangeId, LoanRateChangeDTO dto);

    /**
     * Deletes a loan rate change associated with a specified loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanRateChangeId the unique identifier of the loan rate change to be deleted
     * @return a Mono signaling the completion of the delete operation
     */
    Mono<Void> delete(Long loanServicingCaseId, Long loanRateChangeId);
}