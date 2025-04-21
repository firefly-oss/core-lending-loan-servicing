package com.catalis.core.lending.servicing.interfaces.dtos.servicing.v1;

import com.catalis.core.lending.servicing.interfaces.enums.servicing.v1.ServicingStatusEnum;
import com.catalis.core.utils.annotations.FilterableId;
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
public class LoanServicingCaseDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long loanServicingCaseId;

    @FilterableId
    private Long contractId;

    @FilterableId
    private Long productId;

    @FilterableId
    private Long accountId;

    private ServicingStatusEnum servicingStatus;
    private BigDecimal principalOutstanding;
    private BigDecimal interestOutstanding;
    private BigDecimal feesOutstanding;
    private LocalDate originationDate;
    private LocalDate maturityDate;
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}