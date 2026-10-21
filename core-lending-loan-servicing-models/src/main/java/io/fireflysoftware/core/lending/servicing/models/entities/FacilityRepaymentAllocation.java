/*
 * Copyright 2025 Firefly Software Foundation
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


package io.fireflysoftware.core.lending.servicing.models.entities;

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
 * Record of how one repayment was split across facility participants.
 * {@code paymentId} is the payment accepted by the payments port. This core does not post the ledger.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("facility_repayment_allocation")
public class FacilityRepaymentAllocation {

    @Id
    @Column("allocation_id")
    private UUID allocationId;

    @Column("facility_id")
    private UUID facilityId;

    @Column("loan_repayment_record_id")
    private UUID loanRepaymentRecordId;

    @Column("payment_amount")
    private BigDecimal paymentAmount;

    @Column("unallocated_amount")
    private BigDecimal unallocatedAmount;

    @Column("currency")
    private String currency;

    @Column("payment_id")
    private UUID paymentId;

    @Column("created_at")
    private LocalDateTime createdAt;
}
