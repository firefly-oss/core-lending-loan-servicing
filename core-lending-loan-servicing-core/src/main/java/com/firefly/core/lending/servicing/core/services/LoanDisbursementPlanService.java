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
import com.firefly.core.lending.servicing.interfaces.dtos.LoanDisbursementPlanDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Service interface for managing loan disbursement plans.
 *
 * <p>This service handles the creation, retrieval, update, and deletion of disbursement plans
 * for loans that require staged or scheduled disbursements. Common use cases include:</p>
 * <ul>
 *   <li>Construction loans with multiple draw schedules</li>
 *   <li>Line of credit disbursements</li>
 *   <li>Staged funding for business loans</li>
 *   <li>Educational loans with semester-based disbursements</li>
 * </ul>
 *
 * <p>All operations are scoped to a specific loan servicing case to ensure proper isolation
 * and security.</p>
 *
 * @since 1.0.0
 */
public interface LoanDisbursementPlanService {

    /**
     * Retrieves all disbursement plans for a specific loan servicing case with pagination and filtering.
     *
     * @param loanServicingCaseId the UUID of the loan servicing case
     * @param filterRequest the filter and pagination request
     * @return a Mono emitting a paginated response of disbursement plan DTOs
     */
    Mono<PaginationResponse<LoanDisbursementPlanDTO>> findAll(UUID loanServicingCaseId, FilterRequest<LoanDisbursementPlanDTO> filterRequest);

    /**
     * Creates a new disbursement plan for a loan servicing case.
     *
     * @param loanServicingCaseId the UUID of the loan servicing case
     * @param dto the disbursement plan data to create
     * @return a Mono emitting the created disbursement plan DTO
     */
    Mono<LoanDisbursementPlanDTO> create(UUID loanServicingCaseId, LoanDisbursementPlanDTO dto);

    /**
     * Retrieves a specific disbursement plan by its ID.
     *
     * @param loanServicingCaseId the UUID of the loan servicing case
     * @param loanDisbursementPlanId the UUID of the disbursement plan
     * @return a Mono emitting the disbursement plan DTO, or empty if not found
     */
    Mono<LoanDisbursementPlanDTO> getById(UUID loanServicingCaseId, UUID loanDisbursementPlanId);

    /**
     * Updates an existing disbursement plan.
     *
     * @param loanServicingCaseId the UUID of the loan servicing case
     * @param loanDisbursementPlanId the UUID of the disbursement plan to update
     * @param dto the updated disbursement plan data
     * @return a Mono emitting the updated disbursement plan DTO
     */
    Mono<LoanDisbursementPlanDTO> update(UUID loanServicingCaseId, UUID loanDisbursementPlanId, LoanDisbursementPlanDTO dto);

    /**
     * Deletes a disbursement plan.
     *
     * @param loanServicingCaseId the UUID of the loan servicing case
     * @param loanDisbursementPlanId the UUID of the disbursement plan to delete
     * @return a Mono that completes when the deletion is successful
     */
    Mono<Void> delete(UUID loanServicingCaseId, UUID loanDisbursementPlanId);
}

