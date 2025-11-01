-- V8 - ADD product_catalog_id TO loan_servicing_case
-- This adds product catalog reference to loan servicing cases
-- Note: Customer/party information comes from the contract, not stored directly here

-- ========================================================================
-- ADD product_catalog_id COLUMN
-- ========================================================================
ALTER TABLE loan_servicing_case
ADD COLUMN product_catalog_id UUID NOT NULL;

-- ========================================================================
-- ADD COMMENT
-- ========================================================================
COMMENT ON COLUMN loan_servicing_case.product_catalog_id IS 'Reference to the product category/template from the product catalog';

-- ========================================================================
-- CREATE INDEX
-- ========================================================================
CREATE INDEX IF NOT EXISTS idx_loan_servicing_case_product_catalog_id
ON loan_servicing_case(product_catalog_id);

