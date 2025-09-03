package com.firefly.core.lending.servicing.interfaces.dtos.repayment.v1;

import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanRepaymentRecordDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID loanRepaymentRecordId;

    @FilterableId
    @NotNull(message = "Loan servicing case ID is required")
    private UUID loanServicingCaseId;

    @FilterableId
    private UUID loanRepaymentScheduleId;

    @FilterableId
    @NotNull(message = "Transaction ID is required")
    private UUID transactionId;

    @NotNull(message = "Payment amount is required")
    @DecimalMin(value = "0.01", message = "Payment amount must be greater than 0")
    @Digits(integer = 15, fraction = 2, message = "Payment amount must have at most 15 integer digits and 2 decimal places")
    private BigDecimal paymentAmount;

    @NotNull(message = "Payment date is required")
    @PastOrPresent(message = "Payment date cannot be in the future")
    private LocalDate paymentDate;

    @NotNull(message = "Partial payment flag is required")
    private Boolean isPartialPayment;

    @Size(max = 500, message = "Note cannot exceed 500 characters")
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}