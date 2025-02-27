package com.catalis.core.lending.servicing.core.mappers.repayment.v1;

import com.catalis.core.lending.servicing.interfaces.dtos.repayment.v1.LoanRepaymentScheduleDTO;
import com.catalis.core.lending.servicing.models.entities.repayment.v1.LoanRepaymentSchedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanRepaymentScheduleMapper {
    LoanRepaymentScheduleDTO toDTO(LoanRepaymentSchedule entity);
    LoanRepaymentSchedule toEntity(LoanRepaymentScheduleDTO dto);
}
