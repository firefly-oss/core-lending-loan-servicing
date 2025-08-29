package com.firefly.core.lending.servicing.core.mappers.disbursement.v1;

import com.firefly.core.lending.servicing.interfaces.dtos.disbursement.v1.LoanDisbursementDTO;
import com.firefly.core.lending.servicing.models.entities.disbursement.v1.LoanDisbursement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanDisbursementMapper {
    LoanDisbursementDTO toDTO(LoanDisbursement entity);
    LoanDisbursement toEntity(LoanDisbursementDTO dto);
}
