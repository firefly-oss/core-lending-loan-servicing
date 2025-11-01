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
import com.firefly.core.lending.servicing.interfaces.dtos.LoanDisbursementExternalTransactionDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface LoanDisbursementExternalTransactionService {

    /**
     * Retrieves a paginated list of external transactions for a specific disbursement.
     *
     * @param loanDisbursementId the unique identifier of the loan disbursement
     * @param filterRequest the filtering criteria and pagination details
     * @return a Mono containing a PaginationResponse with the list of external transaction DTOs
     */
    Mono<PaginationResponse<LoanDisbursementExternalTransactionDTO>> findAll(
            UUID loanDisbursementId,
            FilterRequest<LoanDisbursementExternalTransactionDTO> filterRequest);

    /**
     * Creates a new external transaction for a disbursement.
     *
     * @param loanDisbursementId the unique identifier of the loan disbursement
     * @param dto the data transfer object containing details of the external transaction
     * @return a Mono emitting the created external transaction DTO
     */
    Mono<LoanDisbursementExternalTransactionDTO> create(
            UUID loanDisbursementId,
            LoanDisbursementExternalTransactionDTO dto);

    /**
     * Retrieves an external transaction by its ID.
     *
     * @param loanDisbursementId the unique identifier of the loan disbursement
     * @param transactionId the unique identifier of the external transaction
     * @return a Mono emitting the external transaction DTO
     */
    Mono<LoanDisbursementExternalTransactionDTO> getById(
            UUID loanDisbursementId,
            UUID transactionId);

    /**
     * Retrieves all external transactions for a specific disbursement.
     *
     * @param loanDisbursementId the unique identifier of the loan disbursement
     * @return a Flux of external transaction DTOs
     */
    Flux<LoanDisbursementExternalTransactionDTO> findByDisbursementId(UUID loanDisbursementId);

    /**
     * Retrieves an external transaction by PSP transaction ID.
     *
     * @param pspTransactionId the PSP transaction ID
     * @return a Mono emitting the external transaction DTO
     */
    Mono<LoanDisbursementExternalTransactionDTO> findByPspTransactionId(String pspTransactionId);
}

