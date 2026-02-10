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
import com.firefly.core.lending.servicing.core.mappers.LoanEscrowMapper;
import com.firefly.core.lending.servicing.core.services.LoanEscrowService;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanEscrowDTO;
import com.firefly.core.lending.servicing.models.entities.LoanEscrow;
import com.firefly.core.lending.servicing.models.repositories.LoanEscrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class LoanEscrowServiceImpl implements LoanEscrowService {

    @Autowired
    private LoanEscrowRepository repository;

    @Autowired
    private LoanEscrowMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanEscrowDTO>> findAll(UUID loanServicingCaseId, FilterRequest<LoanEscrowDTO> filterRequest) {
        filterRequest.getFilters().setLoanServicingCaseId(loanServicingCaseId);
        return FilterUtils.createFilter(
                LoanEscrow.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<LoanEscrowDTO> create(UUID loanServicingCaseId, LoanEscrowDTO dto) {
        return Mono.just(dto)
                .doOnNext(d -> d.setLoanServicingCaseId(loanServicingCaseId))
                .map(mapper::toEntity)
                .flatMap(entity -> {
                    entity.setCreatedAt(LocalDateTime.now());
                    entity.setUpdatedAt(LocalDateTime.now());
                    return repository.save(entity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanEscrowDTO> getById(UUID loanServicingCaseId, UUID loanEscrowId) {
        return Mono.defer(() -> repository.findById(loanEscrowId)
                .filter(entity -> Objects.equals(entity.getLoanServicingCaseId(), loanServicingCaseId))
                .map(mapper::toDTO));
    }

    @Override
    public Mono<LoanEscrowDTO> update(UUID loanServicingCaseId, UUID loanEscrowId, LoanEscrowDTO dto) {
        return repository.findById(loanEscrowId)
                .filter(entity -> Objects.equals(entity.getLoanServicingCaseId(), loanServicingCaseId))
                .flatMap(existingEntity -> {
                    existingEntity.setEscrowType(dto.getEscrowType());
                    existingEntity.setMonthlyPaymentAmount(dto.getMonthlyPaymentAmount());
                    existingEntity.setCurrentBalance(dto.getCurrentBalance());
                    existingEntity.setTargetBalance(dto.getTargetBalance());
                    existingEntity.setAnnualDisbursementAmount(dto.getAnnualDisbursementAmount());
                    existingEntity.setNextDisbursementDate(dto.getNextDisbursementDate());
                    existingEntity.setLastAnalysisDate(dto.getLastAnalysisDate());
                    existingEntity.setNextAnalysisDate(dto.getNextAnalysisDate());
                    existingEntity.setIsActive(dto.getIsActive());
                    existingEntity.setPayeeName(dto.getPayeeName());
                    existingEntity.setPayeeAccountNumber(dto.getPayeeAccountNumber());
                    existingEntity.setRemarks(dto.getRemarks());
                    existingEntity.setUpdatedAt(LocalDateTime.now());
                    return repository.save(existingEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID loanServicingCaseId, UUID loanEscrowId) {
        return repository.findById(loanEscrowId)
                .filter(entity -> Objects.equals(entity.getLoanServicingCaseId(), loanServicingCaseId))
                .flatMap(repository::delete);
    }
}

