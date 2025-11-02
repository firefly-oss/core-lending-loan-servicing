-- ========================================================================
-- V3 - CREATE CASTS FOR LOAN SERVICING
-- ========================================================================
-- This migration creates custom type casts for PostgreSQL enum types.
-- These casts enable seamless conversion between VARCHAR and enum types,
-- which is particularly useful for R2DBC and JDBC drivers.
-- ========================================================================

-- ========================================================================
-- SERVICING STATUS CASTS
-- ========================================================================
CREATE CAST (VARCHAR AS servicing_status) WITH INOUT AS IMPLICIT;
CREATE CAST (servicing_status AS VARCHAR) WITH INOUT AS IMPLICIT;

-- ========================================================================
-- INTEREST CALCULATION METHOD CASTS
-- ========================================================================
CREATE CAST (VARCHAR AS interest_calculation_method) WITH INOUT AS IMPLICIT;
CREATE CAST (interest_calculation_method AS VARCHAR) WITH INOUT AS IMPLICIT;

-- ========================================================================
-- AMORTIZATION METHOD CASTS
-- ========================================================================
CREATE CAST (VARCHAR AS amortization_method) WITH INOUT AS IMPLICIT;
CREATE CAST (amortization_method AS VARCHAR) WITH INOUT AS IMPLICIT;

-- ========================================================================
-- PAYMENT FREQUENCY CASTS
-- ========================================================================
CREATE CAST (VARCHAR AS payment_frequency) WITH INOUT AS IMPLICIT;
CREATE CAST (payment_frequency AS VARCHAR) WITH INOUT AS IMPLICIT;

-- ========================================================================
-- COMPOUNDING FREQUENCY CASTS
-- ========================================================================
CREATE CAST (VARCHAR AS compounding_frequency) WITH INOUT AS IMPLICIT;
CREATE CAST (compounding_frequency AS VARCHAR) WITH INOUT AS IMPLICIT;

-- ========================================================================
-- DAY COUNT CONVENTION CASTS
-- ========================================================================
CREATE CAST (VARCHAR AS day_count_convention) WITH INOUT AS IMPLICIT;
CREATE CAST (day_count_convention AS VARCHAR) WITH INOUT AS IMPLICIT;

-- ========================================================================
-- ACCRUAL TYPE CASTS
-- ========================================================================
CREATE CAST (VARCHAR AS accrual_type) WITH INOUT AS IMPLICIT;
CREATE CAST (accrual_type AS VARCHAR) WITH INOUT AS IMPLICIT;

-- ========================================================================
-- REASON CODE CASTS
-- ========================================================================
CREATE CAST (VARCHAR AS reason_code) WITH INOUT AS IMPLICIT;
CREATE CAST (reason_code AS VARCHAR) WITH INOUT AS IMPLICIT;

-- ========================================================================
-- EVENT TYPE CASTS
-- ========================================================================
CREATE CAST (VARCHAR AS event_type) WITH INOUT AS IMPLICIT;
CREATE CAST (event_type AS VARCHAR) WITH INOUT AS IMPLICIT;

-- ========================================================================
-- PAYMENT METHOD CASTS
-- ========================================================================
CREATE CAST (VARCHAR AS payment_method) WITH INOUT AS IMPLICIT;
CREATE CAST (payment_method AS VARCHAR) WITH INOUT AS IMPLICIT;

-- ========================================================================
-- PAYMENT STATUS CASTS
-- ========================================================================
CREATE CAST (VARCHAR AS payment_status) WITH INOUT AS IMPLICIT;
CREATE CAST (payment_status AS VARCHAR) WITH INOUT AS IMPLICIT;

-- ========================================================================
-- DISBURSEMENT METHOD CASTS
-- ========================================================================
CREATE CAST (VARCHAR AS disbursement_method) WITH INOUT AS IMPLICIT;
CREATE CAST (disbursement_method AS VARCHAR) WITH INOUT AS IMPLICIT;

-- ========================================================================
-- DISBURSEMENT STATUS CASTS
-- ========================================================================
CREATE CAST (VARCHAR AS disbursement_status) WITH INOUT AS IMPLICIT;
CREATE CAST (disbursement_status AS VARCHAR) WITH INOUT AS IMPLICIT;

-- ========================================================================
-- REBATE TYPE CASTS
-- ========================================================================
CREATE CAST (VARCHAR AS rebate_type) WITH INOUT AS IMPLICIT;
CREATE CAST (rebate_type AS VARCHAR) WITH INOUT AS IMPLICIT;

-- ========================================================================
-- ESCROW TYPE CASTS
-- ========================================================================
CREATE CAST (VARCHAR AS escrow_type) WITH INOUT AS IMPLICIT;
CREATE CAST (escrow_type AS VARCHAR) WITH INOUT AS IMPLICIT;

-- ========================================================================
-- NOTIFICATION TYPE CASTS
-- ========================================================================
CREATE CAST (VARCHAR AS notification_type) WITH INOUT AS IMPLICIT;
CREATE CAST (notification_type AS VARCHAR) WITH INOUT AS IMPLICIT;

-- ========================================================================
-- NOTIFICATION CHANNEL CASTS
-- ========================================================================
CREATE CAST (VARCHAR AS notification_channel) WITH INOUT AS IMPLICIT;
CREATE CAST (notification_channel AS VARCHAR) WITH INOUT AS IMPLICIT;

-- ========================================================================
-- NOTIFICATION STATUS CASTS
-- ========================================================================
CREATE CAST (VARCHAR AS notification_status) WITH INOUT AS IMPLICIT;
CREATE CAST (notification_status AS VARCHAR) WITH INOUT AS IMPLICIT;

