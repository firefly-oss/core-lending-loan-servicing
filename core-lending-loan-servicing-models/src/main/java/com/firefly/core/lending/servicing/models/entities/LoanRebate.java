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

import com.firefly.core.lending.servicing.interfaces.enums.RebateTypeEnum;
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

/**
 * Entity representing rebates applied to a loan.
 * Tracks rebates with or without distributors/brokers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("loan_rebate")
public class LoanRebate {

    @Id
    @Column("loan_rebate_id")
    private UUID loanRebateId;

    @Column("loan_servicing_case_id")
    private UUID loanServicingCaseId;

    @Column("rebate_type")
    private RebateTypeEnum rebateType;

    @Column("rebate_amount")
    private BigDecimal rebateAmount;

    @Column("rebate_date")
    private LocalDate rebateDate;

    @Column("distributor_id")
    private UUID distributorId; // Reference to distributor/broker if applicable

    @Column("distributor_name")
    private String distributorName;

    @Column("distributor_commission")
    private BigDecimal distributorCommission; // Commission paid to distributor

    @Column("is_processed")
    private Boolean isProcessed;

    @Column("processed_date")
    private LocalDate processedDate;

    @Column("description")
    private String description;

    @Column("remarks")
    private String remarks;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}

