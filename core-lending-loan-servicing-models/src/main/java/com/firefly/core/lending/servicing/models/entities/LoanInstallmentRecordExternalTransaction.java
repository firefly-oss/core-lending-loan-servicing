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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing external payment service provider (PSP) transactions for loan installment payments.
 * Tracks payments processed through external payment providers like Stripe, PayPal, etc.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("loan_installment_record_external_transaction")
public class LoanInstallmentRecordExternalTransaction {

    @Id
    @Column("loan_installment_record_external_transaction_id")
    private UUID loanInstallmentRecordExternalTransactionId;

    @Column("loan_installment_record_id")
    private UUID loanInstallmentRecordId;

    @Column("payment_provider_id")
    private UUID paymentProviderId;

    @Column("psp_transaction_id")
    private String pspTransactionId;

    @Column("psp_transaction_reference")
    private String pspTransactionReference;

    @Column("transaction_amount")
    private BigDecimal transactionAmount;

    @Column("transaction_currency")
    private String transactionCurrency;

    @Column("psp_status")
    private String pspStatus;

    @Column("psp_status_message")
    private String pspStatusMessage;

    @Column("payer_account_number")
    private String payerAccountNumber;

    @Column("payer_name")
    private String payerName;

    @Column("transaction_date")
    private LocalDateTime transactionDate;

    @Column("psp_response_payload")
    private String pspResponsePayload;

    @Column("note")
    private String note;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}

