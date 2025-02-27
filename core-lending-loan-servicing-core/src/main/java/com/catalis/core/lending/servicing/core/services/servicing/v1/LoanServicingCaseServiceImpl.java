package com.catalis.core.lending.servicing.core.services.servicing.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.lending.servicing.core.mappers.servicing.v1.LoanServicingCaseMapper;
import com.catalis.core.lending.servicing.interfaces.dtos.servicing.v1.LoanServicingCaseDTO;
import com.catalis.core.lending.servicing.models.entities.servicing.v1.LoanServicingCase;
import com.catalis.core.lending.servicing.models.repositories.servicing.v1.LoanServicingCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

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
    public Mono<LoanServicingCaseDTO> getById(Long loanServicingCaseId) {
        return repository.findById(loanServicingCaseId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanServicingCaseDTO> update(Long loanServicingCaseId, LoanServicingCaseDTO dto) {
        return repository.findById(loanServicingCaseId)
                .flatMap(existingEntity -> {
                    LoanServicingCase updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setLoanServicingCaseId(existingEntity.getLoanServicingCaseId());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(Long loanServicingCaseId) {
        return repository.deleteById(loanServicingCaseId);
    }
}
