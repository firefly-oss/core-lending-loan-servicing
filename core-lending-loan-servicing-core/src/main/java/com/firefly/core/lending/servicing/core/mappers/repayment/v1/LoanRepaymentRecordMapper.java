package com.firefly.core.lending.servicing.core.mappers.repayment.v1;

import com.firefly.core.lending.servicing.interfaces.dtos.repayment.v1.LoanRepaymentRecordDTO;
import com.firefly.core.lending.servicing.models.entities.repayment.v1.LoanRepaymentRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanRepaymentRecordMapper {
    LoanRepaymentRecordDTO toDTO(LoanRepaymentRecord entity);
    LoanRepaymentRecord toEntity(LoanRepaymentRecordDTO dto);
}
