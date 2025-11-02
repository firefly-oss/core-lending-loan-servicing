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
 * Enumeration of loan servicing statuses.
 * Represents the current state of a loan throughout its lifecycle.
 */
public enum ServicingStatusEnum {
    /**
     * Loan is pending activation (disbursement not yet completed)
     */
    PENDING,

    /**
     * Loan is active and in good standing
     */
    ACTIVE,

    /**
     * Loan is in grace period (payment overdue but within grace period)
     */
    GRACE_PERIOD,

    /**
     * Loan is delinquent (payment overdue beyond grace period)
     */
    DELINQUENT,

    /**
     * Loan is in default
     */
    DEFAULT,

    /**
     * Loan is in forbearance (temporary payment suspension)
     */
    FORBEARANCE,

    /**
     * Loan has been restructured
     */
    RESTRUCTURED,

    /**
     * Loan is in bankruptcy proceedings
     */
    BANKRUPTCY,

    /**
     * Loan is in foreclosure process
     */
    FORECLOSURE,

    /**
     * Loan has been charged off
     */
    CHARGED_OFF,

    /**
     * Loan has been paid off in full
     */
    PAID_OFF,

    /**
     * Loan has been closed
     */
    CLOSED,

    /**
     * Loan has been sold/transferred to another servicer
     */
    TRANSFERRED,

    /**
     * Loan has been cancelled
     */
    CANCELLED
}