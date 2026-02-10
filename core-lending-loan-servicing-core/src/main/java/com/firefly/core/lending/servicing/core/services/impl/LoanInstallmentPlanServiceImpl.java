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
import com.firefly.core.lending.servicing.core.mappers.LoanInstallmentPlanMapper;
import com.firefly.core.lending.servicing.core.services.LoanInstallmentPlanService;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanInstallmentPlanDTO;
import com.firefly.core.lending.servicing.models.entities.LoanInstallmentPlan;
import com.firefly.core.lending.servicing.models.repositories.LoanInstallmentPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class LoanInstallmentPlanServiceImpl implements LoanInstallmentPlanService {

    @Autowired
    private LoanInstallmentPlanRepository repository;

    @Autowired
    private LoanInstallmentPlanMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanInstallmentPlanDTO>> findAll(UUID loanServicingCaseId, FilterRequest<LoanInstallmentPlanDTO> filterRequest) {
        filterRequest.getFilters().setLoanServicingCaseId(loanServicingCaseId);
        return FilterUtils.createFilter(
                LoanInstallmentPlan.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<LoanInstallmentPlanDTO> create(UUID loanServicingCaseId, LoanInstallmentPlanDTO dto) {
        dto.setLoanServicingCaseId(loanServicingCaseId);
        LoanInstallmentPlan entity = mapper.toEntity(dto);
        return Mono.just(entity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanInstallmentPlanDTO> getById(UUID loanServicingCaseId, UUID loanInstallmentPlanId) {
        return repository.findById(loanInstallmentPlanId)
                .filter(entity -> entity.getLoanServicingCaseId().equals(loanServicingCaseId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanInstallmentPlanDTO> update(UUID loanServicingCaseId, UUID loanInstallmentPlanId, LoanInstallmentPlanDTO dto) {
        return repository.findById(loanInstallmentPlanId)
                .filter(entity -> entity.getLoanServicingCaseId().equals(loanServicingCaseId))
                .flatMap(existingEntity -> {
                    LoanInstallmentPlan updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setLoanInstallmentPlanId(loanInstallmentPlanId);
                    updatedEntity.setLoanServicingCaseId(loanServicingCaseId);
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID loanServicingCaseId, UUID loanInstallmentPlanId) {
        return repository.findById(loanInstallmentPlanId)
                .filter(entity -> entity.getLoanServicingCaseId().equals(loanServicingCaseId))
                .flatMap(repository::delete);
    }
}

