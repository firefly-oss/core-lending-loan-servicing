package com.firefly.core.lending.servicing.core.services.repayment.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.core.mappers.repayment.v1.LoanRepaymentScheduleMapper;
import com.firefly.core.lending.servicing.interfaces.dtos.repayment.v1.LoanRepaymentScheduleDTO;
import com.firefly.core.lending.servicing.models.entities.repayment.v1.LoanRepaymentSchedule;
import com.firefly.core.lending.servicing.models.repositories.repayment.v1.LoanRepaymentScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class LoanRepaymentScheduleServiceImpl implements LoanRepaymentScheduleService {

    @Autowired
    private LoanRepaymentScheduleRepository repository;

    @Autowired
    private LoanRepaymentScheduleMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanRepaymentScheduleDTO>> findAll(UUID loanServicingCaseId, FilterRequest<LoanRepaymentScheduleDTO> filterRequest) {
        filterRequest.getFilters().setLoanServicingCaseId(loanServicingCaseId);
        return FilterUtils.createFilter(
                LoanRepaymentSchedule.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<LoanRepaymentScheduleDTO> create(UUID loanServicingCaseId, LoanRepaymentScheduleDTO dto) {
        dto.setLoanServicingCaseId(loanServicingCaseId);
        LoanRepaymentSchedule entity = mapper.toEntity(dto);
        return Mono.just(entity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanRepaymentScheduleDTO> getById(UUID loanServicingCaseId, UUID loanRepaymentScheduleId) {
        return repository.findById(loanRepaymentScheduleId)
                .filter(entity -> entity.getLoanServicingCaseId().equals(loanServicingCaseId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanRepaymentScheduleDTO> update(UUID loanServicingCaseId, UUID loanRepaymentScheduleId, LoanRepaymentScheduleDTO dto) {
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
    public Mono<Void> delete(UUID loanServicingCaseId, UUID loanRepaymentScheduleId) {
        return repository.findById(loanRepaymentScheduleId)
                .filter(entity -> entity.getLoanServicingCaseId().equals(loanServicingCaseId))
                .flatMap(repository::delete);
    }
}