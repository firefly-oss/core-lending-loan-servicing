package com.catalis.core.lending.servicing.interfaces.dtos.repayment.v1;

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
public class LoanRepaymentRecordDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long loanRepaymentRecordId;

    @FilterableId
    private Long loanServicingCaseId;

    @FilterableId
    private Long loanRepaymentScheduleId;

    @FilterableId
    private Long transactionId;

    private BigDecimal paymentAmount;
    private LocalDate paymentDate;
    private Boolean isPartialPayment;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}