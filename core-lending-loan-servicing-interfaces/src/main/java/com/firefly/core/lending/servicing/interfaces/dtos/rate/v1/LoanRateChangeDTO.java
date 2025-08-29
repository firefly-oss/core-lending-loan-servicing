package com.firefly.core.lending.servicing.interfaces.dtos.rate.v1;

import com.firefly.core.lending.servicing.interfaces.enums.rate.v1.ReasonCodeEnum;
import com.firefly.core.utils.annotations.FilterableId;
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
public class LoanRateChangeDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long loanRateChangeId;

    @FilterableId
    private Long loanServicingCaseId;

    private BigDecimal oldInterestRate;
    private BigDecimal newInterestRate;
    private LocalDate effectiveDate;
    private ReasonCodeEnum reasonCode;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

