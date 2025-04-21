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
public class LoanRepaymentScheduleDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long loanRepaymentScheduleId;

    @FilterableId
    private Long loanServicingCaseId;

    private Integer installmentNumber;
    private LocalDate dueDate;
    private BigDecimal principalDue;
    private BigDecimal interestDue;
    private BigDecimal feeDue;
    private BigDecimal totalDue;
    private Boolean isPaid;
    private LocalDate paidDate;
    private BigDecimal paidAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}