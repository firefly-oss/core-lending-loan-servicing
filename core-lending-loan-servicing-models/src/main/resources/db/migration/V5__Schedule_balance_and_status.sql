-- ========================================================================
-- V5 - REPAYMENT SCHEDULE BALANCE AND STATUS
-- ========================================================================
-- Remaining principal after each installment, and whether the row is the
-- current plan or a superseded plan kept for restructure compensation.
-- ========================================================================

CREATE TYPE schedule_status AS ENUM (
    'CURRENT',
    'SUPERSEDED'
);

CREATE CAST (VARCHAR AS schedule_status) WITH INOUT AS IMPLICIT;
CREATE CAST (schedule_status AS VARCHAR) WITH INOUT AS IMPLICIT;

ALTER TABLE loan_repayment_schedule
    ADD COLUMN remaining_balance DECIMAL(18,2),
    ADD COLUMN schedule_status schedule_status NOT NULL DEFAULT 'CURRENT';

CREATE INDEX idx_loan_repayment_schedule_status
    ON loan_repayment_schedule (loan_servicing_case_id, schedule_status);
