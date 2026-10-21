/*
 * Copyright 2025 Firefly Software Foundation
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


package io.fireflysoftware.core.lending.servicing.core.services.impl;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.lending.servicing.core.mappers.LoanServicingCaseMapper;
import io.fireflysoftware.core.lending.servicing.core.services.LoanServicingCaseService;
import io.fireflysoftware.core.lending.servicing.interfaces.dtos.LoanServicingCaseDTO;
import io.fireflysoftware.core.lending.servicing.interfaces.enums.ServicingStatusEnum;
import io.fireflysoftware.core.lending.servicing.models.entities.LoanServicingCase;
import io.fireflysoftware.core.lending.servicing.models.repositories.LoanServicingCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class LoanServicingCaseServiceImpl implements LoanServicingCaseService {

    @Autowired
    private LoanServicingCaseRepository repository;

    @Autowired
    private LoanServicingCaseMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanServicingCaseDTO>> findAll(FilterRequest<LoanServicingCaseDTO> filterRequest) {
        return FilterUtils.createFilter(
                LoanServicingCase.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<LoanServicingCaseDTO> create(LoanServicingCaseDTO dto) {
        LoanServicingCase entity = mapper.toEntity(dto);
        if (entity.getServicingStatus() == null) {
            entity.setServicingStatus(ServicingStatusEnum.PENDING);
        }
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanServicingCaseDTO> getById(UUID loanServicingCaseId) {
        return repository.findById(loanServicingCaseId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanServicingCaseDTO> update(UUID loanServicingCaseId, LoanServicingCaseDTO dto) {
        return repository.findById(loanServicingCaseId)
                .flatMap(existingEntity -> {
                    ServicingStatusEnum current = existingEntity.getServicingStatus() != null
                            ? existingEntity.getServicingStatus()
                            : ServicingStatusEnum.PENDING;
                    LoanServicingCase updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setLoanServicingCaseId(existingEntity.getLoanServicingCaseId());
                    if (updatedEntity.getServicingStatus() == null) {
                        updatedEntity.setServicingStatus(current);
                    }
                    if (!ServicingLifecycle.canTransition(current, updatedEntity.getServicingStatus())) {
                        return Mono.error(new IllegalStateException(
                                "Loan servicing case " + loanServicingCaseId + " cannot move from " + current
                                        + " to " + updatedEntity.getServicingStatus()));
                    }
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID loanServicingCaseId) {
        return repository.findById(loanServicingCaseId)
                .flatMap(existing -> {
                    ServicingStatusEnum status = existing.getServicingStatus() != null
                            ? existing.getServicingStatus()
                            : ServicingStatusEnum.PENDING;
                    if (status != ServicingStatusEnum.PENDING) {
                        return Mono.error(new IllegalStateException(
                                "Loan servicing case " + loanServicingCaseId + " is " + status
                                        + " and cannot be deleted; close it so the servicing history is kept"));
                    }
                    return repository.deleteById(loanServicingCaseId);
                });
    }
}
