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


package io.fireflysoftware.core.lending.servicing.core.syndicate;

import io.fireflysoftware.core.lending.servicing.interfaces.enums.FacilityParticipantRole;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Splits a repayment across facility participants by their share of 100.
 * Shares do not have to sum to 100. The remainder stays unallocated.
 * A share above 100 in total is rejected because it would pay out more than was collected.
 */
public final class ParticipantAllocation {

    public static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
    private static final int MONEY_SCALE = 2;
    private static final int SHARE_SCALE = 4;

    private ParticipantAllocation() {
    }

    public record Share(UUID participantId, UUID partyId, FacilityParticipantRole role, BigDecimal sharePercent) {
    }

    public record Line(UUID participantId, UUID partyId, FacilityParticipantRole role, BigDecimal sharePercent,
                       BigDecimal allocatedAmount) {
    }

    public record Plan(BigDecimal paymentAmount, String currency, BigDecimal unallocatedAmount, List<Line> lines) {
    }

    public static Plan allocate(BigDecimal amount, String currency, List<Share> participants) {
        String code = requireCurrency(currency);
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Repayment amount must be positive");
        }
        if (participants == null || participants.isEmpty()) {
            throw new IllegalArgumentException("A facility needs participants before a repayment can be allocated");
        }
        BigDecimal shareTotal = BigDecimal.ZERO;
        int agents = 0;
        for (Share share : participants) {
            requireShare(share.sharePercent());
            if (share.partyId() == null) {
                throw new IllegalArgumentException("Participant party is required");
            }
            if (share.role() == null) {
                throw new IllegalArgumentException("Participant role is required");
            }
            if (share.role() == FacilityParticipantRole.AGENT) {
                agents++;
            }
            shareTotal = shareTotal.add(share.sharePercent());
        }
        if (agents != 1) {
            throw new IllegalArgumentException("A facility has one agent");
        }
        if (shareTotal.compareTo(ONE_HUNDRED) > 0) {
            throw new IllegalArgumentException("Participant shares cannot exceed 100");
        }
        List<Line> lines = new ArrayList<>();
        BigDecimal allocated = BigDecimal.ZERO.setScale(MONEY_SCALE, RoundingMode.UNNECESSARY);
        for (Share share : participants) {
            BigDecimal lineAmount = amount.multiply(share.sharePercent())
                    .divide(ONE_HUNDRED, MONEY_SCALE, RoundingMode.HALF_UP);
            allocated = allocated.add(lineAmount);
            lines.add(new Line(share.participantId(), share.partyId(), share.role(),
                    share.sharePercent().setScale(SHARE_SCALE, RoundingMode.HALF_UP), lineAmount));
        }
        BigDecimal unallocated = amount.setScale(MONEY_SCALE, RoundingMode.HALF_UP).subtract(allocated);
        return new Plan(amount.setScale(MONEY_SCALE, RoundingMode.HALF_UP), code, unallocated, List.copyOf(lines));
    }

    public static BigDecimal requireShare(BigDecimal share) {
        if (share == null) {
            throw new IllegalArgumentException("Participant share is required");
        }
        if (share.compareTo(BigDecimal.ZERO) < 0 || share.compareTo(ONE_HUNDRED) > 0) {
            throw new IllegalArgumentException("Participant share must be from 0 to 100");
        }
        return share.setScale(SHARE_SCALE, RoundingMode.HALF_UP);
    }

    public static String requireCurrency(String currency) {
        if (currency == null || !currency.matches("[A-Z]{3}")) {
            throw new IllegalArgumentException("Currency must be a 3-letter code");
        }
        return currency;
    }
}
