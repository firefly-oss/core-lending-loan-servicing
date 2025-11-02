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
import com.firefly.core.lending.servicing.core.mappers.LoanDisbursementPlanMapper;
import com.firefly.core.lending.servicing.core.services.LoanDisbursementPlanService;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanDisbursementPlanDTO;
import com.firefly.core.lending.servicing.models.entities.LoanDisbursementPlan;
import com.firefly.core.lending.servicing.models.repositories.LoanDisbursementPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class LoanDisbursementPlanServiceImpl implements LoanDisbursementPlanService {

    @Autowired
    private LoanDisbursementPlanRepository repository;

    @Autowired
    private LoanDisbursementPlanMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanDisbursementPlanDTO>> findAll(UUID loanServicingCaseId, FilterRequest<LoanDisbursementPlanDTO> filterRequest) {
        filterRequest.getFilters().setLoanServicingCaseId(loanServicingCaseId);
        return FilterUtils.createFilter(
                LoanDisbursementPlan.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<LoanDisbursementPlanDTO> create(UUID loanServicingCaseId, LoanDisbursementPlanDTO dto) {
        return Mono.just(dto)
                .doOnNext(d -> {
                    if (d.getLoanServicingCaseId() != null && !d.getLoanServicingCaseId().equals(loanServicingCaseId)) {
                        throw new IllegalArgumentException(
                                "Loan servicing case ID in DTO does not match path parameter");
                    }
                    d.setLoanServicingCaseId(loanServicingCaseId);
                })
                .map(mapper::toEntity)
                .flatMap(entity -> {
                    entity.setCreatedAt(LocalDateTime.now());
                    entity.setUpdatedAt(LocalDateTime.now());
                    return repository.save(entity);
                })
                .map(mapper::toDTO)
                .onErrorResume(e -> {
                    if (e instanceof IllegalArgumentException) {
                        return Mono.error(e);
                    }
                    return Mono.error(new RuntimeException("Error creating disbursement plan: " + e.getMessage(), e));
                });
    }

    @Override
    public Mono<LoanDisbursementPlanDTO> getById(UUID loanServicingCaseId, UUID loanDisbursementPlanId) {
        return Mono.defer(() -> repository.findById(loanDisbursementPlanId)
                .filter(entity -> Objects.equals(entity.getLoanServicingCaseId(), loanServicingCaseId))
                .switchIfEmpty(Mono.error(new RuntimeException(
                        "Disbursement plan not found with ID: " + loanDisbursementPlanId +
                        " for loan servicing case: " + loanServicingCaseId)))
                .map(mapper::toDTO))
                .onErrorResume(e -> {
                    if (e instanceof RuntimeException && e.getMessage().contains("not found")) {
                        return Mono.error(e);
                    }
                    return Mono.error(new RuntimeException("Error retrieving disbursement plan: " + e.getMessage(), e));
                });
    }

    @Override
    public Mono<LoanDisbursementPlanDTO> update(UUID loanServicingCaseId, UUID loanDisbursementPlanId, LoanDisbursementPlanDTO dto) {
        return repository.findById(loanDisbursementPlanId)
                .filter(entity -> Objects.equals(entity.getLoanServicingCaseId(), loanServicingCaseId))
                .switchIfEmpty(Mono.error(new RuntimeException(
                        "Disbursement plan not found with ID: " + loanDisbursementPlanId +
                        " for loan servicing case: " + loanServicingCaseId)))
                .flatMap(existingEntity -> {
                    existingEntity.setPlannedDisbursementDate(dto.getPlannedDisbursementDate());
                    existingEntity.setPlannedAmount(dto.getPlannedAmount());
                    existingEntity.setActualDisbursementDate(dto.getActualDisbursementDate());
                    existingEntity.setActualAmount(dto.getActualAmount());
                    existingEntity.setDisbursementNumber(dto.getDisbursementNumber());
                    existingEntity.setIsCompleted(dto.getIsCompleted());
                    existingEntity.setPurpose(dto.getPurpose());
                    existingEntity.setRemarks(dto.getRemarks());
                    existingEntity.setUpdatedAt(LocalDateTime.now());
                    return repository.save(existingEntity);
                })
                .map(mapper::toDTO)
                .onErrorResume(e -> {
                    if (e instanceof RuntimeException && e.getMessage().contains("not found")) {
                        return Mono.error(e);
                    }
                    return Mono.error(new RuntimeException("Error updating disbursement plan: " + e.getMessage(), e));
                });
    }

    @Override
    public Mono<Void> delete(UUID loanServicingCaseId, UUID loanDisbursementPlanId) {
        return repository.findById(loanDisbursementPlanId)
                .filter(entity -> Objects.equals(entity.getLoanServicingCaseId(), loanServicingCaseId))
                .switchIfEmpty(Mono.error(new RuntimeException(
                        "Disbursement plan not found with ID: " + loanDisbursementPlanId +
                        " for loan servicing case: " + loanServicingCaseId)))
                .flatMap(repository::delete)
                .onErrorResume(e -> {
                    if (e instanceof RuntimeException && e.getMessage().contains("not found")) {
                        return Mono.error(e);
                    }
                    return Mono.error(new RuntimeException("Error deleting disbursement plan: " + e.getMessage(), e));
                });
    }
}

