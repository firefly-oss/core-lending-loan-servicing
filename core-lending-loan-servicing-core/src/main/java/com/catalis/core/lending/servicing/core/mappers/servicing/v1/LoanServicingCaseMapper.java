package com.catalis.core.lending.servicing.core.mappers.servicing.v1;

import com.catalis.core.lending.servicing.interfaces.dtos.servicing.v1.LoanServicingCaseDTO;
import com.catalis.core.lending.servicing.models.entities.servicing.v1.LoanServicingCase;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanServicingCaseMapper {
    LoanServicingCaseDTO toDTO(LoanServicingCase entity);
    LoanServicingCase toEntity(LoanServicingCaseDTO dto);
}
