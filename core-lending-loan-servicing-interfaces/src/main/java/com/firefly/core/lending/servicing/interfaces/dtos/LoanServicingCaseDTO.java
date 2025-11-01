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
import com.firefly.core.lending.servicing.interfaces.enums.ServicingStatusEnum;
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
    @NotNull(message = "Product Catalog ID is required")
    private UUID productCatalogId; // Reference to product category/template

    @NotNull(message = "Servicing status is required")
    private ServicingStatusEnum servicingStatus;

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