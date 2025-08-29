package com.firefly.core.lending.servicing.core.services.accrual.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.core.mappers.accrual.v1.LoanAccrualMapper;
import com.firefly.core.lending.servicing.interfaces.dtos.accrual.v1.LoanAccrualDTO;
import com.firefly.core.lending.servicing.models.entities.accrual.v1.LoanAccrual;
import com.firefly.core.lending.servicing.models.repositories.accrual.v1.LoanAccrualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Transactional
public class LoanAccrualServiceImpl implements LoanAccrualService {

    @Autowired
    private LoanAccrualRepository repository;

    @Autowired
    private LoanAccrualMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanAccrualDTO>> findAll(Long loanServicingCaseId, FilterRequest<LoanAccrualDTO> filterRequest) {
        filterRequest.getFilters().setLoanServicingCaseId(loanServicingCaseId);
        return FilterUtils.createFilter(
                LoanAccrual.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<LoanAccrualDTO> create(Long loanServicingCaseId, LoanAccrualDTO dto) {
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
    public Mono<LoanAccrualDTO> getById(Long loanServicingCaseId, Long loanAccrualId) {
        return Mono.defer(() -> repository.findById(loanAccrualId)
                .filter(entity -> Objects.equals(entity.getLoanServicingCaseId(), loanServicingCaseId))
                .map(mapper::toDTO));
    }

    @Override
    public Mono<LoanAccrualDTO> update(Long loanServicingCaseId, Long loanAccrualId, LoanAccrualDTO dto) {
        return repository.findById(loanAccrualId)
                .filter(entity -> Objects.equals(entity.getLoanServicingCaseId(), loanServicingCaseId))
                .flatMap(existingEntity -> {
                    existingEntity.setAccrualAmount(dto.getAccrualAmount());
                    existingEntity.setAccrualType(dto.getAccrualType());
                    existingEntity.setAccrualDate(dto.getAccrualDate());
                    existingEntity.setNote(dto.getNote());
                    existingEntity.setUpdatedAt(LocalDateTime.now());
                    return repository.save(existingEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(Long loanServicingCaseId, Long loanAccrualId) {
        return repository.findById(loanAccrualId)
                .filter(entity -> Objects.equals(entity.getLoanServicingCaseId(), loanServicingCaseId))
                .flatMap(repository::delete);
    }
}
