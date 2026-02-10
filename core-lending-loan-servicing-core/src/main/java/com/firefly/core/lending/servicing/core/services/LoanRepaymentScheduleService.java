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

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanRepaymentScheduleDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface LoanRepaymentScheduleService {

    /**
     * Retrieves a paginated list of LoanRepaymentScheduleDTO records associated with a specific loan servicing case ID,
     * based on the provided filter criteria.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param filterRequest the filtering criteria and pagination details encapsulated in a FilterRequest object
     * @return a Mono containing a PaginationResponse with the list of LoanRepaymentScheduleDTO records
     */
    Mono<PaginationResponse<LoanRepaymentScheduleDTO>> findAll(UUID loanServicingCaseId,
                                                               FilterRequest<LoanRepaymentScheduleDTO> filterRequest);

    /**
     * Creates a new loan repayment schedule for a specific loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case to which the repayment schedule belongs
     * @param dto the data transfer object containing details of the loan repayment schedule to be created
     * @return a Mono emitting the created LoanRepaymentScheduleDTO instance
     */
    Mono<LoanRepaymentScheduleDTO> create(UUID loanServicingCaseId, LoanRepaymentScheduleDTO dto);

    /**
     * Retrieves the details of a specific loan repayment schedule for a given loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanRepaymentScheduleId the unique identifier of the loan repayment schedule
     * @return a Mono emitting the LoanRepaymentScheduleDTO corresponding to the specified identifiers,
     *         or completes empty if no such loan repayment schedule exists
     */
    Mono<LoanRepaymentScheduleDTO> getById(UUID loanServicingCaseId, UUID loanRepaymentScheduleId);

    /**
     * Updates an existing loan repayment schedule record with the provided data.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case associated with the loan repayment schedule
     * @param loanRepaymentScheduleId the unique identifier of the loan repayment schedule to be updated
     * @param dto the data transfer object containing the updated values for the loan repayment schedule
     * @return a Mono containing the updated LoanRepaymentScheduleDTO
     */
    Mono<LoanRepaymentScheduleDTO> update(UUID loanServicingCaseId, UUID loanRepaymentScheduleId,
                                          LoanRepaymentScheduleDTO dto);

    /**
     * Deletes a loan repayment schedule associated with the specified loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanRepaymentScheduleId the unique identifier of the loan repayment schedule to be deleted
     * @return a Mono signaling the completion of the delete operation
     */
    Mono<Void> delete(UUID loanServicingCaseId, UUID loanRepaymentScheduleId);
}
