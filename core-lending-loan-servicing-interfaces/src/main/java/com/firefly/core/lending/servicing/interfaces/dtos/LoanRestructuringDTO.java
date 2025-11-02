/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.lending.servicing.interfaces.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.firefly.core.lending.servicing.interfaces.enums.*;
import com.firefly.core.utils.annotations.FilterableId;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanRestructuringDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID loanRestructuringId;

    @FilterableId
    @NotNull(message = "Loan servicing case ID is required")
    private UUID loanServicingCaseId;

    @NotNull(message = "Restructuring date is required")
    @PastOrPresent(message = "Restructuring date cannot be in the future")
    private LocalDate restructuringDate;

    @NotBlank(message = "Reason is required")
    @Size(max = 500, message = "Reason cannot exceed 500 characters")
    private String reason;

    // Old loan terms (before restructuring)
    @NotNull(message = "Old principal amount is required")
    @DecimalMin(value = "0.01", message = "Old principal amount must be greater than zero")
    @Digits(integer = 15, fraction = 2, message = "Old principal amount must have at most 15 integer digits and 2 decimal places")
    private BigDecimal oldPrincipalAmount;

    @NotNull(message = "Old interest rate is required")
    @DecimalMin(value = "0.0", message = "Old interest rate cannot be negative")
    @DecimalMax(value = "100.0", message = "Old interest rate cannot exceed 100%")
    @Digits(integer = 3, fraction = 4, message = "Old interest rate must have at most 3 integer digits and 4 decimal places")
    private BigDecimal oldInterestRate;

    @NotNull(message = "Old loan term is required")
    @Min(value = 1, message = "Old loan term must be at least 1")
    @Max(value = 600, message = "Old loan term cannot exceed 600 periods")
    private Integer oldLoanTerm;

    @NotNull(message = "Old interest calculation method is required")
    private InterestCalculationMethodEnum oldInterestCalculationMethod;

    @NotNull(message = "Old amortization method is required")
    private AmortizationMethodEnum oldAmortizationMethod;

    @NotNull(message = "Old payment frequency is required")
    private PaymentFrequencyEnum oldPaymentFrequency;

    @NotNull(message = "Old compounding frequency is required")
    private CompoundingFrequencyEnum oldCompoundingFrequency;

    @NotNull(message = "Old day count convention is required")
    private DayCountConventionEnum oldDayCountConvention;

    @NotNull(message = "Old maturity date is required")
    private LocalDate oldMaturityDate;

    // New loan terms (after restructuring)
    @NotNull(message = "New principal amount is required")
    @DecimalMin(value = "0.01", message = "New principal amount must be greater than zero")
    @Digits(integer = 15, fraction = 2, message = "New principal amount must have at most 15 integer digits and 2 decimal places")
    private BigDecimal newPrincipalAmount;

    @NotNull(message = "New interest rate is required")
    @DecimalMin(value = "0.0", message = "New interest rate cannot be negative")
    @DecimalMax(value = "100.0", message = "New interest rate cannot exceed 100%")
    @Digits(integer = 3, fraction = 4, message = "New interest rate must have at most 3 integer digits and 4 decimal places")
    private BigDecimal newInterestRate;

    @NotNull(message = "New loan term is required")
    @Min(value = 1, message = "New loan term must be at least 1")
    @Max(value = 600, message = "New loan term cannot exceed 600 periods")
    private Integer newLoanTerm;

    @NotNull(message = "New interest calculation method is required")
    private InterestCalculationMethodEnum newInterestCalculationMethod;

    @NotNull(message = "New amortization method is required")
    private AmortizationMethodEnum newAmortizationMethod;

    @NotNull(message = "New payment frequency is required")
    private PaymentFrequencyEnum newPaymentFrequency;

    @NotNull(message = "New compounding frequency is required")
    private CompoundingFrequencyEnum newCompoundingFrequency;

    @NotNull(message = "New day count convention is required")
    private DayCountConventionEnum newDayCountConvention;

    @NotNull(message = "New maturity date is required")
    @Future(message = "New maturity date must be in the future")
    private LocalDate newMaturityDate;

    @FilterableId
    private UUID approvedBy;

    @Size(max = 1000, message = "Remarks cannot exceed 1000 characters")
    private String remarks;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

