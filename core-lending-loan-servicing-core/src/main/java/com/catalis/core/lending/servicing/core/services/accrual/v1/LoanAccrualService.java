package com.catalis.core.lending.servicing.core.services.accrual.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.lending.servicing.interfaces.dtos.accrual.v1.LoanAccrualDTO;
import reactor.core.publisher.Mono;

public interface LoanAccrualService {

    /**
     * Retrieves a paginated list of LoanAccrualDTO records associated with a specific loan servicing case ID,
     * based on the provided filter criteria.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param filterRequest the filtering criteria and pagination details encapsulated in a FilterRequest object
     * @return a Mono containing a PaginationResponse with the list of LoanAccrualDTO records
     */
    Mono<PaginationResponse<LoanAccrualDTO>> findAll(Long loanServicingCaseId, FilterRequest<LoanAccrualDTO> filterRequest);

    /**
     * Creates a new loan accrual record for the specified loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case to which the accrual belongs
     * @param dto the data transfer object containing details of the loan accrual to be created
     * @return a Mono emitting the created LoanAccrualDTO instance
     */
    Mono<LoanAccrualDTO> create(Long loanServicingCaseId, LoanAccrualDTO dto);

    /**
     * Retrieves the details of a specific loan accrual for a given loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanAccrualId the unique identifier of the loan accrual
     * @return a Mono emitting the LoanAccrualDTO corresponding to the specified identifiers,
     *         or completes empty if no such loan accrual exists
     */
    Mono<LoanAccrualDTO> getById(Long loanServicingCaseId, Long loanAccrualId);

    /**
     * Updates an existing loan accrual record with the provided data.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case associated with the loan accrual
     * @param loanAccrualId the unique identifier of the loan accrual to be updated
     * @param dto the data transfer object containing the updated values for the loan accrual
     * @return a Mono containing the updated LoanAccrualDTO
     */
    Mono<LoanAccrualDTO> update(Long loanServicingCaseId, Long loanAccrualId, LoanAccrualDTO dto);

    /**
     * Deletes a loan accrual associated with a specific loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanAccrualId the unique identifier of the loan accrual to be deleted
     * @return a Mono signaling the completion of the delete operation
     */
    Mono<Void> delete(Long loanServicingCaseId, Long loanAccrualId);
}
