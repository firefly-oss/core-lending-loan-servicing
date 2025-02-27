-- V2 - CREATE TABLES FOR LOAN SERVICING & ADMINISTRATION

-- ========================================================================
-- TABLE: loan_servicing_case
-- ========================================================================
CREATE TABLE IF NOT EXISTS loan_servicing_case (
                                                   loan_servicing_case_id   BIGSERIAL PRIMARY KEY,
                                                   contract_id              BIGINT,
                                                   product_id               BIGINT,
                                                   account_id               BIGINT,
                                                   servicing_status         servicing_status NOT NULL,
                                                   principal_outstanding    DECIMAL(18,2) DEFAULT 0,
    interest_outstanding     DECIMAL(18,2) DEFAULT 0,
    fees_outstanding         DECIMAL(18,2) DEFAULT 0,
    origination_date         DATE,
    maturity_date            DATE,
    remarks                  TEXT,
    created_at               TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at               TIMESTAMP NOT NULL DEFAULT NOW()
    );

-- ========================================================================
-- TABLE: loan_disbursement
-- ========================================================================
CREATE TABLE IF NOT EXISTS loan_disbursement (
                                                 loan_disbursement_id     BIGSERIAL PRIMARY KEY,
                                                 loan_servicing_case_id   BIGINT NOT NULL,
                                                 transaction_id           BIGINT,
                                                 disbursement_amount      DECIMAL(18,2) NOT NULL,
    disbursement_date        DATE,
    is_final_disbursement    BOOLEAN DEFAULT FALSE,
    note                     TEXT,
    created_at               TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at               TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_disb_serv_case
    FOREIGN KEY (loan_servicing_case_id)
    REFERENCES loan_servicing_case (loan_servicing_case_id)
    );

-- ========================================================================
-- TABLE: loan_repayment_schedule
-- ========================================================================
CREATE TABLE IF NOT EXISTS loan_repayment_schedule (
                                                       loan_repayment_schedule_id  BIGSERIAL PRIMARY KEY,
                                                       loan_servicing_case_id      BIGINT NOT NULL,
                                                       installment_number          INT NOT NULL,
                                                       due_date                    DATE NOT NULL,
                                                       principal_due               DECIMAL(18,2) DEFAULT 0,
    interest_due                DECIMAL(18,2) DEFAULT 0,
    fee_due                     DECIMAL(18,2) DEFAULT 0,
    total_due                   DECIMAL(18,2) DEFAULT 0,
    is_paid                     BOOLEAN DEFAULT FALSE,
    paid_date                   DATE,
    paid_amount                 DECIMAL(18,2) DEFAULT 0,
    created_at                  TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at                  TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_sched_serv_case
    FOREIGN KEY (loan_servicing_case_id)
    REFERENCES loan_servicing_case (loan_servicing_case_id)
    );

-- ========================================================================
-- TABLE: loan_repayment_record
-- ========================================================================
CREATE TABLE IF NOT EXISTS loan_repayment_record (
                                                     loan_repayment_record_id  BIGSERIAL PRIMARY KEY,
                                                     loan_servicing_case_id    BIGINT NOT NULL,
                                                     loan_repayment_schedule_id BIGINT,    -- optional link to schedule installment
                                                     transaction_id            BIGINT,
                                                     payment_amount            DECIMAL(18,2) NOT NULL,
    payment_date              DATE,
    is_partial_payment        BOOLEAN DEFAULT FALSE,
    note                      TEXT,
    created_at                TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at                TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_repay_serv_case
    FOREIGN KEY (loan_servicing_case_id)
    REFERENCES loan_servicing_case (loan_servicing_case_id)
    );

-- ========================================================================
-- TABLE: loan_accrual
-- ========================================================================
CREATE TABLE IF NOT EXISTS loan_accrual (
                                            loan_accrual_id           BIGSERIAL PRIMARY KEY,
                                            loan_servicing_case_id    BIGINT NOT NULL,
                                            accrual_amount            DECIMAL(18,2) NOT NULL,
    accrual_type              accrual_type NOT NULL,
    accrual_date              DATE,
    note                      TEXT,
    created_at                TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at                TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_accrual_serv_case
    FOREIGN KEY (loan_servicing_case_id)
    REFERENCES loan_servicing_case (loan_servicing_case_id)
    );

-- ========================================================================
-- TABLE: loan_rate_change
-- ========================================================================
CREATE TABLE IF NOT EXISTS loan_rate_change (
                                                loan_rate_change_id       BIGSERIAL PRIMARY KEY,
                                                loan_servicing_case_id    BIGINT NOT NULL,
                                                old_interest_rate         DECIMAL(7,4),
    new_interest_rate         DECIMAL(7,4),
    effective_date            DATE,
    reason_code               reason_code,
    note                      TEXT,
    created_at                TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at                TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_ratechg_serv_case
    FOREIGN KEY (loan_servicing_case_id)
    REFERENCES loan_servicing_case (loan_servicing_case_id)
    );

-- ========================================================================
-- TABLE: loan_servicing_event
-- ========================================================================
CREATE TABLE IF NOT EXISTS loan_servicing_event (
                                                    loan_servicing_event_id   BIGSERIAL PRIMARY KEY,
                                                    loan_servicing_case_id    BIGINT NOT NULL,
                                                    event_type                event_type NOT NULL,
                                                    event_date                DATE,
                                                    description               TEXT,
                                                    created_at                TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at                TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_event_serv_case
    FOREIGN KEY (loan_servicing_case_id)
    REFERENCES loan_servicing_case (loan_servicing_case_id)
    );
