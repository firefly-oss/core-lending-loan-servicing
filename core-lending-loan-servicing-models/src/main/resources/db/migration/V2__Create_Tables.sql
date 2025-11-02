-- ========================================================================
-- V2 - CREATE TABLES FOR LOAN SERVICING
-- ========================================================================
-- This migration creates all database tables for the loan servicing module.
-- Tables are created in dependency order to satisfy foreign key constraints.
-- All tables use UUID primary keys for distributed system compatibility.
-- ========================================================================

-- ========================================================================
-- LOAN SERVICING CASE TABLE
-- ========================================================================
-- Core table representing a loan servicing case
-- Entity: LoanServicingCase
CREATE TABLE loan_servicing_case (
    loan_servicing_case_id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    contract_id                     UUID NOT NULL,
    product_id                      UUID NOT NULL,
    application_id                  UUID NOT NULL,
    proposed_offer_id               UUID,
    product_catalog_id              UUID,
    servicing_status                servicing_status NOT NULL,
    -- Loan configuration fields
    principal_amount                DECIMAL(18,2) NOT NULL,
    interest_rate                   DECIMAL(10,6) NOT NULL,
    loan_term                       INTEGER NOT NULL,
    interest_calculation_method     interest_calculation_method NOT NULL,
    amortization_method             amortization_method NOT NULL,
    payment_frequency               payment_frequency NOT NULL,
    compounding_frequency           compounding_frequency NOT NULL,
    day_count_convention            day_count_convention NOT NULL,
    origination_date                DATE NOT NULL,
    maturity_date                   DATE NOT NULL,
    remarks                         TEXT,
    created_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ========================================================================
-- LOAN DISBURSEMENT TABLE
-- ========================================================================
-- Tracks loan disbursements to borrowers
-- Entity: LoanDisbursement
CREATE TABLE loan_disbursement (
    loan_disbursement_id            UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_servicing_case_id          UUID NOT NULL,
    transaction_id                  UUID NOT NULL,
    disbursement_amount             DECIMAL(18,2) NOT NULL,
    disbursement_date               DATE NOT NULL,
    is_final_disbursement           BOOLEAN NOT NULL DEFAULT FALSE,
    disbursement_method             disbursement_method NOT NULL,
    disbursement_status             disbursement_status NOT NULL,
    payment_provider_id             UUID,
    distributor_id                  UUID,
    distributor_agency_id           UUID,
    distributor_agent_id            UUID,
    external_transaction_reference  VARCHAR(255),
    note                            TEXT,
    created_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_disbursement_case FOREIGN KEY (loan_servicing_case_id)
        REFERENCES loan_servicing_case (loan_servicing_case_id)
);

-- ========================================================================
-- LOAN DISBURSEMENT INTERNAL TRANSACTION TABLE
-- ========================================================================
-- Tracks internal account movements for disbursements
-- Entity: LoanDisbursementInternalTransaction
CREATE TABLE loan_disbursement_internal_transaction (
    loan_disbursement_internal_transaction_id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_disbursement_id                        UUID NOT NULL,
    source_account_id                           UUID NOT NULL,
    destination_account_id                      UUID NOT NULL,
    transaction_amount                          DECIMAL(18,2) NOT NULL,
    transaction_reference                       VARCHAR(255),
    transaction_date                            TIMESTAMP NOT NULL,
    note                                        TEXT,
    created_at                                  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                                  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_disb_int_txn_disbursement FOREIGN KEY (loan_disbursement_id) 
        REFERENCES loan_disbursement (loan_disbursement_id)
);

-- ========================================================================
-- LOAN DISBURSEMENT EXTERNAL TRANSACTION TABLE
-- ========================================================================
-- Tracks external PSP transactions for disbursements
-- Entity: LoanDisbursementExternalTransaction
CREATE TABLE loan_disbursement_external_transaction (
    loan_disbursement_external_transaction_id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_disbursement_id                        UUID NOT NULL,
    payment_provider_id                         UUID NOT NULL,
    psp_transaction_id                          VARCHAR(255) NOT NULL,
    psp_transaction_reference                   VARCHAR(255),
    transaction_amount                          DECIMAL(18,2) NOT NULL,
    transaction_currency                        VARCHAR(3) NOT NULL,
    psp_status                                  VARCHAR(50),
    psp_status_message                          TEXT,
    recipient_account_number                    VARCHAR(255),
    recipient_name                              VARCHAR(255),
    transaction_date                            TIMESTAMP NOT NULL,
    psp_response_payload                        TEXT,
    note                                        TEXT,
    created_at                                  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                                  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_disb_ext_txn_disbursement FOREIGN KEY (loan_disbursement_id) 
        REFERENCES loan_disbursement (loan_disbursement_id)
);

-- ========================================================================
-- LOAN DISBURSEMENT PLAN TABLE
-- ========================================================================
-- Tracks planned disbursements for construction loans, LOCs, etc.
-- Entity: LoanDisbursementPlan
CREATE TABLE loan_disbursement_plan (
    loan_disbursement_plan_id       UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_servicing_case_id          UUID NOT NULL,
    planned_disbursement_date       DATE NOT NULL,
    planned_amount                  DECIMAL(18,2) NOT NULL,
    actual_disbursement_date        DATE,
    actual_amount                   DECIMAL(18,2),
    disbursement_number             INTEGER NOT NULL,
    is_completed                    BOOLEAN NOT NULL DEFAULT FALSE,
    purpose                         VARCHAR(500) NOT NULL,
    remarks                         TEXT,
    created_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_disb_plan_case FOREIGN KEY (loan_servicing_case_id) 
        REFERENCES loan_servicing_case (loan_servicing_case_id)
);

-- ========================================================================
-- LOAN INSTALLMENT PLAN TABLE
-- ========================================================================
-- Tracks the installment/repayment schedule
-- Entity: LoanInstallmentPlan
CREATE TABLE loan_installment_plan (
    loan_installment_plan_id        UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_servicing_case_id          UUID NOT NULL,
    installment_number              INTEGER NOT NULL,
    due_date                        DATE NOT NULL,
    principal_due                   DECIMAL(18,2) NOT NULL,
    interest_due                    DECIMAL(18,2) NOT NULL,
    fee_due                         DECIMAL(18,2) NOT NULL DEFAULT 0,
    total_due                       DECIMAL(18,2) NOT NULL,
    is_paid                         BOOLEAN NOT NULL DEFAULT FALSE,
    paid_date                       DATE,
    paid_amount                     DECIMAL(18,2),
    payment_method                  payment_method,
    payment_account_id              UUID,
    payment_provider_id             UUID,
    external_account_reference      VARCHAR(255),
    is_automatic_payment            BOOLEAN NOT NULL DEFAULT FALSE,
    created_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_installment_plan_case FOREIGN KEY (loan_servicing_case_id) 
        REFERENCES loan_servicing_case (loan_servicing_case_id)
);

-- ========================================================================
-- LOAN INSTALLMENT RECORD TABLE
-- ========================================================================
-- Tracks actual installment payments made
-- Entity: LoanInstallmentRecord
CREATE TABLE loan_installment_record (
    loan_installment_record_id      UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_servicing_case_id          UUID NOT NULL,
    loan_installment_plan_id        UUID NOT NULL,
    transaction_id                  UUID NOT NULL,
    payment_amount                  DECIMAL(18,2) NOT NULL,
    payment_date                    DATE NOT NULL,
    is_partial_payment              BOOLEAN NOT NULL DEFAULT FALSE,
    payment_method                  payment_method NOT NULL,
    payment_status                  payment_status NOT NULL,
    payment_provider_id             UUID,
    external_transaction_reference  VARCHAR(255),
    note                            TEXT,
    created_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_installment_record_case FOREIGN KEY (loan_servicing_case_id) 
        REFERENCES loan_servicing_case (loan_servicing_case_id),
    CONSTRAINT fk_installment_record_plan FOREIGN KEY (loan_installment_plan_id) 
        REFERENCES loan_installment_plan (loan_installment_plan_id)
);

-- ========================================================================
-- LOAN INSTALLMENT RECORD INTERNAL TRANSACTION TABLE
-- ========================================================================
-- Tracks internal account movements for installment payments
-- Entity: LoanInstallmentRecordInternalTransaction
CREATE TABLE loan_installment_record_internal_transaction (
    loan_installment_record_internal_transaction_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_installment_record_id                      UUID NOT NULL,
    source_account_id                               UUID NOT NULL,
    destination_account_id                          UUID NOT NULL,
    transaction_amount                              DECIMAL(18,2) NOT NULL,
    transaction_reference                           VARCHAR(255),
    transaction_date                                TIMESTAMP NOT NULL,
    note                                            TEXT,
    created_at                                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_inst_int_txn_record FOREIGN KEY (loan_installment_record_id) 
        REFERENCES loan_installment_record (loan_installment_record_id)
);

-- ========================================================================
-- LOAN INSTALLMENT RECORD EXTERNAL TRANSACTION TABLE
-- ========================================================================
-- Tracks external PSP transactions for installment payments
-- Entity: LoanInstallmentRecordExternalTransaction
CREATE TABLE loan_installment_record_external_transaction (
    loan_installment_record_external_transaction_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_installment_record_id                      UUID NOT NULL,
    payment_provider_id                             UUID NOT NULL,
    psp_transaction_id                              VARCHAR(255) NOT NULL,
    psp_transaction_reference                       VARCHAR(255),
    transaction_amount                              DECIMAL(18,2) NOT NULL,
    transaction_currency                            VARCHAR(3) NOT NULL,
    psp_status                                      VARCHAR(50),
    psp_status_message                              TEXT,
    payer_account_number                            VARCHAR(255),
    payer_name                                      VARCHAR(255),
    transaction_date                                TIMESTAMP NOT NULL,
    psp_response_payload                            TEXT,
    note                                            TEXT,
    created_at                                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_inst_ext_txn_record FOREIGN KEY (loan_installment_record_id) 
        REFERENCES loan_installment_record (loan_installment_record_id)
);

-- ========================================================================
-- LOAN REPAYMENT SCHEDULE TABLE
-- ========================================================================
-- Tracks planned repayment schedule for a loan
-- Entity: LoanRepaymentSchedule
CREATE TABLE loan_repayment_schedule (
    loan_repayment_schedule_id      UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_servicing_case_id          UUID NOT NULL,
    installment_number              INTEGER NOT NULL,
    due_date                        DATE NOT NULL,
    principal_due                   DECIMAL(18,2) NOT NULL,
    interest_due                    DECIMAL(18,2) NOT NULL,
    fee_due                         DECIMAL(18,2) NOT NULL,
    total_due                       DECIMAL(18,2) NOT NULL,
    is_paid                         BOOLEAN NOT NULL DEFAULT FALSE,
    paid_date                       DATE,
    paid_amount                     DECIMAL(18,2),
    created_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_repayment_schedule_case FOREIGN KEY (loan_servicing_case_id)
        REFERENCES loan_servicing_case (loan_servicing_case_id)
);

-- ========================================================================
-- LOAN REPAYMENT RECORD TABLE
-- ========================================================================
-- Tracks actual repayment transactions for a loan
-- Entity: LoanRepaymentRecord
CREATE TABLE loan_repayment_record (
    loan_repayment_record_id        UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_servicing_case_id          UUID NOT NULL,
    loan_repayment_schedule_id      UUID,
    transaction_id                  UUID NOT NULL,
    payment_amount                  DECIMAL(18,2) NOT NULL,
    payment_date                    DATE NOT NULL,
    is_partial_payment              BOOLEAN NOT NULL DEFAULT FALSE,
    note                            TEXT,
    created_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_repayment_record_case FOREIGN KEY (loan_servicing_case_id)
        REFERENCES loan_servicing_case (loan_servicing_case_id),
    CONSTRAINT fk_repayment_record_schedule FOREIGN KEY (loan_repayment_schedule_id)
        REFERENCES loan_repayment_schedule (loan_repayment_schedule_id)
);

-- ========================================================================
-- LOAN BALANCE TABLE
-- ========================================================================
-- Tracks balance snapshots for a loan
-- Entity: LoanBalance
CREATE TABLE loan_balance (
    loan_balance_id                 UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_servicing_case_id          UUID NOT NULL,
    principal_outstanding           DECIMAL(18,2) NOT NULL DEFAULT 0,
    interest_outstanding            DECIMAL(18,2) NOT NULL DEFAULT 0,
    fees_outstanding                DECIMAL(18,2) NOT NULL DEFAULT 0,
    total_outstanding               DECIMAL(18,2) NOT NULL DEFAULT 0,
    balance_date                    DATE NOT NULL,
    is_current                      BOOLEAN NOT NULL DEFAULT TRUE,
    created_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_balance_case FOREIGN KEY (loan_servicing_case_id)
        REFERENCES loan_servicing_case (loan_servicing_case_id)
);

-- ========================================================================
-- LOAN ACCRUAL TABLE
-- ========================================================================
-- Tracks interest, fees, and penalties accrued
-- Entity: LoanAccrual
CREATE TABLE loan_accrual (
    loan_accrual_id                 UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_servicing_case_id          UUID NOT NULL,
    accrual_amount                  DECIMAL(18,2) NOT NULL,
    accrual_type                    accrual_type NOT NULL,
    accrual_date                    DATE NOT NULL,
    note                            TEXT,
    created_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_accrual_case FOREIGN KEY (loan_servicing_case_id)
        REFERENCES loan_servicing_case (loan_servicing_case_id)
);

-- ========================================================================
-- LOAN RATE CHANGE TABLE
-- ========================================================================
-- Tracks interest rate changes
-- Entity: LoanRateChange
CREATE TABLE loan_rate_change (
    loan_rate_change_id             UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_servicing_case_id          UUID NOT NULL,
    old_interest_rate               DECIMAL(10,6) NOT NULL,
    new_interest_rate               DECIMAL(10,6) NOT NULL,
    effective_date                  DATE NOT NULL,
    reason_code                     reason_code NOT NULL,
    note                            TEXT,
    created_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_rate_change_case FOREIGN KEY (loan_servicing_case_id)
        REFERENCES loan_servicing_case (loan_servicing_case_id)
);

-- ========================================================================
-- LOAN SERVICING EVENT TABLE
-- ========================================================================
-- Tracks servicing events (restructure, extension, deferment, etc.)
-- Entity: LoanServicingEvent
CREATE TABLE loan_servicing_event (
    loan_servicing_event_id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_servicing_case_id          UUID NOT NULL,
    event_type                      event_type NOT NULL,
    event_date                      DATE NOT NULL,
    description                     TEXT,
    created_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_event_case FOREIGN KEY (loan_servicing_case_id)
        REFERENCES loan_servicing_case (loan_servicing_case_id)
);

-- ========================================================================
-- LOAN RESTRUCTURING TABLE
-- ========================================================================
-- Tracks loan restructuring events with old and new terms
-- Entity: LoanRestructuring
CREATE TABLE loan_restructuring (
    loan_restructuring_id           UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_servicing_case_id          UUID NOT NULL,
    restructuring_date              DATE NOT NULL,
    reason                          VARCHAR(500) NOT NULL,
    -- Old loan terms
    old_principal_amount            DECIMAL(18,2) NOT NULL,
    old_interest_rate               DECIMAL(10,6) NOT NULL,
    old_loan_term                   INTEGER NOT NULL,
    old_interest_calculation_method interest_calculation_method NOT NULL,
    old_amortization_method         amortization_method NOT NULL,
    old_payment_frequency           payment_frequency NOT NULL,
    old_compounding_frequency       compounding_frequency NOT NULL,
    old_day_count_convention        day_count_convention NOT NULL,
    old_maturity_date               DATE NOT NULL,
    -- New loan terms
    new_principal_amount            DECIMAL(18,2) NOT NULL,
    new_interest_rate               DECIMAL(10,6) NOT NULL,
    new_loan_term                   INTEGER NOT NULL,
    new_interest_calculation_method interest_calculation_method NOT NULL,
    new_amortization_method         amortization_method NOT NULL,
    new_payment_frequency           payment_frequency NOT NULL,
    new_compounding_frequency       compounding_frequency NOT NULL,
    new_day_count_convention        day_count_convention NOT NULL,
    new_maturity_date               DATE NOT NULL,
    approved_by                     UUID,
    remarks                         TEXT,
    created_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_restructuring_case FOREIGN KEY (loan_servicing_case_id)
        REFERENCES loan_servicing_case (loan_servicing_case_id)
);

-- ========================================================================
-- LOAN REBATE TABLE
-- ========================================================================
-- Tracks rebates applied to loans
-- Entity: LoanRebate
CREATE TABLE loan_rebate (
    loan_rebate_id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_servicing_case_id          UUID NOT NULL,
    rebate_type                     rebate_type NOT NULL,
    rebate_amount                   DECIMAL(18,2) NOT NULL,
    rebate_date                     DATE NOT NULL,
    distributor_id                  UUID,
    distributor_agency_id           UUID,
    distributor_agent_id            UUID,
    distributor_commission          DECIMAL(18,2),
    is_processed                    BOOLEAN NOT NULL DEFAULT FALSE,
    processed_date                  DATE,
    description                     VARCHAR(500),
    remarks                         TEXT,
    created_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_rebate_case FOREIGN KEY (loan_servicing_case_id)
        REFERENCES loan_servicing_case (loan_servicing_case_id)
);

-- ========================================================================
-- LOAN ESCROW TABLE
-- ========================================================================
-- Tracks escrow accounts for all lending products
-- Entity: LoanEscrow
CREATE TABLE loan_escrow (
    loan_escrow_id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_servicing_case_id          UUID NOT NULL,
    escrow_type                     escrow_type NOT NULL,
    monthly_payment_amount          DECIMAL(18,2) NOT NULL DEFAULT 0,
    current_balance                 DECIMAL(18,2) NOT NULL DEFAULT 0,
    target_balance                  DECIMAL(18,2) NOT NULL DEFAULT 0,
    annual_disbursement_amount      DECIMAL(18,2) NOT NULL DEFAULT 0,
    next_disbursement_date          DATE,
    last_analysis_date              DATE,
    next_analysis_date              DATE,
    is_active                       BOOLEAN NOT NULL DEFAULT TRUE,
    payee_name                      VARCHAR(255),
    payee_account_number            VARCHAR(255),
    remarks                         TEXT,
    created_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_escrow_case FOREIGN KEY (loan_servicing_case_id)
        REFERENCES loan_servicing_case (loan_servicing_case_id)
);

-- ========================================================================
-- LOAN NOTIFICATION TABLE
-- ========================================================================
-- Tracks notifications sent to loan parties
-- Entity: LoanNotification
CREATE TABLE loan_notification (
    loan_notification_id            UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_servicing_case_id          UUID NOT NULL,
    notification_type               notification_type NOT NULL,
    notification_channel            notification_channel NOT NULL,
    notification_status             notification_status NOT NULL,
    recipient_party_id              UUID NOT NULL,
    recipient_name                  VARCHAR(255) NOT NULL,
    recipient_contact               VARCHAR(255) NOT NULL,
    subject                         VARCHAR(500),
    message_body                    TEXT NOT NULL,
    scheduled_send_time             TIMESTAMP,
    sent_time                       TIMESTAMP,
    delivered_time                  TIMESTAMP,
    read_time                       TIMESTAMP,
    failure_reason                  TEXT,
    retry_count                     INTEGER NOT NULL DEFAULT 0,
    template_id                     VARCHAR(100),
    metadata                        TEXT,
    created_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_notification_case FOREIGN KEY (loan_servicing_case_id)
        REFERENCES loan_servicing_case (loan_servicing_case_id)
);

-- ========================================================================
-- CREATE INDEXES FOR PERFORMANCE
-- ========================================================================

-- Loan Servicing Case indexes
CREATE INDEX idx_loan_servicing_case_contract_id ON loan_servicing_case (contract_id);
CREATE INDEX idx_loan_servicing_case_product_id ON loan_servicing_case (product_id);
CREATE INDEX idx_loan_servicing_case_application_id ON loan_servicing_case (application_id);
CREATE INDEX idx_loan_servicing_case_status ON loan_servicing_case (servicing_status);

-- Loan Disbursement indexes
CREATE INDEX idx_loan_disbursement_case_id ON loan_disbursement (loan_servicing_case_id);
CREATE INDEX idx_loan_disbursement_status ON loan_disbursement (disbursement_status);
CREATE INDEX idx_loan_disbursement_date ON loan_disbursement (disbursement_date);

-- Loan Installment Plan indexes
CREATE INDEX idx_loan_installment_plan_case_id ON loan_installment_plan (loan_servicing_case_id);
CREATE INDEX idx_loan_installment_plan_due_date ON loan_installment_plan (due_date);
CREATE INDEX idx_loan_installment_plan_is_paid ON loan_installment_plan (is_paid);

-- Loan Installment Record indexes
CREATE INDEX idx_loan_installment_record_case_id ON loan_installment_record (loan_servicing_case_id);
CREATE INDEX idx_loan_installment_record_plan_id ON loan_installment_record (loan_installment_plan_id);
CREATE INDEX idx_loan_installment_record_status ON loan_installment_record (payment_status);

-- Loan Balance indexes
CREATE INDEX idx_loan_balance_case_id ON loan_balance (loan_servicing_case_id);
CREATE INDEX idx_loan_balance_is_current ON loan_balance (is_current);
CREATE INDEX idx_loan_balance_date ON loan_balance (balance_date);

-- Loan Accrual indexes
CREATE INDEX idx_loan_accrual_case_id ON loan_accrual (loan_servicing_case_id);
CREATE INDEX idx_loan_accrual_date ON loan_accrual (accrual_date);
CREATE INDEX idx_loan_accrual_type ON loan_accrual (accrual_type);

-- Loan Rate Change indexes
CREATE INDEX idx_loan_rate_change_case_id ON loan_rate_change (loan_servicing_case_id);
CREATE INDEX idx_loan_rate_change_effective_date ON loan_rate_change (effective_date);

-- Loan Restructuring indexes
CREATE INDEX idx_loan_restructuring_case_id ON loan_restructuring (loan_servicing_case_id);
CREATE INDEX idx_loan_restructuring_date ON loan_restructuring (restructuring_date);

-- Loan Rebate indexes
CREATE INDEX idx_loan_rebate_case_id ON loan_rebate (loan_servicing_case_id);
CREATE INDEX idx_loan_rebate_type ON loan_rebate (rebate_type);
CREATE INDEX idx_loan_rebate_is_processed ON loan_rebate (is_processed);

-- Loan Escrow indexes
CREATE INDEX idx_loan_escrow_case_id ON loan_escrow (loan_servicing_case_id);
CREATE INDEX idx_loan_escrow_type ON loan_escrow (escrow_type);
CREATE INDEX idx_loan_escrow_is_active ON loan_escrow (is_active);

-- Loan Notification indexes
CREATE INDEX idx_loan_notification_case_id ON loan_notification (loan_servicing_case_id);
CREATE INDEX idx_loan_notification_type ON loan_notification (notification_type);
CREATE INDEX idx_loan_notification_status ON loan_notification (notification_status);
CREATE INDEX idx_loan_notification_recipient ON loan_notification (recipient_party_id);

