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
import com.firefly.core.lending.servicing.core.mappers.LoanNotificationMapper;
import com.firefly.core.lending.servicing.core.services.LoanNotificationService;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanNotificationDTO;
import com.firefly.core.lending.servicing.models.entities.LoanNotification;
import com.firefly.core.lending.servicing.models.repositories.LoanNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class LoanNotificationServiceImpl implements LoanNotificationService {

    @Autowired
    private LoanNotificationRepository repository;

    @Autowired
    private LoanNotificationMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanNotificationDTO>> findAll(UUID loanServicingCaseId, FilterRequest<LoanNotificationDTO> filterRequest) {
        filterRequest.getFilters().setLoanServicingCaseId(loanServicingCaseId);
        return FilterUtils.createFilter(
                LoanNotification.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<LoanNotificationDTO> create(UUID loanServicingCaseId, LoanNotificationDTO dto) {
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
    public Mono<LoanNotificationDTO> getById(UUID loanServicingCaseId, UUID loanNotificationId) {
        return Mono.defer(() -> repository.findById(loanNotificationId)
                .filter(entity -> Objects.equals(entity.getLoanServicingCaseId(), loanServicingCaseId))
                .map(mapper::toDTO));
    }

    @Override
    public Mono<LoanNotificationDTO> update(UUID loanServicingCaseId, UUID loanNotificationId, LoanNotificationDTO dto) {
        return repository.findById(loanNotificationId)
                .filter(entity -> Objects.equals(entity.getLoanServicingCaseId(), loanServicingCaseId))
                .flatMap(existingEntity -> {
                    existingEntity.setNotificationType(dto.getNotificationType());
                    existingEntity.setNotificationChannel(dto.getNotificationChannel());
                    existingEntity.setNotificationStatus(dto.getNotificationStatus());
                    existingEntity.setRecipientPartyId(dto.getRecipientPartyId());
                    existingEntity.setRecipientName(dto.getRecipientName());
                    existingEntity.setRecipientContact(dto.getRecipientContact());
                    existingEntity.setSubject(dto.getSubject());
                    existingEntity.setMessageBody(dto.getMessageBody());
                    existingEntity.setScheduledSendTime(dto.getScheduledSendTime());
                    existingEntity.setFailureReason(dto.getFailureReason());
                    existingEntity.setRetryCount(dto.getRetryCount());
                    existingEntity.setTemplateId(dto.getTemplateId());
                    existingEntity.setMetadata(dto.getMetadata());
                    existingEntity.setUpdatedAt(LocalDateTime.now());
                    return repository.save(existingEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID loanServicingCaseId, UUID loanNotificationId) {
        return repository.findById(loanNotificationId)
                .filter(entity -> Objects.equals(entity.getLoanServicingCaseId(), loanServicingCaseId))
                .flatMap(repository::delete);
    }
}

