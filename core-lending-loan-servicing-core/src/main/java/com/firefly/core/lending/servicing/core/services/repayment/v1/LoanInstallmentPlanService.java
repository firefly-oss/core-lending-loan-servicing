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


package com.firefly.core.lending.servicing.core.services.repayment.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.interfaces.dtos.repayment.v1.LoanInstallmentPlanDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface LoanInstallmentPlanService {

    /**
     * Retrieves a paginated list of LoanInstallmentPlanDTO records associated with a specific loan servicing case ID,
     * based on the provided filter criteria.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param filterRequest the filtering criteria and pagination details encapsulated in a FilterRequest object
     * @return a Mono containing a PaginationResponse with the list of LoanInstallmentPlanDTO records
     */
    Mono<PaginationResponse<LoanInstallmentPlanDTO>> findAll(UUID loanServicingCaseId,
                                                              FilterRequest<LoanInstallmentPlanDTO> filterRequest);

    /**
     * Creates a new loan installment plan for a specific loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case to which the installment plan belongs
     * @param dto the data transfer object containing details of the loan installment plan to be created
     * @return a Mono emitting the created LoanInstallmentPlanDTO instance
     */
    Mono<LoanInstallmentPlanDTO> create(UUID loanServicingCaseId, LoanInstallmentPlanDTO dto);

    /**
     * Retrieves the details of a specific loan installment plan for a given loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanInstallmentPlanId the unique identifier of the loan installment plan
     * @return a Mono emitting the LoanInstallmentPlanDTO corresponding to the specified identifiers,
     *         or completes empty if no such loan installment plan exists
     */
    Mono<LoanInstallmentPlanDTO> getById(UUID loanServicingCaseId, UUID loanInstallmentPlanId);

    /**
     * Updates an existing loan installment plan record with the provided data.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case associated with the loan installment plan
     * @param loanInstallmentPlanId the unique identifier of the loan installment plan to be updated
     * @param dto the data transfer object containing the updated values for the loan installment plan
     * @return a Mono containing the updated LoanInstallmentPlanDTO
     */
    Mono<LoanInstallmentPlanDTO> update(UUID loanServicingCaseId, UUID loanInstallmentPlanId,
                                        LoanInstallmentPlanDTO dto);

    /**
     * Deletes a loan installment plan associated with the specified loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanInstallmentPlanId the unique identifier of the loan installment plan to be deleted
     * @return a Mono signaling the completion of the delete operation
     */
    Mono<Void> delete(UUID loanServicingCaseId, UUID loanInstallmentPlanId);
}

