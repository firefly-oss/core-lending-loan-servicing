-- V6 - RENAME SCHEDULE TO INSTALLMENT AND ADD PAYMENT TRACKING
-- This migration renames repayment schedule to installment plan and adds payment tracking for internal/external payments

-- ========================================================================
-- CREATE ENUMS FOR PAYMENT TRACKING
-- ========================================================================

-- Payment method enum
CREATE TYPE payment_method AS ENUM (
    'INTERNAL',
    'EXTERNAL'
);

-- Payment status enum
CREATE TYPE payment_status AS ENUM (
    'PENDING',
    'PROCESSING',
    'COMPLETED',
    'FAILED',
    'REVERSED'
);

-- ========================================================================
-- RENAME loan_repayment_schedule TO loan_installment_plan
-- ========================================================================

ALTER TABLE loan_repayment_schedule RENAME TO loan_installment_plan;

-- Rename columns
ALTER TABLE loan_installment_plan 
RENAME COLUMN loan_repayment_schedule_id TO loan_installment_plan_id;

-- Add new payment method configuration columns
ALTER TABLE loan_installment_plan
ADD COLUMN payment_method payment_method,
ADD COLUMN payment_account_id UUID,
ADD COLUMN payment_provider_id UUID,
ADD COLUMN external_account_reference VARCHAR(255),
ADD COLUMN is_automatic_payment BOOLEAN DEFAULT FALSE;

-- Add comments for new columns
COMMENT ON COLUMN loan_installment_plan.payment_method IS 'Method configured for payment collection: INTERNAL or EXTERNAL';
COMMENT ON COLUMN loan_installment_plan.payment_account_id IS 'Optional: Customer internal account ID (only for INTERNAL payments)';
COMMENT ON COLUMN loan_installment_plan.payment_provider_id IS 'Optional: Reference to external payment service provider (only for EXTERNAL payments)';
COMMENT ON COLUMN loan_installment_plan.external_account_reference IS 'Optional: Customer external account identifier for EXTERNAL payments (e.g., bank account number)';
COMMENT ON COLUMN loan_installment_plan.is_automatic_payment IS 'Whether this installment is configured for automatic payment (domiciliation)';

-- ========================================================================
-- RENAME loan_repayment_record TO loan_installment_record
-- ========================================================================

ALTER TABLE loan_repayment_record RENAME TO loan_installment_record;

-- Rename columns
ALTER TABLE loan_installment_record 
RENAME COLUMN loan_repayment_record_id TO loan_installment_record_id;

ALTER TABLE loan_installment_record 
RENAME COLUMN loan_repayment_schedule_id TO loan_installment_plan_id;

-- Add new payment tracking columns
ALTER TABLE loan_installment_record
ADD COLUMN payment_method payment_method,
ADD COLUMN payment_status payment_status DEFAULT 'PENDING',
ADD COLUMN payment_provider_id UUID,
ADD COLUMN external_transaction_reference VARCHAR(255);

-- Add comments for new columns
COMMENT ON COLUMN loan_installment_record.payment_method IS 'Method used for this payment: INTERNAL or EXTERNAL';
COMMENT ON COLUMN loan_installment_record.payment_status IS 'Current status of the payment';
COMMENT ON COLUMN loan_installment_record.payment_provider_id IS 'Reference to external payment service provider (PSP) if EXTERNAL';
COMMENT ON COLUMN loan_installment_record.external_transaction_reference IS 'Transaction reference/ID from external PSP';

-- ========================================================================
-- CREATE loan_installment_record_internal_transaction TABLE
-- ========================================================================

CREATE TABLE IF NOT EXISTS loan_installment_record_internal_transaction (
    loan_installment_record_internal_transaction_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_installment_record_id UUID NOT NULL,
    source_account_id UUID NOT NULL,
    destination_account_id UUID NOT NULL,
    transaction_amount DECIMAL(18,2) NOT NULL,
    transaction_reference VARCHAR(255),
    transaction_date TIMESTAMP NOT NULL,
    note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_internal_tx_installment_record
        FOREIGN KEY (loan_installment_record_id)
        REFERENCES loan_installment_record (loan_installment_record_id)
        ON DELETE CASCADE
);

-- Add comments
COMMENT ON TABLE loan_installment_record_internal_transaction IS 'Tracks internal account movements for loan installment payments';
COMMENT ON COLUMN loan_installment_record_internal_transaction.source_account_id IS 'Source account ID (payer account)';
COMMENT ON COLUMN loan_installment_record_internal_transaction.destination_account_id IS 'Destination account ID (loan account)';
COMMENT ON COLUMN loan_installment_record_internal_transaction.transaction_reference IS 'Internal transaction reference number';

-- Create index
CREATE INDEX idx_installment_internal_tx_record_id 
ON loan_installment_record_internal_transaction(loan_installment_record_id);

-- ========================================================================
-- CREATE loan_installment_record_external_transaction TABLE
-- ========================================================================

CREATE TABLE IF NOT EXISTS loan_installment_record_external_transaction (
    loan_installment_record_external_transaction_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_installment_record_id UUID NOT NULL,
    payment_provider_id UUID NOT NULL,
    psp_transaction_id VARCHAR(255),
    psp_transaction_reference VARCHAR(255),
    transaction_amount DECIMAL(18,2) NOT NULL,
    transaction_currency VARCHAR(3) DEFAULT 'USD',
    psp_status VARCHAR(50),
    psp_status_message TEXT,
    payer_account_number VARCHAR(255),
    payer_name VARCHAR(255),
    transaction_date TIMESTAMP NOT NULL,
    psp_response_payload TEXT,
    note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_external_tx_installment_record
        FOREIGN KEY (loan_installment_record_id)
        REFERENCES loan_installment_record (loan_installment_record_id)
        ON DELETE CASCADE
);

-- Add comments
COMMENT ON TABLE loan_installment_record_external_transaction IS 'Tracks external PSP transactions for loan installment payments';
COMMENT ON COLUMN loan_installment_record_external_transaction.payment_provider_id IS 'Reference to payment service provider master data';
COMMENT ON COLUMN loan_installment_record_external_transaction.psp_transaction_id IS 'Transaction ID from the PSP';
COMMENT ON COLUMN loan_installment_record_external_transaction.psp_transaction_reference IS 'Transaction reference from the PSP';
COMMENT ON COLUMN loan_installment_record_external_transaction.psp_status IS 'Status returned by the PSP';
COMMENT ON COLUMN loan_installment_record_external_transaction.psp_response_payload IS 'Full JSON/XML response from PSP for audit trail';
COMMENT ON COLUMN loan_installment_record_external_transaction.payer_account_number IS 'Payer account number (e.g., last 4 digits of card)';
COMMENT ON COLUMN loan_installment_record_external_transaction.payer_name IS 'Name of the payer';

-- Create indexes
CREATE INDEX idx_installment_external_tx_record_id 
ON loan_installment_record_external_transaction(loan_installment_record_id);

CREATE INDEX idx_installment_external_tx_provider_id 
ON loan_installment_record_external_transaction(payment_provider_id);

CREATE INDEX idx_installment_external_tx_psp_transaction_id 
ON loan_installment_record_external_transaction(psp_transaction_id);

