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
import com.firefly.core.lending.servicing.interfaces.dtos.LoanAccrualDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface LoanAccrualService {

    /**
     * Retrieves a paginated list of LoanAccrualDTO records associated with a specific loan servicing case ID,
     * based on the provided filter criteria.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param filterRequest the filtering criteria and pagination details encapsulated in a FilterRequest object
     * @return a Mono containing a PaginationResponse with the list of LoanAccrualDTO records
     */
    Mono<PaginationResponse<LoanAccrualDTO>> findAll(UUID loanServicingCaseId, FilterRequest<LoanAccrualDTO> filterRequest);

    /**
     * Creates a new loan accrual record for the specified loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case to which the accrual belongs
     * @param dto the data transfer object containing details of the loan accrual to be created
     * @return a Mono emitting the created LoanAccrualDTO instance
     */
    Mono<LoanAccrualDTO> create(UUID loanServicingCaseId, LoanAccrualDTO dto);

    /**
     * Retrieves the details of a specific loan accrual for a given loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanAccrualId the unique identifier of the loan accrual
     * @return a Mono emitting the LoanAccrualDTO corresponding to the specified identifiers,
     *         or completes empty if no such loan accrual exists
     */
    Mono<LoanAccrualDTO> getById(UUID loanServicingCaseId, UUID loanAccrualId);

    /**
     * Updates an existing loan accrual record with the provided data.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case associated with the loan accrual
     * @param loanAccrualId the unique identifier of the loan accrual to be updated
     * @param dto the data transfer object containing the updated values for the loan accrual
     * @return a Mono containing the updated LoanAccrualDTO
     */
    Mono<LoanAccrualDTO> update(UUID loanServicingCaseId, UUID loanAccrualId, LoanAccrualDTO dto);

    /**
     * Deletes a loan accrual associated with a specific loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanAccrualId the unique identifier of the loan accrual to be deleted
     * @return a Mono signaling the completion of the delete operation
     */
    Mono<Void> delete(UUID loanServicingCaseId, UUID loanAccrualId);
}
