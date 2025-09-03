package com.firefly.core.lending.servicing.models.entities.disbursement.v1;

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
@Table("loan_disbursement")
public class LoanDisbursement {

    @Id
    @Column("loan_disbursement_id")
    private UUID loanDisbursementId;

    @Column("loan_servicing_case_id")
    private UUID loanServicingCaseId;

    @Column("transaction_id")
    private UUID transactionId;

    @Column("disbursement_amount")
    private BigDecimal disbursementAmount;

    @Column("disbursement_date")
    private LocalDate disbursementDate;

    @Column("is_final_disbursement")
    private Boolean isFinalDisbursement;

    @Column("note")
    private String note;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
