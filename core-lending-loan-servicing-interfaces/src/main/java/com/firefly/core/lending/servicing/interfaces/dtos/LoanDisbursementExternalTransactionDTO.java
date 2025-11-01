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
import com.firefly.core.utils.annotations.FilterableId;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanDisbursementExternalTransactionDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID loanDisbursementExternalTransactionId;

    @FilterableId
    @NotNull(message = "Loan disbursement ID is required")
    private UUID loanDisbursementId;

    @NotNull(message = "Payment provider ID is required")
    private UUID paymentProviderId;

    @Size(max = 255, message = "PSP transaction ID cannot exceed 255 characters")
    private String pspTransactionId;

    @Size(max = 255, message = "PSP transaction reference cannot exceed 255 characters")
    private String pspTransactionReference;

    @NotNull(message = "Transaction amount is required")
    @DecimalMin(value = "0.01", message = "Transaction amount must be greater than 0")
    @Digits(integer = 15, fraction = 2, message = "Transaction amount must have at most 15 integer digits and 2 decimal places")
    private BigDecimal transactionAmount;

    @NotNull(message = "Transaction currency is required")
    @Size(min = 3, max = 3, message = "Transaction currency must be a 3-letter ISO code")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Transaction currency must be a valid 3-letter ISO code")
    private String transactionCurrency;

    @Size(max = 50, message = "PSP status cannot exceed 50 characters")
    private String pspStatus;

    @Size(max = 1000, message = "PSP status message cannot exceed 1000 characters")
    private String pspStatusMessage;

    @Size(max = 255, message = "Recipient account number cannot exceed 255 characters")
    private String recipientAccountNumber;

    @Size(max = 255, message = "Recipient name cannot exceed 255 characters")
    private String recipientName;

    @NotNull(message = "Transaction date is required")
    @PastOrPresent(message = "Transaction date cannot be in the future")
    private LocalDateTime transactionDate;

    private String pspResponsePayload;

    @Size(max = 500, message = "Note cannot exceed 500 characters")
    private String note;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;
}

