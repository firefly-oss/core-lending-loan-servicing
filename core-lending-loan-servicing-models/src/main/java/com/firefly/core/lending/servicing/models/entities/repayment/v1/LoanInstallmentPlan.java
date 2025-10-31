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


package com.firefly.core.lending.servicing.models.entities.repayment.v1;

import com.firefly.core.lending.servicing.interfaces.enums.repayment.v1.PaymentMethodEnum;
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
@Table("loan_installment_plan")
public class LoanInstallmentPlan {

    @Id
    @Column("loan_installment_plan_id")
    private UUID loanInstallmentPlanId;

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

    // Payment method configuration fields
    @Column("payment_method")
    private PaymentMethodEnum paymentMethod;

    @Column("payment_account_id")
    private UUID paymentAccountId; // Optional: Only for INTERNAL payments (customer has internal account)

    @Column("payment_provider_id")
    private UUID paymentProviderId; // Optional: Only for EXTERNAL payments (PSP reference)

    @Column("external_account_reference")
    private String externalAccountReference; // Optional: For EXTERNAL - customer's external account identifier

    @Column("is_automatic_payment")
    private Boolean isAutomaticPayment;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}

