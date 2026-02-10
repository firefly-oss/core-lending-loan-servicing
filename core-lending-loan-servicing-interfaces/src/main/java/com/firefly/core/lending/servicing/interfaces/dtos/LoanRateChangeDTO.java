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
import com.firefly.core.lending.servicing.interfaces.enums.ReasonCodeEnum;
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

