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
 * Enumeration representing the frequency at which interest is compounded.
 * 
 * DAILY - Interest compounds every day
 * MONTHLY - Interest compounds every month
 * QUARTERLY - Interest compounds every quarter
 * SEMIANNUALLY - Interest compounds twice per year
 * ANNUALLY - Interest compounds once per year
 * CONTINUOUS - Interest compounds continuously (mathematical limit)
 */
public enum CompoundingFrequencyEnum {
    /**
     * Interest compounds every day (365 or 360 times per year)
     * Results in highest effective interest rate
     */
    DAILY,
    
    /**
     * Interest compounds every month (12 times per year)
     * Most common compounding frequency
     */
    MONTHLY,
    
    /**
     * Interest compounds every quarter (4 times per year)
     */
    QUARTERLY,
    
    /**
     * Interest compounds twice per year (2 times per year)
     */
    SEMIANNUALLY,
    
    /**
     * Interest compounds once per year
     * Simplest compounding method
     */
    ANNUALLY,
    
    /**
     * Interest compounds continuously (mathematical limit as n approaches infinity)
     * Used in some financial calculations and theoretical models
     */
    CONTINUOUS
}

