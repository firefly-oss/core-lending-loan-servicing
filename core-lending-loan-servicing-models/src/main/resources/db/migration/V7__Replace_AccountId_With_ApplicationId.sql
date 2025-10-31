-- V7 - REPLACE account_id WITH application_id IN loan_servicing_case
-- This decouples loan servicing from internal account system
-- Loan servicing should track the loan application that originated it, not the account

-- ========================================================================
-- REMOVE account_id COLUMN
-- ========================================================================
ALTER TABLE loan_servicing_case
DROP COLUMN IF EXISTS account_id;

-- ========================================================================
-- ADD application_id COLUMN
-- ========================================================================
ALTER TABLE loan_servicing_case
ADD COLUMN application_id UUID;

-- ========================================================================
-- ADD COMMENTS
-- ========================================================================
COMMENT ON COLUMN loan_servicing_case.application_id IS 'Reference to the loan application that originated this servicing case (from core-lending-loan-origination)';
COMMENT ON TABLE loan_servicing_case IS 'Loan servicing case tracking. Decoupled from account system - customers may or may not have internal accounts';

-- ========================================================================
-- CREATE INDEX
-- ========================================================================
CREATE INDEX IF NOT EXISTS idx_loan_servicing_case_application_id 
ON loan_servicing_case(application_id);

