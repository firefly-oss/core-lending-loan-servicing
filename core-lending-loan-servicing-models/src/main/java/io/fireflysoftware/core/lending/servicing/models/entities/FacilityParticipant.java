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
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * A lender on a syndicated facility. {@code partyId} is the lender.
 * The row does not point at a second loan master.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("facility_participant")
public class FacilityParticipant {

    @Id
    @Column("participant_id")
    private UUID participantId;

    @Column("facility_id")
    private UUID facilityId;

    @Column("party_id")
    private UUID partyId;

    @Column("share_percent")
    private BigDecimal sharePercent;

    @Column("role")
    private FacilityParticipantRole role;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
