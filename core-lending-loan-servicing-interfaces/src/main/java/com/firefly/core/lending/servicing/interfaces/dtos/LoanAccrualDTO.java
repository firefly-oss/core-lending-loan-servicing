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
import com.firefly.core.lending.servicing.interfaces.enums.AccrualTypeEnum;
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
public class LoanAccrualDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID loanAccrualId;

    @FilterableId
    @NotNull(message = "Loan servicing case ID is required")
    private UUID loanServicingCaseId;

    @NotNull(message = "Accrual amount is required")
    @DecimalMin(value = "0.01", message = "Accrual amount must be greater than 0")
    @Digits(integer = 15, fraction = 2, message = "Accrual amount must have at most 15 integer digits and 2 decimal places")
    private BigDecimal accrualAmount;

    @NotNull(message = "Accrual type is required")
    private AccrualTypeEnum accrualType;

    @NotNull(message = "Accrual date is required")
    @PastOrPresent(message = "Accrual date cannot be in the future")
    private LocalDate accrualDate;

    @Size(max = 500, message = "Note cannot exceed 500 characters")
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

