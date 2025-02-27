package com.catalis.core.lending.servicing.core.mappers.rate.v1;

import com.catalis.core.lending.servicing.interfaces.dtos.rate.v1.LoanRateChangeDTO;
import com.catalis.core.lending.servicing.models.entities.rate.v1.LoanRateChange;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanRateChangeMapper {
    LoanRateChangeDTO toDTO(LoanRateChange entity);
    LoanRateChange toEntity(LoanRateChangeDTO dto);
}
