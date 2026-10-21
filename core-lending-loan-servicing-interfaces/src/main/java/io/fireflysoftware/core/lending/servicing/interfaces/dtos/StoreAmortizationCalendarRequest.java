/*
 * Copyright 2025 Firefly Software Foundation
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


package io.fireflysoftware.core.lending.servicing.interfaces.dtos;

import io.fireflysoftware.core.lending.servicing.interfaces.enums.AmortizationMethodEnum;
import io.fireflysoftware.core.lending.servicing.interfaces.enums.PaymentFrequencyEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Inputs for storing an amortization calendar on an existing servicing case.
 * The installment amount, when present, is the priced periodic payment.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreAmortizationCalendarRequest {

    @NotNull
    @DecimalMin(value = "0.01", message = "Principal must be positive")
    private BigDecimal principal;

    @NotNull
    @DecimalMin(value = "0.0", message = "Interest rate cannot be negative")
    private BigDecimal annualRatePercent;

    @NotNull
    @Min(value = 1, message = "Term must be at least 1")
    private Integer term;

    @NotNull
    private PaymentFrequencyEnum paymentFrequency;

    private AmortizationMethodEnum amortizationMethod;

    @NotNull
    private LocalDate firstDueDate;

    @DecimalMin(value = "0.0", message = "Installment amount cannot be negative")
    private BigDecimal installmentAmount;
}
