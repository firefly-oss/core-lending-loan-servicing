package com.firefly.core.lending.servicing.core.services.disbursement.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.interfaces.dtos.disbursement.v1.LoanDisbursementDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface LoanDisbursementService {

    /**
     * Retrieves a paginated list of LoanDisbursementDTO records associated with a specific loan servicing case ID,
     * based on the provided filter criteria.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param filterRequest the filtering criteria and pagination details encapsulated in a FilterRequest object
     * @return a Mono containing a PaginationResponse with the list of LoanDisbursementDTO records
     */
    Mono<PaginationResponse<LoanDisbursementDTO>> findAll(UUID loanServicingCaseId,
                                                          FilterRequest<LoanDisbursementDTO> filterRequest);

    /**
     * Creates a new loan disbursement for a specific loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case to which the disbursement belongs
     * @param dto the data transfer object containing details of the loan disbursement to be created
     * @return a Mono emitting the created LoanDisbursementDTO instance
     */
    Mono<LoanDisbursementDTO> create(UUID loanServicingCaseId, LoanDisbursementDTO dto);

    /**
     * Retrieves the details of a specific loan disbursement for a given loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanDisbursementId the unique identifier of the loan disbursement
     * @return a Mono emitting the LoanDisbursementDTO corresponding to the specified identifiers,
     *         or completes empty if no such loan disbursement exists
     */
    Mono<LoanDisbursementDTO> getById(UUID loanServicingCaseId, UUID loanDisbursementId);

    /**
     * Updates an existing loan disbursement record with the provided data.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case associated with the loan disbursement
     * @param loanDisbursementId the unique identifier of the loan disbursement to be updated
     * @param dto the data transfer object containing the updated values for the loan disbursement
     * @return a Mono containing the updated LoanDisbursementDTO
     */
    Mono<LoanDisbursementDTO> update(UUID loanServicingCaseId, UUID loanDisbursementId, LoanDisbursementDTO dto);

    /**
     * Deletes a loan disbursement associated with a specified loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanDisbursementId the unique identifier of the loan disbursement to be deleted
     * @return a Mono signaling the completion of the delete operation
     */
    Mono<Void> delete(UUID loanServicingCaseId, UUID loanDisbursementId);
}
