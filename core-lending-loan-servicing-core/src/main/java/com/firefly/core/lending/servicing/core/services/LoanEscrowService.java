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
import com.firefly.core.lending.servicing.interfaces.dtos.LoanEscrowDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Service interface for managing loan escrow accounts.
 *
 * <p>This service handles the creation, retrieval, update, and deletion of escrow accounts
 * for all types of lending products. Escrow accounts are used to collect and disburse funds
 * for various purposes across different lending scenarios:</p>
 *
 * <p><strong>Mortgages:</strong></p>
 * <ul>
 *   <li>Property taxes, homeowners insurance, mortgage insurance (PMI/MIP)</li>
 *   <li>HOA fees, ground rent, utilities</li>
 * </ul>
 *
 * <p><strong>Asset-Based Lending:</strong></p>
 * <ul>
 *   <li>Collateral insurance, inventory monitoring fees</li>
 *   <li>Appraisal and valuation costs</li>
 * </ul>
 *
 * <p><strong>Trade Finance:</strong></p>
 * <ul>
 *   <li>Freight and shipping costs, warehousing fees</li>
 *   <li>Letter of credit fees, import/export duties</li>
 * </ul>
 *
 * <p><strong>Equipment Financing:</strong></p>
 * <ul>
 *   <li>Equipment insurance, maintenance reserves</li>
 *   <li>Operating expenses, license and permit fees</li>
 * </ul>
 *
 * <p><strong>Construction Loans:</strong></p>
 * <ul>
 *   <li>Construction holdback, performance bonds, retainage</li>
 * </ul>
 *
 * <p><strong>Project Finance:</strong></p>
 * <ul>
 *   <li>Debt service reserves, interest reserves, principal reserves</li>
 *   <li>Contingency reserves, environmental compliance</li>
 * </ul>
 *
 * <p>The service manages payment collection, balance tracking, and disbursement scheduling
 * for each escrow account.</p>
 *
 * <p>All operations are scoped to a specific loan servicing case to ensure proper isolation
 * and security.</p>
 *
 * @since 1.0.0
 */
public interface LoanEscrowService {

    /**
     * Retrieves all escrow accounts for a specific loan servicing case with pagination and filtering.
     *
     * @param loanServicingCaseId the UUID of the loan servicing case
     * @param filterRequest the filter and pagination request
     * @return a Mono emitting a paginated response of escrow DTOs
     */
    Mono<PaginationResponse<LoanEscrowDTO>> findAll(UUID loanServicingCaseId, FilterRequest<LoanEscrowDTO> filterRequest);

    /**
     * Creates a new escrow account for a loan servicing case.
     *
     * @param loanServicingCaseId the UUID of the loan servicing case
     * @param dto the escrow account data to create
     * @return a Mono emitting the created escrow DTO
     */
    Mono<LoanEscrowDTO> create(UUID loanServicingCaseId, LoanEscrowDTO dto);

    /**
     * Retrieves a specific escrow account by its ID.
     *
     * @param loanServicingCaseId the UUID of the loan servicing case
     * @param loanEscrowId the UUID of the escrow account
     * @return a Mono emitting the escrow DTO, or empty if not found
     */
    Mono<LoanEscrowDTO> getById(UUID loanServicingCaseId, UUID loanEscrowId);

    /**
     * Updates an existing escrow account.
     *
     * @param loanServicingCaseId the UUID of the loan servicing case
     * @param loanEscrowId the UUID of the escrow account to update
     * @param dto the updated escrow account data
     * @return a Mono emitting the updated escrow DTO
     */
    Mono<LoanEscrowDTO> update(UUID loanServicingCaseId, UUID loanEscrowId, LoanEscrowDTO dto);

    /**
     * Deletes an escrow account.
     *
     * @param loanServicingCaseId the UUID of the loan servicing case
     * @param loanEscrowId the UUID of the escrow account to delete
     * @return a Mono that completes when the deletion is successful
     */
    Mono<Void> delete(UUID loanServicingCaseId, UUID loanEscrowId);
}

