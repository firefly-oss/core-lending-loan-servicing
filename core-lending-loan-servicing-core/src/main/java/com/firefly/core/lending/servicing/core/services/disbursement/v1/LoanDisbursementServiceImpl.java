package com.firefly.core.lending.servicing.core.services.disbursement.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.core.mappers.disbursement.v1.LoanDisbursementMapper;
import com.firefly.core.lending.servicing.interfaces.dtos.disbursement.v1.LoanDisbursementDTO;
import com.firefly.core.lending.servicing.models.entities.disbursement.v1.LoanDisbursement;
import com.firefly.core.lending.servicing.models.repositories.disbursement.v1.LoanDisbursementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class LoanDisbursementServiceImpl implements LoanDisbursementService {

    @Autowired
    private LoanDisbursementRepository repository;

    @Autowired
    private LoanDisbursementMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanDisbursementDTO>> findAll(UUID loanServicingCaseId, FilterRequest<LoanDisbursementDTO> filterRequest) {
        filterRequest.getFilters().setLoanServicingCaseId(loanServicingCaseId);
        return FilterUtils.createFilter(
                LoanDisbursement.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<LoanDisbursementDTO> create(UUID loanServicingCaseId, LoanDisbursementDTO dto) {
        dto.setLoanServicingCaseId(loanServicingCaseId);
        LoanDisbursement entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanDisbursementDTO> getById(UUID loanServicingCaseId, UUID loanDisbursementId) {
        return repository.findById(loanDisbursementId)
                .filter(entity -> loanServicingCaseId.equals(entity.getLoanServicingCaseId()))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanDisbursementDTO> update(UUID loanServicingCaseId, UUID loanDisbursementId, LoanDisbursementDTO dto) {
        return repository.findById(loanDisbursementId)
                .filter(existing -> loanServicingCaseId.equals(existing.getLoanServicingCaseId()))
                .flatMap(existing -> {
                    dto.setLoanServicingCaseId(loanServicingCaseId);
                    dto.setLoanDisbursementId(loanDisbursementId);
                    LoanDisbursement entity = mapper.toEntity(dto);
                    return repository.save(entity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID loanServicingCaseId, UUID loanDisbursementId) {
        return repository.findById(loanDisbursementId)
                .filter(entity -> loanServicingCaseId.equals(entity.getLoanServicingCaseId()))
                .flatMap(repository::delete);
    }
}