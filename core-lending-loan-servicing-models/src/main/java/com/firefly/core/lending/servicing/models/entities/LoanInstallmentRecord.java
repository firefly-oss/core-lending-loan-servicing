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

import com.firefly.core.lending.servicing.interfaces.enums.PaymentMethodEnum;
import com.firefly.core.lending.servicing.interfaces.enums.PaymentStatusEnum;
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
@Table("loan_installment_record")
public class LoanInstallmentRecord {

    @Id
    @Column("loan_installment_record_id")
    private UUID loanInstallmentRecordId;

    @Column("loan_servicing_case_id")
    private UUID loanServicingCaseId;

    @Column("loan_installment_plan_id")
    private UUID loanInstallmentPlanId;

    @Column("transaction_id")
    private UUID transactionId;

    @Column("payment_amount")
    private BigDecimal paymentAmount;

    @Column("payment_date")
    private LocalDate paymentDate;

    @Column("is_partial_payment")
    private Boolean isPartialPayment;

    // Payment tracking fields
    @Column("payment_method")
    private PaymentMethodEnum paymentMethod;

    @Column("payment_status")
    private PaymentStatusEnum paymentStatus;

    @Column("payment_provider_id")
    private UUID paymentProviderId;

    @Column("external_transaction_reference")
    private String externalTransactionReference;

    @Column("note")
    private String note;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}

