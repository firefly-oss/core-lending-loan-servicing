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


package com.firefly.core.lending.servicing.models.entities.rate.v1;

import com.firefly.core.lending.servicing.interfaces.enums.ReasonCodeEnum;
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