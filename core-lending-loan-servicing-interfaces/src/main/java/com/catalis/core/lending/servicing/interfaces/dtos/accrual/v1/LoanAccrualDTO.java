package com.catalis.core.lending.servicing.interfaces.dtos.accrual.v1;

import com.catalis.common.core.filters.FilterableId;
import com.catalis.core.lending.servicing.interfaces.enums.accrual.v1.AccrualTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanAccrualDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long loanAccrualId;

    @FilterableId
    private Long loanServicingCaseId;

    private BigDecimal accrualAmount;
    private AccrualTypeEnum accrualType;
    private LocalDate accrualDate;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

