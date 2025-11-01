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

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.core.mappers.LoanServicingEventMapper;
import com.firefly.core.lending.servicing.core.services.LoanServicingEventService;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanServicingEventDTO;
import com.firefly.core.lending.servicing.models.entities.LoanServicingEvent;
import com.firefly.core.lending.servicing.models.repositories.LoanServicingEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class LoanServicingEventServiceImpl implements LoanServicingEventService {

    @Autowired
    private LoanServicingEventRepository repository;

    @Autowired
    private LoanServicingEventMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanServicingEventDTO>> findAll(UUID loanServicingCaseId, FilterRequest<LoanServicingEventDTO> filterRequest) {
        filterRequest.getFilters().setLoanServicingCaseId(loanServicingCaseId);
        return FilterUtils.createFilter(
                LoanServicingEvent.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<LoanServicingEventDTO> create(UUID loanServicingCaseId, LoanServicingEventDTO dto) {
        dto.setLoanServicingCaseId(loanServicingCaseId);
        LoanServicingEvent entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanServicingEventDTO> getById(UUID loanServicingCaseId, UUID loanServicingEventId) {
        return repository.findById(loanServicingEventId)
                .filter(event -> event.getLoanServicingCaseId().equals(loanServicingCaseId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanServicingEventDTO> update(UUID loanServicingCaseId, UUID loanServicingEventId, LoanServicingEventDTO dto) {
        return repository.findById(loanServicingEventId)
                .filter(entity -> entity.getLoanServicingCaseId().equals(loanServicingCaseId))
                .flatMap(existingEntity -> {
                    existingEntity.setEventType(dto.getEventType());
                    existingEntity.setEventDate(dto.getEventDate());
                    existingEntity.setDescription(dto.getDescription());
                    existingEntity.setUpdatedAt(dto.getUpdatedAt());
                    return repository.save(existingEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID loanServicingCaseId, UUID loanServicingEventId) {
        return repository.findById(loanServicingEventId)
                .filter(event -> event.getLoanServicingCaseId().equals(loanServicingCaseId))
                .flatMap(repository::delete);
    }
}