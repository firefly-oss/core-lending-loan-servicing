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
 * Entity representing a planned disbursement schedule for a loan.
 * Tracks when and how much will be disbursed to the borrower.
 * Useful for construction loans, line of credit draws, or staged disbursements.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("loan_disbursement_plan")
public class LoanDisbursementPlan {

    @Id
    @Column("loan_disbursement_plan_id")
    private UUID loanDisbursementPlanId;

    @Column("loan_servicing_case_id")
    private UUID loanServicingCaseId;

    @Column("planned_disbursement_date")
    private LocalDate plannedDisbursementDate;

    @Column("planned_amount")
    private BigDecimal plannedAmount;

    @Column("actual_disbursement_date")
    private LocalDate actualDisbursementDate;

    @Column("actual_amount")
    private BigDecimal actualAmount;

    @Column("disbursement_number")
    private Integer disbursementNumber; // Sequence number (1st, 2nd, 3rd disbursement)

    @Column("is_completed")
    private Boolean isCompleted;

    @Column("purpose")
    private String purpose; // Purpose of this disbursement (e.g., "Foundation work", "Framing", etc.)

    @Column("remarks")
    private String remarks;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}

