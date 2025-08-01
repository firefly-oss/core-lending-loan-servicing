package com.catalis.core.lending.servicing.core.services.repayment.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.lending.servicing.core.mappers.repayment.v1.LoanRepaymentScheduleMapper;
import com.catalis.core.lending.servicing.interfaces.dtos.repayment.v1.LoanRepaymentScheduleDTO;
import com.catalis.core.lending.servicing.models.entities.repayment.v1.LoanRepaymentSchedule;
import com.catalis.core.lending.servicing.models.repositories.repayment.v1.LoanRepaymentScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class LoanRepaymentScheduleServiceImpl implements LoanRepaymentScheduleService {

    @Autowired
    private LoanRepaymentScheduleRepository repository;

    @Autowired
    private LoanRepaymentScheduleMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanRepaymentScheduleDTO>> findAll(Long loanServicingCaseId, FilterRequest<LoanRepaymentScheduleDTO> filterRequest) {
        filterRequest.getFilters().setLoanServicingCaseId(loanServicingCaseId);
        return FilterUtils.createFilter(
                LoanRepaymentSchedule.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<LoanRepaymentScheduleDTO> create(Long loanServicingCaseId, LoanRepaymentScheduleDTO dto) {
        dto.setLoanServicingCaseId(loanServicingCaseId);
        LoanRepaymentSchedule entity = mapper.toEntity(dto);
        return Mono.just(entity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanRepaymentScheduleDTO> getById(Long loanServicingCaseId, Long loanRepaymentScheduleId) {
        return repository.findById(loanRepaymentScheduleId)
                .filter(entity -> entity.getLoanServicingCaseId().equals(loanServicingCaseId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanRepaymentScheduleDTO> update(Long loanServicingCaseId, Long loanRepaymentScheduleId, LoanRepaymentScheduleDTO dto) {
        return repository.findById(loanRepaymentScheduleId)
                .filter(entity -> entity.getLoanServicingCaseId().equals(loanServicingCaseId))
                .flatMap(existingEntity -> {
                    LoanRepaymentSchedule updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setLoanRepaymentScheduleId(loanRepaymentScheduleId);
                    updatedEntity.setLoanServicingCaseId(loanServicingCaseId);
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(Long loanServicingCaseId, Long loanRepaymentScheduleId) {
        return repository.findById(loanRepaymentScheduleId)
                .filter(entity -> entity.getLoanServicingCaseId().equals(loanServicingCaseId))
                .flatMap(repository::delete);
    }
}