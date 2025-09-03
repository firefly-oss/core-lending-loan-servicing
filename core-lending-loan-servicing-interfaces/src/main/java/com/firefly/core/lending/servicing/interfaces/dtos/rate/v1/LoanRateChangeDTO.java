package com.firefly.core.lending.servicing.interfaces.dtos.rate.v1;

import com.firefly.core.lending.servicing.interfaces.enums.rate.v1.ReasonCodeEnum;
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
public class LoanRateChangeDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID loanRateChangeId;

    @FilterableId
    @NotNull(message = "Loan servicing case ID is required")
    private UUID loanServicingCaseId;

    @NotNull(message = "Old interest rate is required")
    @DecimalMin(value = "0.0", message = "Old interest rate cannot be negative")
    @DecimalMax(value = "100.0", message = "Old interest rate cannot exceed 100%")
    @Digits(integer = 3, fraction = 4, message = "Old interest rate must have at most 3 integer digits and 4 decimal places")
    private BigDecimal oldInterestRate;

    @NotNull(message = "New interest rate is required")
    @DecimalMin(value = "0.0", message = "New interest rate cannot be negative")
    @DecimalMax(value = "100.0", message = "New interest rate cannot exceed 100%")
    @Digits(integer = 3, fraction = 4, message = "New interest rate must have at most 3 integer digits and 4 decimal places")
    private BigDecimal newInterestRate;

    @NotNull(message = "Effective date is required")
    @PastOrPresent(message = "Effective date cannot be in the future")
    private LocalDate effectiveDate;

    @NotNull(message = "Reason code is required")
    private ReasonCodeEnum reasonCode;

    @Size(max = 500, message = "Note cannot exceed 500 characters")
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

