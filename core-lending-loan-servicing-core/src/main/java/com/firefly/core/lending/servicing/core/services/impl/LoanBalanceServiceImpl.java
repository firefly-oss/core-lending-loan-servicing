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
import com.firefly.core.lending.servicing.core.mappers.LoanBalanceMapper;
import com.firefly.core.lending.servicing.core.services.LoanBalanceService;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanBalanceDTO;
import com.firefly.core.lending.servicing.models.entities.LoanBalance;
import com.firefly.core.lending.servicing.models.repositories.LoanBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class LoanBalanceServiceImpl implements LoanBalanceService {

    @Autowired
    private LoanBalanceRepository repository;

    @Autowired
    private LoanBalanceMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanBalanceDTO>> findAll(UUID loanServicingCaseId, FilterRequest<LoanBalanceDTO> filterRequest) {
        filterRequest.getFilters().setLoanServicingCaseId(loanServicingCaseId);
        return FilterUtils.createFilter(
                LoanBalance.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<LoanBalanceDTO> getCurrentBalance(UUID loanServicingCaseId) {
        return repository.findByLoanServicingCaseIdAndIsCurrentTrue(loanServicingCaseId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanBalanceDTO> create(UUID loanServicingCaseId, LoanBalanceDTO dto) {
        return Mono.just(dto)
                .doOnNext(d -> d.setLoanServicingCaseId(loanServicingCaseId))
                // Mark previous current balance as historical
                .flatMap(d -> repository.findByLoanServicingCaseIdAndIsCurrentTrue(loanServicingCaseId)
                        .flatMap(currentBalance -> {
                            currentBalance.setIsCurrent(false);
                            currentBalance.setUpdatedAt(LocalDateTime.now());
                            return repository.save(currentBalance);
                        })
                        .then(Mono.just(d)))
                .map(mapper::toEntity)
                .flatMap(entity -> {
                    entity.setIsCurrent(true);
                    entity.setCreatedAt(LocalDateTime.now());
                    entity.setUpdatedAt(LocalDateTime.now());
                    return repository.save(entity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanBalanceDTO> getById(UUID loanServicingCaseId, UUID loanBalanceId) {
        return Mono.defer(() -> repository.findById(loanBalanceId)
                .filter(entity -> Objects.equals(entity.getLoanServicingCaseId(), loanServicingCaseId))
                .map(mapper::toDTO));
    }

    @Override
    public Mono<LoanBalanceDTO> update(UUID loanServicingCaseId, UUID loanBalanceId, LoanBalanceDTO dto) {
        return repository.findById(loanBalanceId)
                .filter(entity -> Objects.equals(entity.getLoanServicingCaseId(), loanServicingCaseId))
                .flatMap(existingEntity -> {
                    existingEntity.setPrincipalOutstanding(dto.getPrincipalOutstanding());
                    existingEntity.setInterestOutstanding(dto.getInterestOutstanding());
                    existingEntity.setFeesOutstanding(dto.getFeesOutstanding());
                    existingEntity.setTotalOutstanding(dto.getTotalOutstanding());
                    existingEntity.setBalanceDate(dto.getBalanceDate());
                    existingEntity.setIsCurrent(dto.getIsCurrent());
                    existingEntity.setUpdatedAt(LocalDateTime.now());
                    return repository.save(existingEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID loanServicingCaseId, UUID loanBalanceId) {
        return repository.findById(loanBalanceId)
                .filter(entity -> Objects.equals(entity.getLoanServicingCaseId(), loanServicingCaseId))
                .flatMap(repository::delete);
    }
}

