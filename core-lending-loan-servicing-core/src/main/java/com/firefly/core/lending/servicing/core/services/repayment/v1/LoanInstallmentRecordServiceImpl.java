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


package com.firefly.core.lending.servicing.core.services.repayment.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.core.mappers.repayment.v1.LoanInstallmentRecordMapper;
import com.firefly.core.lending.servicing.interfaces.dtos.repayment.v1.LoanInstallmentRecordDTO;
import com.firefly.core.lending.servicing.models.entities.repayment.v1.LoanInstallmentRecord;
import com.firefly.core.lending.servicing.models.repositories.repayment.v1.LoanInstallmentRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class LoanInstallmentRecordServiceImpl implements LoanInstallmentRecordService {

    @Autowired
    private LoanInstallmentRecordRepository repository;

    @Autowired
    private LoanInstallmentRecordMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanInstallmentRecordDTO>> findAll(UUID loanServicingCaseId, FilterRequest<LoanInstallmentRecordDTO> filterRequest) {
        filterRequest.getFilters().setLoanServicingCaseId(loanServicingCaseId);
        return FilterUtils.createFilter(
                LoanInstallmentRecord.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<LoanInstallmentRecordDTO> create(UUID loanServicingCaseId, LoanInstallmentRecordDTO dto) {
        dto.setLoanServicingCaseId(loanServicingCaseId);
        LoanInstallmentRecord entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanInstallmentRecordDTO> getById(UUID loanServicingCaseId, UUID loanInstallmentRecordId) {
        return repository.findById(loanInstallmentRecordId)
                .filter(record -> record.getLoanServicingCaseId().equals(loanServicingCaseId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanInstallmentRecordDTO> update(UUID loanServicingCaseId, UUID loanInstallmentRecordId, LoanInstallmentRecordDTO dto) {
        return repository.findById(loanInstallmentRecordId)
                .filter(record -> record.getLoanServicingCaseId().equals(loanServicingCaseId))
                .flatMap(existingEntity -> {
                    LoanInstallmentRecord updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setLoanInstallmentRecordId(loanInstallmentRecordId);
                    updatedEntity.setLoanServicingCaseId(loanServicingCaseId);
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID loanServicingCaseId, UUID loanInstallmentRecordId) {
        return repository.findById(loanInstallmentRecordId)
                .filter(record -> record.getLoanServicingCaseId().equals(loanServicingCaseId))
                .flatMap(repository::delete);
    }
}

