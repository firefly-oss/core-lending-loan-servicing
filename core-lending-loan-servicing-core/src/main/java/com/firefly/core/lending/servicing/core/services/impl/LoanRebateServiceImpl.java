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
import com.firefly.core.lending.servicing.core.mappers.LoanRebateMapper;
import com.firefly.core.lending.servicing.core.services.LoanRebateService;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanRebateDTO;
import com.firefly.core.lending.servicing.models.entities.LoanRebate;
import com.firefly.core.lending.servicing.models.repositories.LoanRebateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class LoanRebateServiceImpl implements LoanRebateService {

    @Autowired
    private LoanRebateRepository repository;

    @Autowired
    private LoanRebateMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanRebateDTO>> findAll(UUID loanServicingCaseId, FilterRequest<LoanRebateDTO> filterRequest) {
        filterRequest.getFilters().setLoanServicingCaseId(loanServicingCaseId);
        return FilterUtils.createFilter(
                LoanRebate.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<LoanRebateDTO> create(UUID loanServicingCaseId, LoanRebateDTO dto) {
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
                    return Mono.error(new RuntimeException("Error creating rebate: " + e.getMessage(), e));
                });
    }

    @Override
    public Mono<LoanRebateDTO> getById(UUID loanServicingCaseId, UUID loanRebateId) {
        return Mono.defer(() -> repository.findById(loanRebateId)
                .filter(entity -> Objects.equals(entity.getLoanServicingCaseId(), loanServicingCaseId))
                .switchIfEmpty(Mono.error(new RuntimeException(
                        "Rebate not found with ID: " + loanRebateId +
                        " for loan servicing case: " + loanServicingCaseId)))
                .map(mapper::toDTO))
                .onErrorResume(e -> {
                    if (e instanceof RuntimeException && e.getMessage().contains("not found")) {
                        return Mono.error(e);
                    }
                    return Mono.error(new RuntimeException("Error retrieving rebate: " + e.getMessage(), e));
                });
    }

    @Override
    public Mono<LoanRebateDTO> update(UUID loanServicingCaseId, UUID loanRebateId, LoanRebateDTO dto) {
        return repository.findById(loanRebateId)
                .filter(entity -> Objects.equals(entity.getLoanServicingCaseId(), loanServicingCaseId))
                .switchIfEmpty(Mono.error(new RuntimeException(
                        "Rebate not found with ID: " + loanRebateId +
                        " for loan servicing case: " + loanServicingCaseId)))
                .flatMap(existingEntity -> {
                    existingEntity.setRebateType(dto.getRebateType());
                    existingEntity.setRebateAmount(dto.getRebateAmount());
                    existingEntity.setRebateDate(dto.getRebateDate());
                    existingEntity.setDistributorId(dto.getDistributorId());
                    existingEntity.setDistributorAgencyId(dto.getDistributorAgencyId());
                    existingEntity.setDistributorAgentId(dto.getDistributorAgentId());
                    existingEntity.setDistributorCommission(dto.getDistributorCommission());
                    existingEntity.setIsProcessed(dto.getIsProcessed());
                    existingEntity.setProcessedDate(dto.getProcessedDate());
                    existingEntity.setDescription(dto.getDescription());
                    existingEntity.setRemarks(dto.getRemarks());
                    existingEntity.setUpdatedAt(LocalDateTime.now());
                    return repository.save(existingEntity);
                })
                .map(mapper::toDTO)
                .onErrorResume(e -> {
                    if (e instanceof RuntimeException && e.getMessage().contains("not found")) {
                        return Mono.error(e);
                    }
                    return Mono.error(new RuntimeException("Error updating rebate: " + e.getMessage(), e));
                });
    }

    @Override
    public Mono<Void> delete(UUID loanServicingCaseId, UUID loanRebateId) {
        return repository.findById(loanRebateId)
                .filter(entity -> Objects.equals(entity.getLoanServicingCaseId(), loanServicingCaseId))
                .switchIfEmpty(Mono.error(new RuntimeException(
                        "Rebate not found with ID: " + loanRebateId +
                        " for loan servicing case: " + loanServicingCaseId)))
                .flatMap(repository::delete)
                .onErrorResume(e -> {
                    if (e instanceof RuntimeException && e.getMessage().contains("not found")) {
                        return Mono.error(e);
                    }
                    return Mono.error(new RuntimeException("Error deleting rebate: " + e.getMessage(), e));
                });
    }
}

