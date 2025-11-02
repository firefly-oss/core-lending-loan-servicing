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
import com.firefly.core.lending.servicing.interfaces.dtos.LoanRebateDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Service interface for managing loan rebates.
 *
 * <p>This service handles the creation, retrieval, update, and deletion of rebates
 * applied to loans. Rebates can be direct to borrowers or through distributors/brokers.
 * Common rebate scenarios include:</p>
 * <ul>
 *   <li>Direct borrower rebates (cash back, principal reduction)</li>
 *   <li>Distributor/broker commission rebates</li>
 *   <li>Promotional rebates (marketing campaigns)</li>
 *   <li>Early payment incentives</li>
 *   <li>Loyalty rewards</li>
 *   <li>Referral bonuses</li>
 *   <li>Government subsidies</li>
 *   <li>Rate buydowns</li>
 * </ul>
 *
 * <p>All operations are scoped to a specific loan servicing case to ensure proper isolation
 * and security.</p>
 *
 * @since 1.0.0
 */
public interface LoanRebateService {

    /**
     * Retrieves all rebates for a specific loan servicing case with pagination and filtering.
     *
     * @param loanServicingCaseId the UUID of the loan servicing case
     * @param filterRequest the filter and pagination request
     * @return a Mono emitting a paginated response of rebate DTOs
     */
    Mono<PaginationResponse<LoanRebateDTO>> findAll(UUID loanServicingCaseId, FilterRequest<LoanRebateDTO> filterRequest);

    /**
     * Creates a new rebate for a loan servicing case.
     *
     * @param loanServicingCaseId the UUID of the loan servicing case
     * @param dto the rebate data to create
     * @return a Mono emitting the created rebate DTO
     */
    Mono<LoanRebateDTO> create(UUID loanServicingCaseId, LoanRebateDTO dto);

    /**
     * Retrieves a specific rebate by its ID.
     *
     * @param loanServicingCaseId the UUID of the loan servicing case
     * @param loanRebateId the UUID of the rebate
     * @return a Mono emitting the rebate DTO, or empty if not found
     */
    Mono<LoanRebateDTO> getById(UUID loanServicingCaseId, UUID loanRebateId);

    /**
     * Updates an existing rebate.
     *
     * @param loanServicingCaseId the UUID of the loan servicing case
     * @param loanRebateId the UUID of the rebate to update
     * @param dto the updated rebate data
     * @return a Mono emitting the updated rebate DTO
     */
    Mono<LoanRebateDTO> update(UUID loanServicingCaseId, UUID loanRebateId, LoanRebateDTO dto);

    /**
     * Deletes a rebate.
     *
     * @param loanServicingCaseId the UUID of the loan servicing case
     * @param loanRebateId the UUID of the rebate to delete
     * @return a Mono that completes when the deletion is successful
     */
    Mono<Void> delete(UUID loanServicingCaseId, UUID loanRebateId);
}

