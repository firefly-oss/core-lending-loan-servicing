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


package com.firefly.core.lending.servicing.core.services.disbursement.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.interfaces.dtos.disbursement.v1.LoanDisbursementInternalTransactionDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface LoanDisbursementInternalTransactionService {

    /**
     * Retrieves a paginated list of internal transactions for a specific disbursement.
     *
     * @param loanDisbursementId the unique identifier of the loan disbursement
     * @param filterRequest the filtering criteria and pagination details
     * @return a Mono containing a PaginationResponse with the list of internal transaction DTOs
     */
    Mono<PaginationResponse<LoanDisbursementInternalTransactionDTO>> findAll(
            UUID loanDisbursementId,
            FilterRequest<LoanDisbursementInternalTransactionDTO> filterRequest);

    /**
     * Creates a new internal transaction for a disbursement.
     *
     * @param loanDisbursementId the unique identifier of the loan disbursement
     * @param dto the data transfer object containing details of the internal transaction
     * @return a Mono emitting the created internal transaction DTO
     */
    Mono<LoanDisbursementInternalTransactionDTO> create(
            UUID loanDisbursementId,
            LoanDisbursementInternalTransactionDTO dto);

    /**
     * Retrieves an internal transaction by its ID.
     *
     * @param loanDisbursementId the unique identifier of the loan disbursement
     * @param transactionId the unique identifier of the internal transaction
     * @return a Mono emitting the internal transaction DTO
     */
    Mono<LoanDisbursementInternalTransactionDTO> getById(
            UUID loanDisbursementId,
            UUID transactionId);

    /**
     * Retrieves all internal transactions for a specific disbursement.
     *
     * @param loanDisbursementId the unique identifier of the loan disbursement
     * @return a Flux of internal transaction DTOs
     */
    Flux<LoanDisbursementInternalTransactionDTO> findByDisbursementId(UUID loanDisbursementId);
}

