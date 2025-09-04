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
public class LoanRepaymentScheduleDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID loanRepaymentScheduleId;

    @FilterableId
    @NotNull(message = "Loan servicing case ID is required")
    private UUID loanServicingCaseId;

    @NotNull(message = "Installment number is required")
    @Min(value = 1, message = "Installment number must be at least 1")
    private Integer installmentNumber;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;

    @NotNull(message = "Principal due is required")
    @DecimalMin(value = "0.0", message = "Principal due cannot be negative")
    @Digits(integer = 15, fraction = 2, message = "Principal due must have at most 15 integer digits and 2 decimal places")
    private BigDecimal principalDue;

    @NotNull(message = "Interest due is required")
    @DecimalMin(value = "0.0", message = "Interest due cannot be negative")
    @Digits(integer = 15, fraction = 2, message = "Interest due must have at most 15 integer digits and 2 decimal places")
    private BigDecimal interestDue;

    @NotNull(message = "Fee due is required")
    @DecimalMin(value = "0.0", message = "Fee due cannot be negative")
    @Digits(integer = 15, fraction = 2, message = "Fee due must have at most 15 integer digits and 2 decimal places")
    private BigDecimal feeDue;

    @NotNull(message = "Total due is required")
    @DecimalMin(value = "0.0", message = "Total due cannot be negative")
    @Digits(integer = 15, fraction = 2, message = "Total due must have at most 15 integer digits and 2 decimal places")
    private BigDecimal totalDue;

    @NotNull(message = "Paid flag is required")
    private Boolean isPaid;

    @PastOrPresent(message = "Paid date cannot be in the future")
    private LocalDate paidDate;

    @DecimalMin(value = "0.0", message = "Paid amount cannot be negative")
    @Digits(integer = 15, fraction = 2, message = "Paid amount must have at most 15 integer digits and 2 decimal places")
    private BigDecimal paidAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}