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
public class LoanBalanceDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID loanBalanceId;

    @FilterableId
    @NotNull(message = "Loan servicing case ID is required")
    private UUID loanServicingCaseId;

    @NotNull(message = "Principal outstanding is required")
    @DecimalMin(value = "0.0", message = "Principal outstanding cannot be negative")
    @Digits(integer = 15, fraction = 2, message = "Principal outstanding must have at most 15 integer digits and 2 decimal places")
    private BigDecimal principalOutstanding;

    @NotNull(message = "Interest outstanding is required")
    @DecimalMin(value = "0.0", message = "Interest outstanding cannot be negative")
    @Digits(integer = 15, fraction = 2, message = "Interest outstanding must have at most 15 integer digits and 2 decimal places")
    private BigDecimal interestOutstanding;

    @NotNull(message = "Fees outstanding is required")
    @DecimalMin(value = "0.0", message = "Fees outstanding cannot be negative")
    @Digits(integer = 15, fraction = 2, message = "Fees outstanding must have at most 15 integer digits and 2 decimal places")
    private BigDecimal feesOutstanding;

    @NotNull(message = "Total outstanding is required")
    @DecimalMin(value = "0.0", message = "Total outstanding cannot be negative")
    @Digits(integer = 15, fraction = 2, message = "Total outstanding must have at most 15 integer digits and 2 decimal places")
    private BigDecimal totalOutstanding;

    @NotNull(message = "Balance date is required")
    @PastOrPresent(message = "Balance date cannot be in the future")
    private LocalDate balanceDate;

    @NotNull(message = "Is current flag is required")
    private Boolean isCurrent;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

