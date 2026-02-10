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
import com.firefly.core.lending.servicing.interfaces.dtos.LoanRestructuringDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface LoanRestructuringService {

    /**
     * Retrieves a paginated list of LoanRestructuringDTO records for a specific loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param filterRequest the filtering criteria and pagination details
     * @return a Mono containing a PaginationResponse with the list of LoanRestructuringDTO records
     */
    Mono<PaginationResponse<LoanRestructuringDTO>> findAll(UUID loanServicingCaseId, FilterRequest<LoanRestructuringDTO> filterRequest);

    /**
     * Creates a new loan restructuring record for the specified loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param dto the data transfer object containing restructuring details
     * @return a Mono emitting the created LoanRestructuringDTO instance
     */
    Mono<LoanRestructuringDTO> create(UUID loanServicingCaseId, LoanRestructuringDTO dto);

    /**
     * Retrieves a specific loan restructuring record by ID.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanRestructuringId the unique identifier of the restructuring record
     * @return a Mono emitting the LoanRestructuringDTO, or empty if not found
     */
    Mono<LoanRestructuringDTO> getById(UUID loanServicingCaseId, UUID loanRestructuringId);

    /**
     * Updates an existing loan restructuring record.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanRestructuringId the unique identifier of the restructuring record to update
     * @param dto the data transfer object containing updated restructuring values
     * @return a Mono containing the updated LoanRestructuringDTO
     */
    Mono<LoanRestructuringDTO> update(UUID loanServicingCaseId, UUID loanRestructuringId, LoanRestructuringDTO dto);

    /**
     * Deletes a loan restructuring record.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanRestructuringId the unique identifier of the restructuring record to delete
     * @return a Mono signaling completion of the delete operation
     */
    Mono<Void> delete(UUID loanServicingCaseId, UUID loanRestructuringId);
}

