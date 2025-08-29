package com.firefly.core.lending.servicing.core.mappers.accrual.v1;

import com.firefly.core.lending.servicing.interfaces.dtos.accrual.v1.LoanAccrualDTO;
import com.firefly.core.lending.servicing.models.entities.accrual.v1.LoanAccrual;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanAccrualMapper {
    LoanAccrualDTO toDTO(LoanAccrual entity);
    LoanAccrual toEntity(LoanAccrualDTO dto);
}
