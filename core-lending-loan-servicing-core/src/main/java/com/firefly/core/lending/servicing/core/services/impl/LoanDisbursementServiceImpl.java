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


package com.firefly.core.lending.servicing.core.services.impl;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.core.mappers.LoanDisbursementMapper;
import com.firefly.core.lending.servicing.core.services.LoanDisbursementService;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanDisbursementDTO;
import com.firefly.core.lending.servicing.models.entities.LoanDisbursement;
import com.firefly.core.lending.servicing.models.repositories.LoanDisbursementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class LoanDisbursementServiceImpl implements LoanDisbursementService {

    @Autowired
    private LoanDisbursementRepository repository;

    @Autowired
    private LoanDisbursementMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanDisbursementDTO>> findAll(UUID loanServicingCaseId, FilterRequest<LoanDisbursementDTO> filterRequest) {
        filterRequest.getFilters().setLoanServicingCaseId(loanServicingCaseId);
        return FilterUtils.createFilter(
                LoanDisbursement.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<LoanDisbursementDTO> create(UUID loanServicingCaseId, LoanDisbursementDTO dto) {
        dto.setLoanServicingCaseId(loanServicingCaseId);
        LoanDisbursement entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanDisbursementDTO> getById(UUID loanServicingCaseId, UUID loanDisbursementId) {
        return repository.findById(loanDisbursementId)
                .filter(entity -> loanServicingCaseId.equals(entity.getLoanServicingCaseId()))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanDisbursementDTO> update(UUID loanServicingCaseId, UUID loanDisbursementId, LoanDisbursementDTO dto) {
        return repository.findById(loanDisbursementId)
                .filter(existing -> loanServicingCaseId.equals(existing.getLoanServicingCaseId()))
                .flatMap(existing -> {
                    dto.setLoanServicingCaseId(loanServicingCaseId);
                    dto.setLoanDisbursementId(loanDisbursementId);
                    LoanDisbursement entity = mapper.toEntity(dto);
                    return repository.save(entity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID loanServicingCaseId, UUID loanDisbursementId) {
        return repository.findById(loanDisbursementId)
                .filter(entity -> loanServicingCaseId.equals(entity.getLoanServicingCaseId()))
                .flatMap(repository::delete);
    }
}