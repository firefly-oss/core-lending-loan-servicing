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


package com.firefly.core.lending.servicing.models.entities;

import com.firefly.core.lending.servicing.interfaces.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a loan restructuring event.
 * Tracks changes to loan terms when a loan is restructured due to financial hardship,
 * renegotiation, or other reasons. Maintains history of old and new terms.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("loan_restructuring")
public class LoanRestructuring {

    @Id
    @Column("loan_restructuring_id")
    private UUID loanRestructuringId;

    @Column("loan_servicing_case_id")
    private UUID loanServicingCaseId;

    @Column("restructuring_date")
    private LocalDate restructuringDate;

    @Column("reason")
    private String reason; // Reason for restructuring (e.g., "Financial hardship", "Renegotiation")

    // Old loan terms (before restructuring)
    @Column("old_principal_amount")
    private BigDecimal oldPrincipalAmount;

    @Column("old_interest_rate")
    private BigDecimal oldInterestRate;

    @Column("old_loan_term")
    private Integer oldLoanTerm;

    @Column("old_interest_calculation_method")
    private InterestCalculationMethodEnum oldInterestCalculationMethod;

    @Column("old_amortization_method")
    private AmortizationMethodEnum oldAmortizationMethod;

    @Column("old_payment_frequency")
    private PaymentFrequencyEnum oldPaymentFrequency;

    @Column("old_compounding_frequency")
    private CompoundingFrequencyEnum oldCompoundingFrequency;

    @Column("old_day_count_convention")
    private DayCountConventionEnum oldDayCountConvention;

    @Column("old_maturity_date")
    private LocalDate oldMaturityDate;

    // New loan terms (after restructuring)
    @Column("new_principal_amount")
    private BigDecimal newPrincipalAmount;

    @Column("new_interest_rate")
    private BigDecimal newInterestRate;

    @Column("new_loan_term")
    private Integer newLoanTerm;

    @Column("new_interest_calculation_method")
    private InterestCalculationMethodEnum newInterestCalculationMethod;

    @Column("new_amortization_method")
    private AmortizationMethodEnum newAmortizationMethod;

    @Column("new_payment_frequency")
    private PaymentFrequencyEnum newPaymentFrequency;

    @Column("new_compounding_frequency")
    private CompoundingFrequencyEnum newCompoundingFrequency;

    @Column("new_day_count_convention")
    private DayCountConventionEnum newDayCountConvention;

    @Column("new_maturity_date")
    private LocalDate newMaturityDate;

    @Column("approved_by")
    private UUID approvedBy; // User ID who approved the restructuring

    @Column("remarks")
    private String remarks;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}

