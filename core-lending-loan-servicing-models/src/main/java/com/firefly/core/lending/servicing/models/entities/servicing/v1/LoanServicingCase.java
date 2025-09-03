package com.firefly.core.lending.servicing.models.entities.servicing.v1;

import com.firefly.core.lending.servicing.interfaces.enums.servicing.v1.ServicingStatusEnum;
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

    @Column("account_id")
    private UUID accountId;

    @Column("servicing_status")
    private ServicingStatusEnum servicingStatus;

    @Column("principal_outstanding")
    private BigDecimal principalOutstanding;

    @Column("interest_outstanding")
    private BigDecimal interestOutstanding;

    @Column("fees_outstanding")
    private BigDecimal feesOutstanding;

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