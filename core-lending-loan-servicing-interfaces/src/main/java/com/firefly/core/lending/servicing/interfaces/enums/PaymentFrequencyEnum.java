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
 * Enumeration representing the frequency of loan payments.
 * 
 * DAILY - Payment due every day
 * WEEKLY - Payment due every week
 * BIWEEKLY - Payment due every two weeks (26 payments per year)
 * SEMIMONTHLY - Payment due twice per month (24 payments per year)
 * MONTHLY - Payment due every month (12 payments per year)
 * BIMONTHLY - Payment due every two months (6 payments per year)
 * QUARTERLY - Payment due every quarter (4 payments per year)
 * SEMIANNUALLY - Payment due twice per year (2 payments per year)
 * ANNUALLY - Payment due once per year
 */
public enum PaymentFrequencyEnum {
    /**
     * Payment due every day
     * Rare, typically used for very short-term loans
     */
    DAILY,
    
    /**
     * Payment due every week (52 payments per year)
     */
    WEEKLY,
    
    /**
     * Payment due every two weeks (26 payments per year)
     * Common for payroll-aligned loans
     */
    BIWEEKLY,
    
    /**
     * Payment due twice per month (24 payments per year)
     * Typically on specific dates like 1st and 15th
     */
    SEMIMONTHLY,
    
    /**
     * Payment due every month (12 payments per year)
     * Most common payment frequency
     */
    MONTHLY,
    
    /**
     * Payment due every two months (6 payments per year)
     */
    BIMONTHLY,
    
    /**
     * Payment due every quarter (4 payments per year)
     * Common for business loans
     */
    QUARTERLY,
    
    /**
     * Payment due twice per year (2 payments per year)
     */
    SEMIANNUALLY,
    
    /**
     * Payment due once per year
     * Common for certain agricultural or seasonal loans
     */
    ANNUALLY
}

