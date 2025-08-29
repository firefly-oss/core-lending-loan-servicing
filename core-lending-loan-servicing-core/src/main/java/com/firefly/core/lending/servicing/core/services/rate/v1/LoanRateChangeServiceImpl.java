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

@Service
@Transactional
public class LoanRateChangeServiceImpl implements LoanRateChangeService {

    @Autowired
    private LoanRateChangeRepository repository;

    @Autowired
    private LoanRateChangeMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanRateChangeDTO>> findAll(Long loanServicingCaseId, FilterRequest<LoanRateChangeDTO> filterRequest) {
        filterRequest.getFilters().setLoanServicingCaseId(loanServicingCaseId);
        return FilterUtils.createFilter(
                LoanRateChange.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<LoanRateChangeDTO> create(Long loanServicingCaseId, LoanRateChangeDTO dto) {
        dto.setLoanServicingCaseId(loanServicingCaseId);
        LoanRateChange entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(savedEntity -> mapper.toDTO(savedEntity));
    }

    @Override
    public Mono<LoanRateChangeDTO> getById(Long loanServicingCaseId, Long loanRateChangeId) {
        return repository.findById(loanRateChangeId)
                .filter(entity -> entity.getLoanServicingCaseId().equals(loanServicingCaseId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanRateChangeDTO> update(Long loanServicingCaseId, Long loanRateChangeId, LoanRateChangeDTO dto) {
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
    public Mono<Void> delete(Long loanServicingCaseId, Long loanRateChangeId) {
        return repository.findById(loanRateChangeId)
                .filter(entity -> entity.getLoanServicingCaseId().equals(loanServicingCaseId))
                .flatMap(repository::delete);
    }
}
