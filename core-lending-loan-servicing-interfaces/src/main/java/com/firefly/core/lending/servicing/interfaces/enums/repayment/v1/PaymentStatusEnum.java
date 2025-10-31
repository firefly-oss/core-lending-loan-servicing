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


package com.firefly.core.lending.servicing.interfaces.enums.repayment.v1;

/**
 * Enumeration representing the status of a loan installment payment.
 * 
 * PENDING - Payment has been created but not yet processed
 * PROCESSING - Payment is currently being processed
 * COMPLETED - Payment has been successfully completed
 * FAILED - Payment processing failed
 * REVERSED - Payment has been reversed/cancelled
 */
public enum PaymentStatusEnum {
    /**
     * Payment has been created but not yet processed
     */
    PENDING,
    
    /**
     * Payment is currently being processed
     */
    PROCESSING,
    
    /**
     * Payment has been successfully completed
     */
    COMPLETED,
    
    /**
     * Payment processing failed
     */
    FAILED,
    
    /**
     * Payment has been reversed or cancelled
     */
    REVERSED
}

