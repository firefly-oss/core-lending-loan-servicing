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
import com.firefly.core.lending.servicing.interfaces.dtos.LoanServicingEventDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface LoanServicingEventService {

    /**
     * Retrieves a paginated list of LoanServicingEventDTO records associated with a specific loan servicing case ID,
     * based on the provided filter criteria.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param filterRequest the filtering criteria and pagination details encapsulated in a FilterRequest object
     * @return a Mono containing a PaginationResponse with the list of LoanServicingEventDTO records
     */
    Mono<PaginationResponse<LoanServicingEventDTO>> findAll(UUID loanServicingCaseId,
                                                            FilterRequest<LoanServicingEventDTO> filterRequest);

    /**
     * Creates a new loan servicing event for the specified loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case to which the event belongs
     * @param dto the data transfer object containing details of the loan servicing event to be created
     * @return a Mono emitting the created LoanServicingEventDTO instance
     */
    Mono<LoanServicingEventDTO> create(UUID loanServicingCaseId, LoanServicingEventDTO dto);

    /**
     * Retrieves the details of a specific loan servicing event for a given loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanServicingEventId the unique identifier of the loan servicing event
     * @return a Mono emitting the LoanServicingEventDTO corresponding to the specified identifiers,
     *         or completes empty if no such loan servicing event exists
     */
    Mono<LoanServicingEventDTO> getById(UUID loanServicingCaseId, UUID loanServicingEventId);

    /**
     * Updates an existing loan servicing event record with the provided data.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case associated with the loan servicing event
     * @param loanServicingEventId the unique identifier of the loan servicing event to be updated
     * @param dto the data transfer object containing the updated values for the loan servicing event
     * @return a Mono containing the updated LoanServicingEventDTO
     */
    Mono<LoanServicingEventDTO> update(UUID loanServicingCaseId, UUID loanServicingEventId,
                                       LoanServicingEventDTO dto);

    /**
     * Deletes a loan servicing event associated with a specific loan servicing case.
     *
     * @param loanServicingCaseId the unique identifier of the loan servicing case
     * @param loanServicingEventId the unique identifier of the loan servicing event to be deleted
     * @return a Mono signaling the completion of the delete operation
     */
    Mono<Void> delete(UUID loanServicingCaseId, UUID loanServicingEventId);
}
