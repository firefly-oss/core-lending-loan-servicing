package com.firefly.core.lending.servicing.models.entities.accrual.v1;

import com.firefly.core.lending.servicing.interfaces.enums.accrual.v1.AccrualTypeEnum;
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
@Table("loan_accrual")
public class LoanAccrual {

    @Id
    @Column("loan_accrual_id")
    private UUID loanAccrualId;

    @Column("loan_servicing_case_id")
    private UUID loanServicingCaseId;

    @Column("accrual_amount")
    private BigDecimal accrualAmount;

    @Column("accrual_type")
    private AccrualTypeEnum accrualType;

    @Column("accrual_date")
    private LocalDate accrualDate;

    @Column("note")
    private String note;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}