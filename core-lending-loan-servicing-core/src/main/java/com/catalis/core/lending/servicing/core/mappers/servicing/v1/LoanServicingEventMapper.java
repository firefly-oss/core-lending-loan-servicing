package com.catalis.core.lending.servicing.core.mappers.servicing.v1;

import com.catalis.core.lending.servicing.interfaces.dtos.servicing.v1.LoanServicingEventDTO;
import com.catalis.core.lending.servicing.models.entities.servicing.v1.LoanServicingEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanServicingEventMapper {
    LoanServicingEventDTO toDTO(LoanServicingEvent entity);
    LoanServicingEvent toEntity(LoanServicingEventDTO dto);
}