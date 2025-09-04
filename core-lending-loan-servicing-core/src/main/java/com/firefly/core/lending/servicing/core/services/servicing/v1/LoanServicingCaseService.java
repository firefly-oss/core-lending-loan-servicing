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


package com.firefly.core.lending.servicing.core.services.servicing.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.interfaces.dtos.servicing.v1.LoanServicingCaseDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

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
    Mono<LoanServicingCaseDTO> getById(UUID loanServicingCaseId);

    /**
     * Updates an existing loan servicing case with the provided data.
     *
     * @param loanServicingCaseId The unique identifier of the loan servicing case to be updated.
     * @param dto The data transfer object containing the updated values for the loan servicing case.
     * @return A Mono containing the updated LoanServicingCaseDTO.
     */
    Mono<LoanServicingCaseDTO> update(UUID loanServicingCaseId, LoanServicingCaseDTO dto);

    /**
     * Deletes a loan servicing case by its unique identifier.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case to be deleted
     * @return a Mono signaling the completion of the delete operation
     */
    Mono<Void> delete(UUID loanServicingCaseId);
}