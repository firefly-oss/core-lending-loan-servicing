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
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanDisbursementPlanDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID loanDisbursementPlanId;

    @FilterableId
    @NotNull(message = "Loan servicing case ID is required")
    private UUID loanServicingCaseId;

    @NotNull(message = "Planned disbursement date is required")
    @Future(message = "Planned disbursement date must be in the future")
    private LocalDate plannedDisbursementDate;

    @NotNull(message = "Planned amount is required")
    @DecimalMin(value = "0.01", message = "Planned amount must be greater than zero")
    @Digits(integer = 15, fraction = 2, message = "Planned amount must have at most 15 integer digits and 2 decimal places")
    private BigDecimal plannedAmount;

    @PastOrPresent(message = "Actual disbursement date cannot be in the future")
    private LocalDate actualDisbursementDate;

    @DecimalMin(value = "0.0", message = "Actual amount cannot be negative")
    @Digits(integer = 15, fraction = 2, message = "Actual amount must have at most 15 integer digits and 2 decimal places")
    private BigDecimal actualAmount;

    @NotNull(message = "Disbursement number is required")
    @Min(value = 1, message = "Disbursement number must be at least 1")
    private Integer disbursementNumber;

    @NotNull(message = "Completion status is required")
    private Boolean isCompleted;

    @NotBlank(message = "Purpose is required")
    @Size(max = 500, message = "Purpose cannot exceed 500 characters")
    private String purpose;

    @Size(max = 1000, message = "Remarks cannot exceed 1000 characters")
    private String remarks;
}

