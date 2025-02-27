package com.catalis.core.lending.servicing.models.entities.repayment.v1;

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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("loan_repayment_record")
public class LoanRepaymentRecord {

    @Id
    @Column("loan_repayment_record_id")
    private Long loanRepaymentRecordId;

    @Column("loan_servicing_case_id")
    private Long loanServicingCaseId;

    @Column("loan_repayment_schedule_id")
    private Long loanRepaymentScheduleId;

    @Column("transaction_id")
    private Long transactionId;

    @Column("payment_amount")
    private BigDecimal paymentAmount;

    @Column("payment_date")
    private LocalDate paymentDate;

    @Column("is_partial_payment")
    private Boolean isPartialPayment;

    @Column("note")
    private String note;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}