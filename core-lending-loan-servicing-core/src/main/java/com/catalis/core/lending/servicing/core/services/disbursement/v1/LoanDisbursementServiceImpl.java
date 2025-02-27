package com.catalis.core.lending.servicing.core.services.disbursement.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.lending.servicing.core.mappers.disbursement.v1.LoanDisbursementMapper;
import com.catalis.core.lending.servicing.interfaces.dtos.disbursement.v1.LoanDisbursementDTO;
import com.catalis.core.lending.servicing.models.entities.disbursement.v1.LoanDisbursement;
import com.catalis.core.lending.servicing.models.repositories.disbursement.v1.LoanDisbursementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class LoanDisbursementServiceImpl implements LoanDisbursementService {

    @Autowired
    private LoanDisbursementRepository repository;

    @Autowired
    private LoanDisbursementMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanDisbursementDTO>> findAll(Long loanServicingCaseId, FilterRequest<LoanDisbursementDTO> filterRequest) {
        filterRequest.getFilters().setLoanServicingCaseId(loanServicingCaseId);
        return FilterUtils.createFilter(
                LoanDisbursement.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<LoanDisbursementDTO> create(Long loanServicingCaseId, LoanDisbursementDTO dto) {
        dto.setLoanServicingCaseId(loanServicingCaseId);
        LoanDisbursement entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanDisbursementDTO> getById(Long loanServicingCaseId, Long loanDisbursementId) {
        return repository.findById(loanDisbursementId)
                .filter(entity -> loanServicingCaseId.equals(entity.getLoanServicingCaseId()))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanDisbursementDTO> update(Long loanServicingCaseId, Long loanDisbursementId, LoanDisbursementDTO dto) {
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
    public Mono<Void> delete(Long loanServicingCaseId, Long loanDisbursementId) {
        return repository.findById(loanDisbursementId)
                .filter(entity -> loanServicingCaseId.equals(entity.getLoanServicingCaseId()))
                .flatMap(repository::delete);
    }
}