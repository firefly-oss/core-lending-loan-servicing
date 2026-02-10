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
import com.firefly.core.lending.servicing.interfaces.enums.RebateTypeEnum;
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
public class LoanRebateDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID loanRebateId;

    @FilterableId
    @NotNull(message = "Loan servicing case ID is required")
    private UUID loanServicingCaseId;

    @NotNull(message = "Rebate type is required")
    private RebateTypeEnum rebateType;

    @NotNull(message = "Rebate amount is required")
    @DecimalMin(value = "0.01", message = "Rebate amount must be greater than zero")
    @Digits(integer = 15, fraction = 2, message = "Rebate amount must have at most 15 integer digits and 2 decimal places")
    private BigDecimal rebateAmount;

    @NotNull(message = "Rebate date is required")
    @PastOrPresent(message = "Rebate date cannot be in the future")
    private LocalDate rebateDate;

    @FilterableId
    private UUID distributorId;

    @FilterableId
    private UUID distributorAgencyId;

    @FilterableId
    private UUID distributorAgentId;

    @DecimalMin(value = "0.0", message = "Distributor commission cannot be negative")
    @Digits(integer = 15, fraction = 2, message = "Distributor commission must have at most 15 integer digits and 2 decimal places")
    private BigDecimal distributorCommission;

    @NotNull(message = "Processing status is required")
    private Boolean isProcessed;

    @PastOrPresent(message = "Processed date cannot be in the future")
    private LocalDate processedDate;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @Size(max = 1000, message = "Remarks cannot exceed 1000 characters")
    private String remarks;
}

