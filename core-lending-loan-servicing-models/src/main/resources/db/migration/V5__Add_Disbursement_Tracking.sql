-- V5 - ADD DISBURSEMENT TRACKING ENHANCEMENTS
-- This migration adds support for tracking internal and external disbursement transactions

-- ========================================================================
-- CREATE ENUMS FOR DISBURSEMENT TRACKING
-- ========================================================================

-- Disbursement method enum
CREATE TYPE disbursement_method AS ENUM (
    'INTERNAL',
    'EXTERNAL'
);

-- Disbursement status enum
CREATE TYPE disbursement_status AS ENUM (
    'PENDING',
    'PROCESSING',
    'COMPLETED',
    'FAILED',
    'REVERSED'
);

-- ========================================================================
-- ALTER loan_disbursement TABLE
-- ========================================================================

-- Add new columns to loan_disbursement table
ALTER TABLE loan_disbursement
ADD COLUMN disbursement_method disbursement_method,
ADD COLUMN disbursement_status disbursement_status DEFAULT 'PENDING',
ADD COLUMN payment_provider_id UUID,
ADD COLUMN external_transaction_reference VARCHAR(255);

-- Add comments for new columns
COMMENT ON COLUMN loan_disbursement.disbursement_method IS 'Method used for disbursement: INTERNAL or EXTERNAL';
COMMENT ON COLUMN loan_disbursement.disbursement_status IS 'Current status of the disbursement';
COMMENT ON COLUMN loan_disbursement.payment_provider_id IS 'Reference to external payment service provider (PSP) master data';
COMMENT ON COLUMN loan_disbursement.external_transaction_reference IS 'Transaction reference/ID from external PSP';

-- ========================================================================
-- CREATE loan_disbursement_internal_transaction TABLE
-- ========================================================================

CREATE TABLE IF NOT EXISTS loan_disbursement_internal_transaction (
    loan_disbursement_internal_transaction_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_disbursement_id UUID NOT NULL,
    source_account_id UUID NOT NULL,
    destination_account_id UUID NOT NULL,
    transaction_amount DECIMAL(18,2) NOT NULL,
    transaction_reference VARCHAR(255),
    transaction_date TIMESTAMP NOT NULL,
    note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_internal_tx_disbursement
        FOREIGN KEY (loan_disbursement_id)
        REFERENCES loan_disbursement (loan_disbursement_id)
        ON DELETE CASCADE
);

-- Add comments
COMMENT ON TABLE loan_disbursement_internal_transaction IS 'Tracks internal account movements for loan disbursements';
COMMENT ON COLUMN loan_disbursement_internal_transaction.source_account_id IS 'Source account for the internal transfer';
COMMENT ON COLUMN loan_disbursement_internal_transaction.destination_account_id IS 'Destination account for the internal transfer';
COMMENT ON COLUMN loan_disbursement_internal_transaction.transaction_reference IS 'Internal transaction reference number';

-- Create indexes
CREATE INDEX idx_internal_tx_disbursement_id ON loan_disbursement_internal_transaction(loan_disbursement_id);
CREATE INDEX idx_internal_tx_source_account ON loan_disbursement_internal_transaction(source_account_id);
CREATE INDEX idx_internal_tx_destination_account ON loan_disbursement_internal_transaction(destination_account_id);
CREATE INDEX idx_internal_tx_date ON loan_disbursement_internal_transaction(transaction_date);

-- ========================================================================
-- CREATE loan_disbursement_external_transaction TABLE
-- ========================================================================

CREATE TABLE IF NOT EXISTS loan_disbursement_external_transaction (
    loan_disbursement_external_transaction_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_disbursement_id UUID NOT NULL,
    payment_provider_id UUID NOT NULL,
    psp_transaction_id VARCHAR(255),
    psp_transaction_reference VARCHAR(255),
    transaction_amount DECIMAL(18,2) NOT NULL,
    transaction_currency VARCHAR(3) DEFAULT 'USD',
    psp_status VARCHAR(50),
    psp_status_message TEXT,
    recipient_account_number VARCHAR(255),
    recipient_name VARCHAR(255),
    transaction_date TIMESTAMP NOT NULL,
    psp_response_payload TEXT,
    note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_external_tx_disbursement
        FOREIGN KEY (loan_disbursement_id)
        REFERENCES loan_disbursement (loan_disbursement_id)
        ON DELETE CASCADE
);

-- Add comments
COMMENT ON TABLE loan_disbursement_external_transaction IS 'Tracks external PSP transactions for loan disbursements';
COMMENT ON COLUMN loan_disbursement_external_transaction.payment_provider_id IS 'Reference to payment service provider master data';
COMMENT ON COLUMN loan_disbursement_external_transaction.psp_transaction_id IS 'Transaction ID from the PSP system';
COMMENT ON COLUMN loan_disbursement_external_transaction.psp_transaction_reference IS 'Transaction reference from the PSP system';
COMMENT ON COLUMN loan_disbursement_external_transaction.psp_status IS 'Status returned by the PSP';
COMMENT ON COLUMN loan_disbursement_external_transaction.psp_response_payload IS 'Full JSON/XML response from PSP for audit purposes';

-- Create indexes
CREATE INDEX idx_external_tx_disbursement_id ON loan_disbursement_external_transaction(loan_disbursement_id);
CREATE INDEX idx_external_tx_payment_provider ON loan_disbursement_external_transaction(payment_provider_id);
CREATE INDEX idx_external_tx_psp_transaction_id ON loan_disbursement_external_transaction(psp_transaction_id);
CREATE INDEX idx_external_tx_date ON loan_disbursement_external_transaction(transaction_date);
CREATE INDEX idx_external_tx_status ON loan_disbursement_external_transaction(psp_status);

-- ========================================================================
-- CREATE CASTS FOR NEW ENUMS
-- ========================================================================

-- Cast for disbursement_method
CREATE CAST (varchar AS disbursement_method) WITH INOUT AS IMPLICIT;

-- Cast for disbursement_status
CREATE CAST (varchar AS disbursement_status) WITH INOUT AS IMPLICIT;

