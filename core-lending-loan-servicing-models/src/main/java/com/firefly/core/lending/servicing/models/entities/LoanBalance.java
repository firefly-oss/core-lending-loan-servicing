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
 * Entity representing the current balance snapshot for a loan servicing case.
 * This entity tracks the outstanding amounts for principal, interest, and fees.
 * Balance snapshots are created/updated whenever payments are made or accruals occur.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("loan_balance")
public class LoanBalance {

    @Id
    @Column("loan_balance_id")
    private UUID loanBalanceId;

    @Column("loan_servicing_case_id")
    private UUID loanServicingCaseId;

    @Column("principal_outstanding")
    private BigDecimal principalOutstanding;

    @Column("interest_outstanding")
    private BigDecimal interestOutstanding;

    @Column("fees_outstanding")
    private BigDecimal feesOutstanding;

    @Column("total_outstanding")
    private BigDecimal totalOutstanding; // Computed: principal + interest + fees

    @Column("balance_date")
    private LocalDate balanceDate; // Date of this balance snapshot

    @Column("is_current")
    private Boolean isCurrent; // True for the most recent balance, false for historical

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}

