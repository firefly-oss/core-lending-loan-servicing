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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("loan_servicing_case")
public class LoanServicingCase {

    @Id
    @Column("loan_servicing_case_id")
    private UUID loanServicingCaseId;

    @Column("contract_id")
    private UUID contractId;

    @Column("product_id")
    private UUID productId;

    @Column("application_id")
    private UUID applicationId; // Reference to loan application (from core-lending-loan-origination)

    @Column("proposed_offer_id")
    private UUID proposedOfferId; // Reference to the accepted proposed offer (from core-lending-loan-origination)

    @Column("product_catalog_id")
    private UUID productCatalogId; // Reference to product category/template

    @Column("servicing_status")
    private ServicingStatusEnum servicingStatus;

    // Loan configuration fields - common to all loan types
    @Column("principal_amount")
    private BigDecimal principalAmount;

    @Column("interest_rate")
    private BigDecimal interestRate; // Annual interest rate as percentage

    @Column("loan_term")
    private Integer loanTerm; // Number of payment periods

    @Column("interest_calculation_method")
    private InterestCalculationMethodEnum interestCalculationMethod;

    @Column("amortization_method")
    private AmortizationMethodEnum amortizationMethod;

    @Column("payment_frequency")
    private PaymentFrequencyEnum paymentFrequency;

    @Column("compounding_frequency")
    private CompoundingFrequencyEnum compoundingFrequency;

    @Column("day_count_convention")
    private DayCountConventionEnum dayCountConvention;

    @Column("origination_date")
    private LocalDate originationDate;

    @Column("maturity_date")
    private LocalDate maturityDate;

    @Column("remarks")
    private String remarks;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}