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

import io.fireflysoftware.core.lending.servicing.interfaces.enums.FacilityParticipantRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("facility_allocation_line")
public class FacilityAllocationLine {

    @Id
    @Column("line_id")
    private UUID lineId;

    @Column("allocation_id")
    private UUID allocationId;

    @Column("participant_id")
    private UUID participantId;

    @Column("party_id")
    private UUID partyId;

    @Column("role")
    private FacilityParticipantRole role;

    @Column("share_percent")
    private BigDecimal sharePercent;

    @Column("allocated_amount")
    private BigDecimal allocatedAmount;

    @Column("currency")
    private String currency;
}
