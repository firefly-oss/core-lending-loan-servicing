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
 * Enumeration representing the day count convention used for interest calculations.
 * Day count conventions determine how interest accrues between payment dates.
 * 
 * ACTUAL_360 - Actual days / 360 day year
 * ACTUAL_365 - Actual days / 365 day year
 * ACTUAL_ACTUAL - Actual days / actual days in year
 * THIRTY_360 - 30 days per month / 360 day year
 */
public enum DayCountConventionEnum {
    /**
     * Actual/360 - Actual number of days divided by 360
     * Common for money market instruments and commercial loans
     * Results in slightly higher interest than Actual/365
     */
    ACTUAL_360,
    
    /**
     * Actual/365 - Actual number of days divided by 365
     * Common for corporate bonds and some loans
     * Fixed denominator regardless of leap years
     */
    ACTUAL_365,
    
    /**
     * Actual/Actual - Actual number of days divided by actual days in the year
     * Most accurate method, accounts for leap years
     * Common for government bonds and mortgages
     */
    ACTUAL_ACTUAL,
    
    /**
     * 30/360 - Assumes 30 days in each month and 360 days in a year
     * Simplifies calculations, common for corporate bonds
     * Also known as "Bond Basis"
     */
    THIRTY_360
}

