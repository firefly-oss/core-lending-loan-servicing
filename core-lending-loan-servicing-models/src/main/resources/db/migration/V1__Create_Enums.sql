-- V1 - CREATE ENUMS FOR LOAN SERVICING

-- loan_servicing_case -> servicing_status
CREATE TYPE servicing_status AS ENUM (
    'ACTIVE',
    'PAID_OFF',
    'CLOSED',
    'DEFAULT',
    'RESTRUCTURED'
);

-- loan_accrual -> accrual_type
CREATE TYPE accrual_type AS ENUM (
    'INTEREST',
    'PENALTY',
    'LATE_FEE',
    'SERVICING_FEE'
);

-- loan_rate_change -> reason_code
CREATE TYPE reason_code AS ENUM (
    'INDEX_ADJUSTMENT',
    'RENEGOTIATION',
    'PENALTY',
    'PROMOTION'
);

-- loan_servicing_event -> event_type
CREATE TYPE event_type AS ENUM (
    'RESTRUCTURE',
    'EXTENSION',
    'DEFERMENT',
    'COLLECTION_CALL',
    'NOTICE'
);