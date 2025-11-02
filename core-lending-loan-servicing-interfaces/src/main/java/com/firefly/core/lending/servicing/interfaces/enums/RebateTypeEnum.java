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


package com.firefly.core.lending.servicing.interfaces.enums;

/**
 * Enumeration of rebate types for loan servicing.
 * Covers various rebate scenarios in global lending operations.
 */
public enum RebateTypeEnum {
    /**
     * Direct rebate to borrower (cash back, principal reduction)
     */
    BORROWER_REBATE,

    /**
     * Rebate through distributor/broker/agent
     */
    DISTRIBUTOR_REBATE,

    /**
     * Promotional rebate (marketing campaigns, special offers)
     */
    PROMOTIONAL_REBATE,

    /**
     * Early payment rebate (incentive for paying ahead of schedule)
     */
    EARLY_PAYMENT_REBATE,

    /**
     * Loyalty rebate (reward for existing customers)
     */
    LOYALTY_REBATE,

    /**
     * Referral rebate (reward for referring new customers)
     */
    REFERRAL_REBATE,

    /**
     * Government subsidy or grant rebate
     */
    GOVERNMENT_SUBSIDY,

    /**
     * Interest rate buydown rebate
     */
    RATE_BUYDOWN,

    /**
     * Closing cost rebate
     */
    CLOSING_COST_REBATE,

    /**
     * Refinance rebate
     */
    REFINANCE_REBATE,

    /**
     * Volume-based rebate (for high-volume borrowers or partners)
     */
    VOLUME_REBATE,

    /**
     * Other rebate types
     */
    OTHER
}

