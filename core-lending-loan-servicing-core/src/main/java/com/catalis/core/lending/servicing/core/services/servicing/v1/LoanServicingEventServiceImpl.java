package com.catalis.core.lending.servicing.core.services.servicing.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.lending.servicing.core.mappers.servicing.v1.LoanServicingEventMapper;
import com.catalis.core.lending.servicing.interfaces.dtos.servicing.v1.LoanServicingEventDTO;
import com.catalis.core.lending.servicing.models.entities.servicing.v1.LoanServicingEvent;
import com.catalis.core.lending.servicing.models.repositories.servicing.v1.LoanServicingEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class LoanServicingEventServiceImpl implements LoanServicingEventService {

    @Autowired
    private LoanServicingEventRepository repository;

    @Autowired
    private LoanServicingEventMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanServicingEventDTO>> findAll(Long loanServicingCaseId, FilterRequest<LoanServicingEventDTO> filterRequest) {
        filterRequest.getFilters().setLoanServicingCaseId(loanServicingCaseId);
        return FilterUtils.createFilter(
                LoanServicingEvent.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<LoanServicingEventDTO> create(Long loanServicingCaseId, LoanServicingEventDTO dto) {
        dto.setLoanServicingCaseId(loanServicingCaseId);
        LoanServicingEvent entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanServicingEventDTO> getById(Long loanServicingCaseId, Long loanServicingEventId) {
        return repository.findById(loanServicingEventId)
                .filter(event -> event.getLoanServicingCaseId().equals(loanServicingCaseId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanServicingEventDTO> update(Long loanServicingCaseId, Long loanServicingEventId, LoanServicingEventDTO dto) {
        return repository.findById(loanServicingEventId)
                .filter(entity -> entity.getLoanServicingCaseId().equals(loanServicingCaseId))
                .flatMap(existingEntity -> {
                    existingEntity.setEventType(dto.getEventType());
                    existingEntity.setEventDate(dto.getEventDate());
                    existingEntity.setDescription(dto.getDescription());
                    existingEntity.setUpdatedAt(dto.getUpdatedAt());
                    return repository.save(existingEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(Long loanServicingCaseId, Long loanServicingEventId) {
        return repository.findById(loanServicingEventId)
                .filter(event -> event.getLoanServicingCaseId().equals(loanServicingCaseId))
                .flatMap(repository::delete);
    }
}