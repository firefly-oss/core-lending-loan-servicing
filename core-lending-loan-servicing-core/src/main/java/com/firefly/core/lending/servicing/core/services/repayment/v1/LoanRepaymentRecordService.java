package com.firefly.core.lending.servicing.core.services.repayment.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.interfaces.dtos.repayment.v1.LoanRepaymentRecordDTO;
import reactor.core.publisher.Mono;

public interface LoanRepaymentRecordService {

    /**
     * Retrieves a paginated list of LoanRepaymentRecordDTO records associated with a specific loan servicing case ID,
     * based on the provided filter criteria.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param filterRequest the filtering criteria and pagination details encapsulated in a FilterRequest object
     * @return a Mono containing a PaginationResponse with the list of LoanRepaymentRecordDTO records
     */
    Mono<PaginationResponse<LoanRepaymentRecordDTO>> findAll(Long loanServicingCaseId,
                                                             FilterRequest<LoanRepaymentRecordDTO> filterRequest);

    /**
     * Creates a new loan repayment record for the specified loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case associated with the repayment record
     * @param dto the data transfer object containing details of the loan repayment record to be created
     * @return a Mono emitting the created LoanRepaymentRecordDTO instance
     */
    Mono<LoanRepaymentRecordDTO> create(Long loanServicingCaseId, LoanRepaymentRecordDTO dto);

    /**
     * Retrieves the details of a specific loan repayment record for a given loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanRepaymentRecordId the unique identifier of the loan repayment record
     * @return a Mono emitting the LoanRepaymentRecordDTO corresponding to the specified identifiers,
     *         or completes empty if no such loan repayment record exists
     */
    Mono<LoanRepaymentRecordDTO> getById(Long loanServicingCaseId, Long loanRepaymentRecordId);

    /**
     * Updates an existing loan repayment record with the provided data.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case associated with the loan repayment record
     * @param loanRepaymentRecordId the unique identifier of the loan repayment record to be updated
     * @param dto the data transfer object containing the updated values for the loan repayment record
     * @return a Mono containing the updated LoanRepaymentRecordDTO
     */
    Mono<LoanRepaymentRecordDTO> update(Long loanServicingCaseId, Long loanRepaymentRecordId,
                                        LoanRepaymentRecordDTO dto);

    /**
     * Deletes a loan repayment record associated with a specific loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanRepaymentRecordId the unique identifier of the loan repayment record to be deleted
     * @return a Mono signaling the completion of the delete operation
     */
    Mono<Void> delete(Long loanServicingCaseId, Long loanRepaymentRecordId);
}