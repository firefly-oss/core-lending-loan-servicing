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
import com.firefly.core.lending.servicing.interfaces.enums.EscrowTypeEnum;
import com.firefly.core.utils.annotations.FilterableId;
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
public class LoanEscrowDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID loanEscrowId;

    @FilterableId
    @NotNull(message = "Loan servicing case ID is required")
    private UUID loanServicingCaseId;

    @NotNull(message = "Escrow type is required")
    private EscrowTypeEnum escrowType;

    @NotNull(message = "Monthly payment amount is required")
    @DecimalMin(value = "0.0", message = "Monthly payment amount cannot be negative")
    @Digits(integer = 15, fraction = 2, message = "Monthly payment amount must have at most 15 integer digits and 2 decimal places")
    private BigDecimal monthlyPaymentAmount;

    @NotNull(message = "Current balance is required")
    @DecimalMin(value = "0.0", message = "Current balance cannot be negative")
    @Digits(integer = 15, fraction = 2, message = "Current balance must have at most 15 integer digits and 2 decimal places")
    private BigDecimal currentBalance;

    @NotNull(message = "Target balance is required")
    @DecimalMin(value = "0.0", message = "Target balance cannot be negative")
    @Digits(integer = 15, fraction = 2, message = "Target balance must have at most 15 integer digits and 2 decimal places")
    private BigDecimal targetBalance;

    @NotNull(message = "Annual disbursement amount is required")
    @DecimalMin(value = "0.0", message = "Annual disbursement amount cannot be negative")
    @Digits(integer = 15, fraction = 2, message = "Annual disbursement amount must have at most 15 integer digits and 2 decimal places")
    private BigDecimal annualDisbursementAmount;

    @Future(message = "Next disbursement date must be in the future")
    private LocalDate nextDisbursementDate;

    @PastOrPresent(message = "Last analysis date cannot be in the future")
    private LocalDate lastAnalysisDate;

    @Future(message = "Next analysis date must be in the future")
    private LocalDate nextAnalysisDate;

    @NotNull(message = "Active status is required")
    private Boolean isActive;

    @NotBlank(message = "Payee name is required")
    @Size(max = 255, message = "Payee name cannot exceed 255 characters")
    private String payeeName;

    @Size(max = 100, message = "Payee account number cannot exceed 100 characters")
    private String payeeAccountNumber;

    @Size(max = 1000, message = "Remarks cannot exceed 1000 characters")
    private String remarks;
}

