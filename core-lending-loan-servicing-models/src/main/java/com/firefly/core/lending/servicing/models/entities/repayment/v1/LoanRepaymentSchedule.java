package com.firefly.core.lending.servicing.models.entities.repayment.v1;

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
@Table("loan_repayment_schedule")
public class LoanRepaymentSchedule {

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
}