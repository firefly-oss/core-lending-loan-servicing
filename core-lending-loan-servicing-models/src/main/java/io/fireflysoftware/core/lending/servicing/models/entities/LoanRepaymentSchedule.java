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


package io.fireflysoftware.core.lending.servicing.models.entities;

import io.fireflysoftware.core.lending.servicing.interfaces.enums.ScheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
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
@Table("loan_repayment_schedule")
public class LoanRepaymentSchedule implements Persistable<UUID> {

    @Id
    @Column("loan_repayment_schedule_id")
    private UUID loanRepaymentScheduleId;

    @Column("loan_servicing_case_id")
    private UUID loanServicingCaseId;

    @Column("installment_number")
    private Integer installmentNumber;

    @Column("due_date")
    private LocalDate dueDate;

    @Column("principal_due")
    private BigDecimal principalDue;

    @Column("interest_due")
    private BigDecimal interestDue;

    @Column("fee_due")
    private BigDecimal feeDue;

    @Column("total_due")
    private BigDecimal totalDue;

    @Column("remaining_balance")
    private BigDecimal remainingBalance;

    @Column("schedule_status")
    private ScheduleStatus scheduleStatus;

    @Column("is_paid")
    private Boolean isPaid;

    @Column("paid_date")
    private LocalDate paidDate;

    @Column("paid_amount")
    private BigDecimal paidAmount;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    /** True only for a row that has not been inserted. A preset id would otherwise be treated as an update. */
    @Transient
    private boolean insert;

    @Override
    public UUID getId() {
        return loanRepaymentScheduleId;
    }

    @Override
    public boolean isNew() {
        return insert;
    }

    public void markNew() {
        if (loanRepaymentScheduleId == null) {
            loanRepaymentScheduleId = UUID.randomUUID();
        }
        insert = true;
    }
}