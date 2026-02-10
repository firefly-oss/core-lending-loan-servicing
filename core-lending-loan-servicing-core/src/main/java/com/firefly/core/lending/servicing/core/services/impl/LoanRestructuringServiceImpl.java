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
import com.firefly.core.lending.servicing.core.mappers.LoanRestructuringMapper;
import com.firefly.core.lending.servicing.core.services.LoanRestructuringService;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanRestructuringDTO;
import com.firefly.core.lending.servicing.models.entities.LoanRestructuring;
import com.firefly.core.lending.servicing.models.repositories.LoanRestructuringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class LoanRestructuringServiceImpl implements LoanRestructuringService {

    @Autowired
    private LoanRestructuringRepository repository;

    @Autowired
    private LoanRestructuringMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanRestructuringDTO>> findAll(UUID loanServicingCaseId, FilterRequest<LoanRestructuringDTO> filterRequest) {
        filterRequest.getFilters().setLoanServicingCaseId(loanServicingCaseId);
        return FilterUtils.createFilter(
                LoanRestructuring.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<LoanRestructuringDTO> create(UUID loanServicingCaseId, LoanRestructuringDTO dto) {
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
    public Mono<LoanRestructuringDTO> getById(UUID loanServicingCaseId, UUID loanRestructuringId) {
        return Mono.defer(() -> repository.findById(loanRestructuringId)
                .filter(entity -> Objects.equals(entity.getLoanServicingCaseId(), loanServicingCaseId))
                .map(mapper::toDTO));
    }

    @Override
    public Mono<LoanRestructuringDTO> update(UUID loanServicingCaseId, UUID loanRestructuringId, LoanRestructuringDTO dto) {
        return repository.findById(loanRestructuringId)
                .filter(entity -> Objects.equals(entity.getLoanServicingCaseId(), loanServicingCaseId))
                .flatMap(existingEntity -> {
                    existingEntity.setRestructuringDate(dto.getRestructuringDate());
                    existingEntity.setReason(dto.getReason());
                    
                    // Update old terms
                    existingEntity.setOldPrincipalAmount(dto.getOldPrincipalAmount());
                    existingEntity.setOldInterestRate(dto.getOldInterestRate());
                    existingEntity.setOldLoanTerm(dto.getOldLoanTerm());
                    existingEntity.setOldInterestCalculationMethod(dto.getOldInterestCalculationMethod());
                    existingEntity.setOldAmortizationMethod(dto.getOldAmortizationMethod());
                    existingEntity.setOldPaymentFrequency(dto.getOldPaymentFrequency());
                    existingEntity.setOldCompoundingFrequency(dto.getOldCompoundingFrequency());
                    existingEntity.setOldDayCountConvention(dto.getOldDayCountConvention());
                    existingEntity.setOldMaturityDate(dto.getOldMaturityDate());
                    
                    // Update new terms
                    existingEntity.setNewPrincipalAmount(dto.getNewPrincipalAmount());
                    existingEntity.setNewInterestRate(dto.getNewInterestRate());
                    existingEntity.setNewLoanTerm(dto.getNewLoanTerm());
                    existingEntity.setNewInterestCalculationMethod(dto.getNewInterestCalculationMethod());
                    existingEntity.setNewAmortizationMethod(dto.getNewAmortizationMethod());
                    existingEntity.setNewPaymentFrequency(dto.getNewPaymentFrequency());
                    existingEntity.setNewCompoundingFrequency(dto.getNewCompoundingFrequency());
                    existingEntity.setNewDayCountConvention(dto.getNewDayCountConvention());
                    existingEntity.setNewMaturityDate(dto.getNewMaturityDate());
                    
                    existingEntity.setApprovedBy(dto.getApprovedBy());
                    existingEntity.setRemarks(dto.getRemarks());
                    existingEntity.setUpdatedAt(LocalDateTime.now());
                    
                    return repository.save(existingEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID loanServicingCaseId, UUID loanRestructuringId) {
        return repository.findById(loanRestructuringId)
                .filter(entity -> Objects.equals(entity.getLoanServicingCaseId(), loanServicingCaseId))
                .flatMap(repository::delete);
    }
}

