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
 * Enumeration representing the status of a loan disbursement.
 * 
 * PENDING - Disbursement has been created but not yet processed
 * PROCESSING - Disbursement is currently being processed
 * COMPLETED - Disbursement has been successfully completed
 * FAILED - Disbursement processing failed
 * REVERSED - Disbursement has been reversed/cancelled
 */
public enum DisbursementStatusEnum {
    /**
     * Disbursement has been created but not yet processed
     */
    PENDING,
    
    /**
     * Disbursement is currently being processed
     */
    PROCESSING,
    
    /**
     * Disbursement has been successfully completed
     */
    COMPLETED,
    
    /**
     * Disbursement processing failed
     */
    FAILED,
    
    /**
     * Disbursement has been reversed or cancelled
     */
    REVERSED
}

