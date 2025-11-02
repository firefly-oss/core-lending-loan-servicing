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
 * Enumeration representing the method used to amortize (repay) a loan.
 * 
 * EQUAL_INSTALLMENT - Equal total payment each period (principal + interest)
 * EQUAL_PRINCIPAL - Equal principal payment each period (total payment varies)
 * BALLOON_PAYMENT - Regular payments with large final payment
 * INTEREST_ONLY - Only interest paid during term, principal due at end
 * BULLET - Single payment of principal and interest at maturity
 */
public enum AmortizationMethodEnum {
    /**
     * Equal total payment each period (principal + interest)
     * Most common for consumer loans and mortgages
     * Payment amount stays constant, but principal/interest split changes
     */
    EQUAL_INSTALLMENT,
    
    /**
     * Equal principal payment each period
     * Total payment decreases over time as interest portion decreases
     * Common for commercial loans
     */
    EQUAL_PRINCIPAL,
    
    /**
     * Regular smaller payments with a large final balloon payment
     * Used when borrower expects future cash flow or plans to refinance
     */
    BALLOON_PAYMENT,
    
    /**
     * Only interest is paid during the loan term
     * Full principal amount due at maturity
     * Common for construction loans or bridge financing
     */
    INTEREST_ONLY,
    
    /**
     * Single payment of both principal and interest at maturity
     * No periodic payments during the loan term
     * Common for short-term commercial loans
     */
    BULLET
}

