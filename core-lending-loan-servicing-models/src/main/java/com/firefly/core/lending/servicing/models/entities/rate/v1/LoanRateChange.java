package com.firefly.core.lending.servicing.models.entities.rate.v1;

import com.firefly.core.lending.servicing.interfaces.enums.rate.v1.ReasonCodeEnum;
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
@Table("loan_rate_change")
public class LoanRateChange {

    @Id
    @Column("loan_rate_change_id")
    private UUID loanRateChangeId;

    @Column("loan_servicing_case_id")
    private UUID loanServicingCaseId;

    @Column("old_interest_rate")
    private BigDecimal oldInterestRate;

    @Column("new_interest_rate")
    private BigDecimal newInterestRate;

    @Column("effective_date")
    private LocalDate effectiveDate;

    @Column("reason_code")
    private ReasonCodeEnum reasonCode;

    @Column("note")
    private String note;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}