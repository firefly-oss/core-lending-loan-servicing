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

import com.firefly.core.lending.servicing.interfaces.enums.DisbursementMethodEnum;
import com.firefly.core.lending.servicing.interfaces.enums.DisbursementStatusEnum;
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

    @Column("disbursement_method")
    private DisbursementMethodEnum disbursementMethod;

    @Column("disbursement_status")
    private DisbursementStatusEnum disbursementStatus;

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
