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

import com.firefly.core.lending.servicing.interfaces.enums.repayment.v1.PaymentMethodEnum;
import com.firefly.core.lending.servicing.interfaces.enums.repayment.v1.PaymentStatusEnum;
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
public class LoanInstallmentRecordDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID loanInstallmentRecordId;

    @FilterableId
    @NotNull(message = "Loan servicing case ID is required")
    private UUID loanServicingCaseId;

    @FilterableId
    private UUID loanInstallmentPlanId;

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

    // Payment tracking fields
    @NotNull(message = "Payment method is required")
    private PaymentMethodEnum paymentMethod;

    @NotNull(message = "Payment status is required")
    private PaymentStatusEnum paymentStatus;

    @FilterableId
    private UUID paymentProviderId;

    @Size(max = 255, message = "External transaction reference cannot exceed 255 characters")
    private String externalTransactionReference;

    @Size(max = 500, message = "Note cannot exceed 500 characters")
    private String note;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;
}

