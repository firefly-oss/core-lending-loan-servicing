package com.firefly.core.lending.servicing.core.services.servicing.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.core.mappers.servicing.v1.LoanServicingCaseMapper;
import com.firefly.core.lending.servicing.interfaces.dtos.servicing.v1.LoanServicingCaseDTO;
import com.firefly.core.lending.servicing.models.entities.servicing.v1.LoanServicingCase;
import com.firefly.core.lending.servicing.models.repositories.servicing.v1.LoanServicingCaseRepository;
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
                    LoanServicingCase updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setLoanServicingCaseId(existingEntity.getLoanServicingCaseId());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID loanServicingCaseId) {
        return repository.deleteById(loanServicingCaseId);
    }
}
