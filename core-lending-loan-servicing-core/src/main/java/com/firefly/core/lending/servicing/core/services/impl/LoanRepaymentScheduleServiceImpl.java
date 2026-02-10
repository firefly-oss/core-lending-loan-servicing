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
import com.firefly.core.lending.servicing.core.mappers.LoanRepaymentScheduleMapper;
import com.firefly.core.lending.servicing.core.services.LoanRepaymentScheduleService;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanRepaymentScheduleDTO;
import com.firefly.core.lending.servicing.models.entities.LoanRepaymentSchedule;
import com.firefly.core.lending.servicing.models.repositories.LoanRepaymentScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class LoanRepaymentScheduleServiceImpl implements LoanRepaymentScheduleService {

    @Autowired
    private LoanRepaymentScheduleRepository repository;

    @Autowired
    private LoanRepaymentScheduleMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanRepaymentScheduleDTO>> findAll(UUID loanServicingCaseId, FilterRequest<LoanRepaymentScheduleDTO> filterRequest) {
        filterRequest.getFilters().setLoanServicingCaseId(loanServicingCaseId);
        return FilterUtils.createFilter(
                LoanRepaymentSchedule.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<LoanRepaymentScheduleDTO> create(UUID loanServicingCaseId, LoanRepaymentScheduleDTO dto) {
        dto.setLoanServicingCaseId(loanServicingCaseId);
        LoanRepaymentSchedule entity = mapper.toEntity(dto);
        return Mono.just(entity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanRepaymentScheduleDTO> getById(UUID loanServicingCaseId, UUID loanRepaymentScheduleId) {
        return repository.findById(loanRepaymentScheduleId)
                .filter(entity -> entity.getLoanServicingCaseId().equals(loanServicingCaseId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanRepaymentScheduleDTO> update(UUID loanServicingCaseId, UUID loanRepaymentScheduleId, LoanRepaymentScheduleDTO dto) {
        return repository.findById(loanRepaymentScheduleId)
                .filter(entity -> entity.getLoanServicingCaseId().equals(loanServicingCaseId))
                .flatMap(existingEntity -> {
                    LoanRepaymentSchedule updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setLoanRepaymentScheduleId(loanRepaymentScheduleId);
                    updatedEntity.setLoanServicingCaseId(loanServicingCaseId);
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID loanServicingCaseId, UUID loanRepaymentScheduleId) {
        return repository.findById(loanRepaymentScheduleId)
                .filter(entity -> entity.getLoanServicingCaseId().equals(loanServicingCaseId))
                .flatMap(repository::delete);
    }
}