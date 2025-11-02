-- ========================================================================
-- V1 - CREATE ENUMS FOR LOAN SERVICING
-- ========================================================================
-- This migration creates all PostgreSQL enum types used in the loan servicing module.
-- These enums match exactly with the Java enum classes in the interfaces module.
-- ========================================================================

-- Enable UUID extension if not already enabled
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- ========================================================================
-- SERVICING STATUS ENUM
-- ========================================================================
-- Used by: loan_servicing_case.servicing_status
-- Java Enum: ServicingStatusEnum
CREATE TYPE servicing_status AS ENUM (
    'PENDING',
    'ACTIVE',
    'GRACE_PERIOD',
    'DELINQUENT',
    'DEFAULT',
    'FORBEARANCE',
    'RESTRUCTURED',
    'BANKRUPTCY',
    'FORECLOSURE',
    'CHARGED_OFF',
    'PAID_OFF',
    'CLOSED',
    'TRANSFERRED',
    'CANCELLED'
);

-- ========================================================================
-- INTEREST CALCULATION METHOD ENUM
-- ========================================================================
-- Used by: loan_servicing_case.interest_calculation_method, loan_restructuring
-- Java Enum: InterestCalculationMethodEnum
CREATE TYPE interest_calculation_method AS ENUM (
    'SIMPLE',
    'COMPOUND',
    'ACTUARIAL',
    'REDUCING_BALANCE',
    'FLAT_RATE'
);

-- ========================================================================
-- AMORTIZATION METHOD ENUM
-- ========================================================================
-- Used by: loan_servicing_case.amortization_method, loan_restructuring
-- Java Enum: AmortizationMethodEnum
CREATE TYPE amortization_method AS ENUM (
    'EQUAL_INSTALLMENT',
    'EQUAL_PRINCIPAL',
    'BALLOON_PAYMENT',
    'INTEREST_ONLY',
    'BULLET'
);

-- ========================================================================
-- PAYMENT FREQUENCY ENUM
-- ========================================================================
-- Used by: loan_servicing_case.payment_frequency, loan_restructuring
-- Java Enum: PaymentFrequencyEnum
CREATE TYPE payment_frequency AS ENUM (
    'DAILY',
    'WEEKLY',
    'BIWEEKLY',
    'SEMIMONTHLY',
    'MONTHLY',
    'BIMONTHLY',
    'QUARTERLY',
    'SEMIANNUALLY',
    'ANNUALLY'
);

-- ========================================================================
-- COMPOUNDING FREQUENCY ENUM
-- ========================================================================
-- Used by: loan_servicing_case.compounding_frequency, loan_restructuring
-- Java Enum: CompoundingFrequencyEnum
CREATE TYPE compounding_frequency AS ENUM (
    'DAILY',
    'MONTHLY',
    'QUARTERLY',
    'SEMIANNUALLY',
    'ANNUALLY',
    'CONTINUOUS'
);

-- ========================================================================
-- DAY COUNT CONVENTION ENUM
-- ========================================================================
-- Used by: loan_servicing_case.day_count_convention, loan_restructuring
-- Java Enum: DayCountConventionEnum
CREATE TYPE day_count_convention AS ENUM (
    'ACTUAL_360',
    'ACTUAL_365',
    'ACTUAL_ACTUAL',
    'THIRTY_360'
);

-- ========================================================================
-- ACCRUAL TYPE ENUM
-- ========================================================================
-- Used by: loan_accrual.accrual_type
-- Java Enum: AccrualTypeEnum
CREATE TYPE accrual_type AS ENUM (
    'INTEREST',
    'PENALTY',
    'LATE_FEE',
    'SERVICING_FEE'
);

-- ========================================================================
-- REASON CODE ENUM
-- ========================================================================
-- Used by: loan_rate_change.reason_code
-- Java Enum: ReasonCodeEnum
CREATE TYPE reason_code AS ENUM (
    'INDEX_ADJUSTMENT',
    'RENEGOTIATION',
    'PENALTY',
    'PROMOTION'
);

-- ========================================================================
-- EVENT TYPE ENUM
-- ========================================================================
-- Used by: loan_servicing_event.event_type
-- Java Enum: EventTypeEnum
CREATE TYPE event_type AS ENUM (
    'RESTRUCTURE',
    'EXTENSION',
    'DEFERMENT',
    'COLLECTION_CALL',
    'NOTICE'
);

-- ========================================================================
-- PAYMENT METHOD ENUM
-- ========================================================================
-- Used by: loan_installment_plan.payment_method, loan_installment_record.payment_method
-- Java Enum: PaymentMethodEnum
CREATE TYPE payment_method AS ENUM (
    'INTERNAL',
    'EXTERNAL'
);

-- ========================================================================
-- PAYMENT STATUS ENUM
-- ========================================================================
-- Used by: loan_installment_record.payment_status
-- Java Enum: PaymentStatusEnum
CREATE TYPE payment_status AS ENUM (
    'PENDING',
    'PROCESSING',
    'COMPLETED',
    'FAILED',
    'REVERSED'
);

-- ========================================================================
-- DISBURSEMENT METHOD ENUM
-- ========================================================================
-- Used by: loan_disbursement.disbursement_method
-- Java Enum: DisbursementMethodEnum
CREATE TYPE disbursement_method AS ENUM (
    'INTERNAL',
    'EXTERNAL'
);

-- ========================================================================
-- DISBURSEMENT STATUS ENUM
-- ========================================================================
-- Used by: loan_disbursement.disbursement_status
-- Java Enum: DisbursementStatusEnum
CREATE TYPE disbursement_status AS ENUM (
    'PENDING',
    'PROCESSING',
    'COMPLETED',
    'FAILED',
    'REVERSED'
);

-- ========================================================================
-- REBATE TYPE ENUM
-- ========================================================================
-- Used by: loan_rebate.rebate_type
-- Java Enum: RebateTypeEnum
CREATE TYPE rebate_type AS ENUM (
    'BORROWER_REBATE',
    'DISTRIBUTOR_REBATE',
    'PROMOTIONAL_REBATE',
    'EARLY_PAYMENT_REBATE',
    'LOYALTY_REBATE',
    'REFERRAL_REBATE',
    'GOVERNMENT_SUBSIDY',
    'RATE_BUYDOWN',
    'CLOSING_COST_REBATE',
    'REFINANCE_REBATE',
    'VOLUME_REBATE',
    'OTHER'
);

-- ========================================================================
-- ESCROW TYPE ENUM
-- ========================================================================
-- Used by: loan_escrow.escrow_type
-- Java Enum: EscrowTypeEnum
CREATE TYPE escrow_type AS ENUM (
    -- Insurance Escrows
    'INSURANCE',
    'COLLATERAL_INSURANCE',
    'CREDIT_INSURANCE',
    'LIABILITY_INSURANCE',
    -- Tax Escrows
    'TAX',
    'PROPERTY_TAX',
    'BUSINESS_TAX',
    'IMPORT_EXPORT_DUTY',
    -- Maintenance & Operating Escrows
    'MAINTENANCE_RESERVE',
    'REPAIR_RESERVE',
    'OPERATING_EXPENSE',
    'CAPEX_RESERVE',
    -- Association & Fee Escrows
    'ASSOCIATION_FEES',
    'LEASE_PAYMENT',
    'LICENSE_PERMIT_FEES',
    -- Trade Finance Escrows
    'FREIGHT_SHIPPING',
    'WAREHOUSING',
    'LC_FEES',
    -- Asset-Based Lending Escrows
    'INVENTORY_MONITORING',
    'APPRAISAL_VALUATION',
    'ENVIRONMENTAL_COMPLIANCE',
    -- Construction & Development Escrows
    'CONSTRUCTION_HOLDBACK',
    'PERFORMANCE_BOND',
    'RETAINAGE',
    -- Legal & Compliance Escrows
    'LEGAL_PROFESSIONAL_FEES',
    'REGULATORY_COMPLIANCE',
    'LITIGATION_RESERVE',
    -- Debt Service & Payment Escrows
    'DEBT_SERVICE_RESERVE',
    'INTEREST_RESERVE',
    'PRINCIPAL_RESERVE',
    -- Other Escrows
    'SECURITY_DEPOSIT',
    'CONTINGENCY_RESERVE',
    'THIRD_PARTY_FEES',
    'COMBINED',
    'OTHER'
);

-- ========================================================================
-- NOTIFICATION TYPE ENUM
-- ========================================================================
-- Used by: loan_notification.notification_type
-- Java Enum: NotificationTypeEnum
CREATE TYPE notification_type AS ENUM (
    -- Payment-related notifications
    'PAYMENT_DUE_REMINDER',
    'PAYMENT_RECEIVED',
    'PAYMENT_OVERDUE',
    'PAYMENT_FAILED',
    'PAYMENT_REVERSED',
    'PARTIAL_PAYMENT_RECEIVED',
    -- Disbursement-related notifications
    'DISBURSEMENT_COMPLETED',
    'DISBURSEMENT_SCHEDULED',
    'DISBURSEMENT_FAILED',
    -- Rate and terms notifications
    'RATE_CHANGE',
    'LOAN_RESTRUCTURED',
    'TERMS_MODIFIED',
    -- Maturity and payoff notifications
    'MATURITY_APPROACHING',
    'LOAN_PAID_OFF',
    'PAYOFF_QUOTE_GENERATED',
    -- Delinquency and collections notifications
    'GRACE_PERIOD_NOTICE',
    'DELINQUENCY_NOTICE',
    'DEFAULT_NOTICE',
    'COLLECTION_NOTICE',
    'CHARGE_OFF_NOTICE',
    'FORECLOSURE_NOTICE',
    'BANKRUPTCY_NOTICE',
    -- Statement and reporting notifications
    'STATEMENT_GENERATED',
    'ANNUAL_STATEMENT',
    'TAX_STATEMENT',
    -- Escrow notifications
    'ESCROW_PAYMENT',
    'ESCROW_ANALYSIS',
    'ESCROW_SHORTAGE',
    'ESCROW_SURPLUS',
    -- Rebate and incentive notifications
    'REBATE_PROCESSED',
    'REBATE_APPROVED',
    'INCENTIVE_EARNED',
    -- Account and servicing notifications
    'ACCOUNT_ACTIVATED',
    'ACCOUNT_SUSPENDED',
    'ACCOUNT_CLOSED',
    'SERVICING_TRANSFER',
    'DOCUMENT_REQUIRED',
    'DOCUMENT_RECEIVED',
    'FORBEARANCE_APPROVED',
    'FORBEARANCE_ENDED',
    'GENERAL_INFORMATION',
    'WELCOME',
    'SECURITY_ALERT'
);

-- ========================================================================
-- NOTIFICATION CHANNEL ENUM
-- ========================================================================
-- Used by: loan_notification.notification_channel
-- Java Enum: NotificationChannelEnum
CREATE TYPE notification_channel AS ENUM (
    'EMAIL',
    'SMS',
    'PUSH',
    'IN_APP',
    'MAIL',
    'PHONE'
);

-- ========================================================================
-- NOTIFICATION STATUS ENUM
-- ========================================================================
-- Used by: loan_notification.notification_status
-- Java Enum: NotificationStatusEnum
CREATE TYPE notification_status AS ENUM (
    'PENDING',
    'SENT',
    'DELIVERED',
    'FAILED',
    'READ'
);