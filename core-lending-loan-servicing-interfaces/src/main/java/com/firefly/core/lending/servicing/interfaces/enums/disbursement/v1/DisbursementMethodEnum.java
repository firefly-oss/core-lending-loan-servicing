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


package com.firefly.core.lending.servicing.interfaces.enums.disbursement.v1;

/**
 * Enumeration representing the method used for loan disbursement.
 * 
 * INTERNAL - Disbursement through internal account movements within the system
 * EXTERNAL - Disbursement through external payment service providers (PSPs)
 */
public enum DisbursementMethodEnum {
    /**
     * Disbursement processed through internal account movements
     */
    INTERNAL,
    
    /**
     * Disbursement processed through external payment service provider
     */
    EXTERNAL
}

