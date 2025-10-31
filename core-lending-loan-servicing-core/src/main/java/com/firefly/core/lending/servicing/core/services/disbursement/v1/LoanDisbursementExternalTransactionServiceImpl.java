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
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.core.mappers.disbursement.v1.LoanDisbursementExternalTransactionMapper;
import com.firefly.core.lending.servicing.interfaces.dtos.disbursement.v1.LoanDisbursementExternalTransactionDTO;
import com.firefly.core.lending.servicing.models.entities.disbursement.v1.LoanDisbursementExternalTransaction;
import com.firefly.core.lending.servicing.models.repositories.disbursement.v1.LoanDisbursementExternalTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class LoanDisbursementExternalTransactionServiceImpl implements LoanDisbursementExternalTransactionService {

    @Autowired
    private LoanDisbursementExternalTransactionRepository repository;

    @Autowired
    private LoanDisbursementExternalTransactionMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanDisbursementExternalTransactionDTO>> findAll(
            UUID loanDisbursementId,
            FilterRequest<LoanDisbursementExternalTransactionDTO> filterRequest) {
        filterRequest.getFilters().setLoanDisbursementId(loanDisbursementId);
        return FilterUtils.createFilter(
                LoanDisbursementExternalTransaction.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<LoanDisbursementExternalTransactionDTO> create(
            UUID loanDisbursementId,
            LoanDisbursementExternalTransactionDTO dto) {
        dto.setLoanDisbursementId(loanDisbursementId);
        LoanDisbursementExternalTransaction entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanDisbursementExternalTransactionDTO> getById(
            UUID loanDisbursementId,
            UUID transactionId) {
        return repository.findById(transactionId)
                .filter(entity -> loanDisbursementId.equals(entity.getLoanDisbursementId()))
                .map(mapper::toDTO);
    }

    @Override
    public Flux<LoanDisbursementExternalTransactionDTO> findByDisbursementId(UUID loanDisbursementId) {
        return repository.findByLoanDisbursementId(loanDisbursementId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanDisbursementExternalTransactionDTO> findByPspTransactionId(String pspTransactionId) {
        return repository.findByPspTransactionId(pspTransactionId)
                .map(mapper::toDTO);
    }
}

