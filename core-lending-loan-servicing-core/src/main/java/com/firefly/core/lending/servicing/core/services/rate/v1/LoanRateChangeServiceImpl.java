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


package com.firefly.core.lending.servicing.core.services.rate.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.core.mappers.rate.v1.LoanRateChangeMapper;
import com.firefly.core.lending.servicing.interfaces.dtos.rate.v1.LoanRateChangeDTO;
import com.firefly.core.lending.servicing.models.entities.rate.v1.LoanRateChange;
import com.firefly.core.lending.servicing.models.repositories.rate.v1.LoanRateChangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class LoanRateChangeServiceImpl implements LoanRateChangeService {

    @Autowired
    private LoanRateChangeRepository repository;

    @Autowired
    private LoanRateChangeMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanRateChangeDTO>> findAll(UUID loanServicingCaseId, FilterRequest<LoanRateChangeDTO> filterRequest) {
        filterRequest.getFilters().setLoanServicingCaseId(loanServicingCaseId);
        return FilterUtils.createFilter(
                LoanRateChange.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<LoanRateChangeDTO> create(UUID loanServicingCaseId, LoanRateChangeDTO dto) {
        dto.setLoanServicingCaseId(loanServicingCaseId);
        LoanRateChange entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(savedEntity -> mapper.toDTO(savedEntity));
    }

    @Override
    public Mono<LoanRateChangeDTO> getById(UUID loanServicingCaseId, UUID loanRateChangeId) {
        return repository.findById(loanRateChangeId)
                .filter(entity -> entity.getLoanServicingCaseId().equals(loanServicingCaseId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanRateChangeDTO> update(UUID loanServicingCaseId, UUID loanRateChangeId, LoanRateChangeDTO dto) {
        return repository.findById(loanRateChangeId)
                .filter(entity -> entity.getLoanServicingCaseId().equals(loanServicingCaseId))
                .flatMap(existingEntity -> {
                    LoanRateChange updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setLoanRateChangeId(loanRateChangeId);
                    updatedEntity.setLoanServicingCaseId(loanServicingCaseId);
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID loanServicingCaseId, UUID loanRateChangeId) {
        return repository.findById(loanRateChangeId)
                .filter(entity -> entity.getLoanServicingCaseId().equals(loanServicingCaseId))
                .flatMap(repository::delete);
    }
}
