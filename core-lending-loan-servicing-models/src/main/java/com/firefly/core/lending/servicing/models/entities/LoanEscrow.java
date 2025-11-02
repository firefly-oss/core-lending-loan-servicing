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

import com.firefly.core.lending.servicing.interfaces.enums.EscrowTypeEnum;
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
 * Entity representing escrow accounts for loan servicing.
 * Tracks escrow for taxes, insurance, HOA fees, etc.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("loan_escrow")
public class LoanEscrow {

    @Id
    @Column("loan_escrow_id")
    private UUID loanEscrowId;

    @Column("loan_servicing_case_id")
    private UUID loanServicingCaseId;

    @Column("escrow_type")
    private EscrowTypeEnum escrowType;

    @Column("monthly_payment_amount")
    private BigDecimal monthlyPaymentAmount; // Monthly escrow payment amount

    @Column("current_balance")
    private BigDecimal currentBalance; // Current escrow account balance

    @Column("target_balance")
    private BigDecimal targetBalance; // Target/required escrow balance

    @Column("annual_disbursement_amount")
    private BigDecimal annualDisbursementAmount; // Expected annual disbursement (e.g., annual tax bill)

    @Column("next_disbursement_date")
    private LocalDate nextDisbursementDate; // When next payment is due (e.g., tax due date)

    @Column("last_analysis_date")
    private LocalDate lastAnalysisDate; // Last escrow analysis date

    @Column("next_analysis_date")
    private LocalDate nextAnalysisDate; // Next scheduled escrow analysis

    @Column("is_active")
    private Boolean isActive;

    @Column("payee_name")
    private String payeeName; // Who receives the escrow payments (e.g., tax authority, insurance company)

    @Column("payee_account_number")
    private String payeeAccountNumber;

    @Column("remarks")
    private String remarks;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}

