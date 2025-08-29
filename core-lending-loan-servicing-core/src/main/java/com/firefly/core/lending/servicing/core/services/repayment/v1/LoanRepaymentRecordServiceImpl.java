package com.firefly.core.lending.servicing.core.services.repayment.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.core.mappers.repayment.v1.LoanRepaymentRecordMapper;
import com.firefly.core.lending.servicing.interfaces.dtos.repayment.v1.LoanRepaymentRecordDTO;
import com.firefly.core.lending.servicing.models.entities.repayment.v1.LoanRepaymentRecord;
import com.firefly.core.lending.servicing.models.repositories.repayment.v1.LoanRepaymentRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class LoanRepaymentRecordServiceImpl implements LoanRepaymentRecordService {

    @Autowired
    private LoanRepaymentRecordRepository repository;

    @Autowired
    private LoanRepaymentRecordMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanRepaymentRecordDTO>> findAll(Long loanServicingCaseId, FilterRequest<LoanRepaymentRecordDTO> filterRequest) {
        filterRequest.getFilters().setLoanServicingCaseId(loanServicingCaseId);
        return FilterUtils.createFilter(
                LoanRepaymentRecord.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<LoanRepaymentRecordDTO> create(Long loanServicingCaseId, LoanRepaymentRecordDTO dto) {
        dto.setLoanServicingCaseId(loanServicingCaseId);
        LoanRepaymentRecord entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanRepaymentRecordDTO> getById(Long loanServicingCaseId, Long loanRepaymentRecordId) {
        return repository.findById(loanRepaymentRecordId)
                .filter(record -> record.getLoanServicingCaseId().equals(loanServicingCaseId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanRepaymentRecordDTO> update(Long loanServicingCaseId, Long loanRepaymentRecordId, LoanRepaymentRecordDTO dto) {
        return repository.findById(loanRepaymentRecordId)
                .filter(record -> record.getLoanServicingCaseId().equals(loanServicingCaseId))
                .flatMap(existingRecord -> {
                    dto.setLoanRepaymentRecordId(loanRepaymentRecordId);
                    dto.setLoanServicingCaseId(loanServicingCaseId);
                    LoanRepaymentRecord updatedEntity = mapper.toEntity(dto);
                    return repository.save(updatedEntity).map(mapper::toDTO);
                });
    }

    @Override
    public Mono<Void> delete(Long loanServicingCaseId, Long loanRepaymentRecordId) {
        return repository.findById(loanRepaymentRecordId)
                .filter(record -> record.getLoanServicingCaseId().equals(loanServicingCaseId))
                .flatMap(repository::delete);
    }
}