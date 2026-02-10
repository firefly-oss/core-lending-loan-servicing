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
import org.fireflyframework.utils.annotations.FilterableId;
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
public class LoanServicingCaseDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID loanServicingCaseId;

    @FilterableId
    @NotNull(message = "Contract ID is required")
    private UUID contractId;

    @FilterableId
    @NotNull(message = "Product ID is required")
    private UUID productId;

    @FilterableId
    @NotNull(message = "Application ID is required")
    private UUID applicationId; // Reference to loan application (from core-lending-loan-origination)

    @FilterableId
    private UUID proposedOfferId; // Reference to the accepted proposed offer (from core-lending-loan-origination)

    @FilterableId
    @NotNull(message = "Product Catalog ID is required")
    private UUID productCatalogId; // Reference to product category/template

    @NotNull(message = "Servicing status is required")
    private ServicingStatusEnum servicingStatus;

    // Loan configuration fields - common to all loan types
    @NotNull(message = "Principal amount is required")
    @DecimalMin(value = "0.01", message = "Principal amount must be greater than zero")
    @Digits(integer = 15, fraction = 2, message = "Principal amount must have at most 15 integer digits and 2 decimal places")
    private BigDecimal principalAmount;

    @NotNull(message = "Interest rate is required")
    @DecimalMin(value = "0.0", message = "Interest rate cannot be negative")
    @DecimalMax(value = "100.0", message = "Interest rate cannot exceed 100%")
    @Digits(integer = 3, fraction = 4, message = "Interest rate must have at most 3 integer digits and 4 decimal places")
    private BigDecimal interestRate; // Annual interest rate as percentage (e.g., 5.25 for 5.25%)

    @NotNull(message = "Loan term is required")
    @Min(value = 1, message = "Loan term must be at least 1")
    @Max(value = 600, message = "Loan term cannot exceed 600 periods")
    private Integer loanTerm; // Number of payment periods

    @NotNull(message = "Interest calculation method is required")
    private InterestCalculationMethodEnum interestCalculationMethod;

    @NotNull(message = "Amortization method is required")
    private AmortizationMethodEnum amortizationMethod;

    @NotNull(message = "Payment frequency is required")
    private PaymentFrequencyEnum paymentFrequency;

    @NotNull(message = "Compounding frequency is required")
    private CompoundingFrequencyEnum compoundingFrequency;

    @NotNull(message = "Day count convention is required")
    private DayCountConventionEnum dayCountConvention;

    @NotNull(message = "Origination date is required")
    @PastOrPresent(message = "Origination date cannot be in the future")
    private LocalDate originationDate;

    @NotNull(message = "Maturity date is required")
    @Future(message = "Maturity date must be in the future")
    private LocalDate maturityDate;

    @Size(max = 1000, message = "Remarks cannot exceed 1000 characters")
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}