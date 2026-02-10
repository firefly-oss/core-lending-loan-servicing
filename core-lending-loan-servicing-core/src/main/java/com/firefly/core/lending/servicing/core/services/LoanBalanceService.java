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
import com.firefly.core.lending.servicing.interfaces.dtos.LoanBalanceDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface LoanBalanceService {

    /**
     * Retrieves a paginated list of LoanBalanceDTO records (balance history) for a specific loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param filterRequest the filtering criteria and pagination details
     * @return a Mono containing a PaginationResponse with the list of LoanBalanceDTO records
     */
    Mono<PaginationResponse<LoanBalanceDTO>> findAll(UUID loanServicingCaseId, FilterRequest<LoanBalanceDTO> filterRequest);

    /**
     * Retrieves the current balance for a specific loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @return a Mono emitting the current LoanBalanceDTO, or empty if no balance exists
     */
    Mono<LoanBalanceDTO> getCurrentBalance(UUID loanServicingCaseId);

    /**
     * Creates a new balance snapshot for the specified loan servicing case.
     * This will mark the previous current balance as historical.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param dto the data transfer object containing balance details
     * @return a Mono emitting the created LoanBalanceDTO instance
     */
    Mono<LoanBalanceDTO> create(UUID loanServicingCaseId, LoanBalanceDTO dto);

    /**
     * Retrieves a specific balance snapshot by ID.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanBalanceId the unique identifier of the balance snapshot
     * @return a Mono emitting the LoanBalanceDTO, or empty if not found
     */
    Mono<LoanBalanceDTO> getById(UUID loanServicingCaseId, UUID loanBalanceId);

    /**
     * Updates an existing balance snapshot.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanBalanceId the unique identifier of the balance snapshot to update
     * @param dto the data transfer object containing updated balance values
     * @return a Mono containing the updated LoanBalanceDTO
     */
    Mono<LoanBalanceDTO> update(UUID loanServicingCaseId, UUID loanBalanceId, LoanBalanceDTO dto);

    /**
     * Deletes a balance snapshot.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanBalanceId the unique identifier of the balance snapshot to delete
     * @return a Mono signaling completion of the delete operation
     */
    Mono<Void> delete(UUID loanServicingCaseId, UUID loanBalanceId);
}

