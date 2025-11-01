/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.lending.servicing.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanInstallmentRecordDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface LoanInstallmentRecordService {

    /**
     * Retrieves a paginated list of LoanInstallmentRecordDTO records associated with a specific loan servicing case ID,
     * based on the provided filter criteria.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param filterRequest the filtering criteria and pagination details encapsulated in a FilterRequest object
     * @return a Mono containing a PaginationResponse with the list of LoanInstallmentRecordDTO records
     */
    Mono<PaginationResponse<LoanInstallmentRecordDTO>> findAll(UUID loanServicingCaseId,
                                                                FilterRequest<LoanInstallmentRecordDTO> filterRequest);

    /**
     * Creates a new loan installment record for a specific loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case to which the installment record belongs
     * @param dto the data transfer object containing details of the loan installment record to be created
     * @return a Mono emitting the created LoanInstallmentRecordDTO instance
     */
    Mono<LoanInstallmentRecordDTO> create(UUID loanServicingCaseId, LoanInstallmentRecordDTO dto);

    /**
     * Retrieves the details of a specific loan installment record for a given loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanInstallmentRecordId the unique identifier of the loan installment record
     * @return a Mono emitting the LoanInstallmentRecordDTO corresponding to the specified identifiers,
     *         or completes empty if no such loan installment record exists
     */
    Mono<LoanInstallmentRecordDTO> getById(UUID loanServicingCaseId, UUID loanInstallmentRecordId);

    /**
     * Updates an existing loan installment record with the provided data.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case associated with the loan installment record
     * @param loanInstallmentRecordId the unique identifier of the loan installment record to be updated
     * @param dto the data transfer object containing the updated values for the loan installment record
     * @return a Mono containing the updated LoanInstallmentRecordDTO
     */
    Mono<LoanInstallmentRecordDTO> update(UUID loanServicingCaseId, UUID loanInstallmentRecordId,
                                          LoanInstallmentRecordDTO dto);

    /**
     * Deletes a loan installment record associated with the specified loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanInstallmentRecordId the unique identifier of the loan installment record to be deleted
     * @return a Mono signaling the completion of the delete operation
     */
    Mono<Void> delete(UUID loanServicingCaseId, UUID loanInstallmentRecordId);
}

