package com.firefly.core.lending.servicing.interfaces.dtos.disbursement.v1;

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
public class LoanDisbursementDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long loanDisbursementId;

    @FilterableId
    private Long loanServicingCaseId;

    @FilterableId
    private Long transactionId;

    private BigDecimal disbursementAmount;
    private LocalDate disbursementDate;
    private Boolean isFinalDisbursement;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

