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
 * Enumeration representing the method used to calculate interest on a loan.
 * 
 * SIMPLE - Simple interest calculated on the original principal only
 * COMPOUND - Compound interest where interest is added to principal periodically
 * ACTUARIAL - Actuarial method (normal) - interest calculated on outstanding balance
 * REDUCING_BALANCE - Interest calculated on the reducing principal balance
 * FLAT_RATE - Flat rate interest calculated on original principal for entire term
 */
public enum InterestCalculationMethodEnum {
    /**
     * Simple interest calculated on the original principal only
     * Formula: I = P × r × t
     */
    SIMPLE,
    
    /**
     * Compound interest where interest is added to principal periodically
     * Formula: A = P(1 + r/n)^(nt)
     */
    COMPOUND,
    
    /**
     * Actuarial method (normal) - interest calculated on outstanding balance between payment dates
     * Most common method for installment loans
     */
    ACTUARIAL,
    
    /**
     * Interest calculated on the reducing principal balance after each payment
     * Common for amortizing loans
     */
    REDUCING_BALANCE,
    
    /**
     * Flat rate interest calculated on original principal for entire term
     * Total interest is fixed regardless of payments made
     */
    FLAT_RATE
}

