-- V9 - ADD proposed_offer_id TO loan_servicing_case
-- This adds a reference to the accepted proposed offer from the loan origination module
-- to create a complete audit trail: Application → Proposed Offer → Contract → Servicing Case

-- ========================================================================
-- ADD proposed_offer_id COLUMN
-- ========================================================================
ALTER TABLE loan_servicing_case
ADD COLUMN proposed_offer_id UUID;

-- ========================================================================
-- ADD COMMENT
-- ========================================================================
COMMENT ON COLUMN loan_servicing_case.proposed_offer_id IS 'Reference to the accepted proposed offer from the loan origination module (optional - provides audit trail)';

-- ========================================================================
-- CREATE INDEX
-- ========================================================================
CREATE INDEX IF NOT EXISTS idx_loan_servicing_case_proposed_offer_id 
ON loan_servicing_case(proposed_offer_id);

