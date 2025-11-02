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
 * Enumeration of notification types for loan servicing events.
 * Defines the various types of notifications that can be sent to loan parties.
 * Covers comprehensive loan lifecycle events for global lending operations.
 */
public enum NotificationTypeEnum {
    // Payment-related notifications
    /**
     * Payment due reminder notification (upcoming payment)
     */
    PAYMENT_DUE_REMINDER,

    /**
     * Payment received confirmation
     */
    PAYMENT_RECEIVED,

    /**
     * Payment overdue notice (past due)
     */
    PAYMENT_OVERDUE,

    /**
     * Payment failed notification (ACH return, card decline, etc.)
     */
    PAYMENT_FAILED,

    /**
     * Payment reversal notification
     */
    PAYMENT_REVERSED,

    /**
     * Partial payment received notification
     */
    PARTIAL_PAYMENT_RECEIVED,

    // Disbursement-related notifications
    /**
     * Loan disbursement completed notification
     */
    DISBURSEMENT_COMPLETED,

    /**
     * Disbursement scheduled notification
     */
    DISBURSEMENT_SCHEDULED,

    /**
     * Disbursement failed notification
     */
    DISBURSEMENT_FAILED,

    // Rate and terms notifications
    /**
     * Interest rate change notification
     */
    RATE_CHANGE,

    /**
     * Loan restructuring notification
     */
    LOAN_RESTRUCTURED,

    /**
     * Terms modification notification
     */
    TERMS_MODIFIED,

    // Maturity and payoff notifications
    /**
     * Loan maturity approaching notification
     */
    MATURITY_APPROACHING,

    /**
     * Loan paid off notification
     */
    LOAN_PAID_OFF,

    /**
     * Payoff quote generated notification
     */
    PAYOFF_QUOTE_GENERATED,

    // Delinquency and collections notifications
    /**
     * Grace period notification
     */
    GRACE_PERIOD_NOTICE,

    /**
     * Delinquency notice
     */
    DELINQUENCY_NOTICE,

    /**
     * Default notice
     */
    DEFAULT_NOTICE,

    /**
     * Collection notice
     */
    COLLECTION_NOTICE,

    /**
     * Charge-off notification
     */
    CHARGE_OFF_NOTICE,

    /**
     * Foreclosure notice
     */
    FORECLOSURE_NOTICE,

    /**
     * Bankruptcy notification
     */
    BANKRUPTCY_NOTICE,

    // Statement and reporting notifications
    /**
     * Monthly statement generated notification
     */
    STATEMENT_GENERATED,

    /**
     * Annual statement notification (tax reporting)
     */
    ANNUAL_STATEMENT,

    /**
     * Year-end tax statement (1098, etc.)
     */
    TAX_STATEMENT,

    // Escrow notifications
    /**
     * Escrow payment notification
     */
    ESCROW_PAYMENT,

    /**
     * Escrow analysis notification
     */
    ESCROW_ANALYSIS,

    /**
     * Escrow shortage notification
     */
    ESCROW_SHORTAGE,

    /**
     * Escrow surplus notification
     */
    ESCROW_SURPLUS,

    // Rebate and incentive notifications
    /**
     * Rebate processed notification
     */
    REBATE_PROCESSED,

    /**
     * Rebate approved notification
     */
    REBATE_APPROVED,

    /**
     * Incentive earned notification
     */
    INCENTIVE_EARNED,

    // Account and servicing notifications
    /**
     * Account activation notification
     */
    ACCOUNT_ACTIVATED,

    /**
     * Account suspended notification
     */
    ACCOUNT_SUSPENDED,

    /**
     * Account closed notification
     */
    ACCOUNT_CLOSED,

    /**
     * Servicing transfer notification (loan sold/transferred)
     */
    SERVICING_TRANSFER,

    /**
     * Document required notification
     */
    DOCUMENT_REQUIRED,

    /**
     * Document received notification
     */
    DOCUMENT_RECEIVED,

    /**
     * Forbearance approved notification
     */
    FORBEARANCE_APPROVED,

    /**
     * Forbearance ended notification
     */
    FORBEARANCE_ENDED,

    /**
     * General information notification
     */
    GENERAL_INFORMATION,

    /**
     * Welcome notification (loan activation)
     */
    WELCOME,

    /**
     * Security alert notification
     */
    SECURITY_ALERT
}

