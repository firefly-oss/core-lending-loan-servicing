-- Syndicated facility on a loan servicing case.
-- Participants cascade with the facility. Cross-service ids stay UUID columns without foreign keys.

CREATE TYPE facility_status AS ENUM ('OPEN', 'CLOSED');
CREATE TYPE facility_participant_role AS ENUM ('AGENT', 'LENDER');

CREATE CAST (VARCHAR AS facility_status) WITH INOUT AS IMPLICIT;
CREATE CAST (facility_status AS VARCHAR) WITH INOUT AS IMPLICIT;
CREATE CAST (VARCHAR AS facility_participant_role) WITH INOUT AS IMPLICIT;
CREATE CAST (facility_participant_role AS VARCHAR) WITH INOUT AS IMPLICIT;

CREATE TABLE syndicated_facility (
    facility_id                 UUID PRIMARY KEY,
    loan_servicing_case_id      UUID NOT NULL,
    origination_facility_id     UUID,
    currency                    VARCHAR(3) NOT NULL,
    commitment_amount           DECIMAL(18,2) NOT NULL,
    status                      facility_status NOT NULL,
    created_at                  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_syndicated_facility_case FOREIGN KEY (loan_servicing_case_id)
        REFERENCES loan_servicing_case (loan_servicing_case_id),
    CONSTRAINT ck_syndicated_facility_currency CHECK (currency ~ '^[A-Z]{3}$'),
    CONSTRAINT ck_syndicated_facility_commitment CHECK (commitment_amount > 0)
);

CREATE TABLE facility_participant (
    participant_id              UUID PRIMARY KEY,
    facility_id                 UUID NOT NULL,
    party_id                    UUID NOT NULL,
    share_percent               DECIMAL(7,4) NOT NULL,
    role                        facility_participant_role NOT NULL,
    created_at                  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_facility_participant_facility FOREIGN KEY (facility_id)
        REFERENCES syndicated_facility (facility_id) ON DELETE CASCADE,
    CONSTRAINT ck_facility_participant_share CHECK (share_percent >= 0 AND share_percent <= 100)
);

CREATE TABLE facility_status_history (
    history_id                  UUID PRIMARY KEY,
    facility_id                 UUID NOT NULL,
    previous_status             facility_status NOT NULL,
    status                      facility_status NOT NULL,
    changed_at                  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_facility_status_history_facility FOREIGN KEY (facility_id)
        REFERENCES syndicated_facility (facility_id) ON DELETE CASCADE
);

CREATE TABLE facility_repayment_allocation (
    allocation_id               UUID PRIMARY KEY,
    facility_id                 UUID NOT NULL,
    loan_repayment_record_id    UUID,
    payment_amount              DECIMAL(18,2) NOT NULL,
    unallocated_amount          DECIMAL(18,2) NOT NULL,
    currency                    VARCHAR(3) NOT NULL,
    payment_id                  UUID,
    created_at                  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_facility_allocation_facility FOREIGN KEY (facility_id)
        REFERENCES syndicated_facility (facility_id) ON DELETE CASCADE,
    CONSTRAINT ck_facility_allocation_currency CHECK (currency ~ '^[A-Z]{3}$'),
    CONSTRAINT ck_facility_allocation_amount CHECK (payment_amount > 0 AND unallocated_amount >= 0)
);

CREATE TABLE facility_allocation_line (
    line_id                     UUID PRIMARY KEY,
    allocation_id               UUID NOT NULL,
    participant_id              UUID NOT NULL,
    party_id                    UUID NOT NULL,
    role                        facility_participant_role NOT NULL,
    share_percent               DECIMAL(7,4) NOT NULL,
    allocated_amount            DECIMAL(18,2) NOT NULL,
    currency                    VARCHAR(3) NOT NULL,
    CONSTRAINT fk_facility_allocation_line FOREIGN KEY (allocation_id)
        REFERENCES facility_repayment_allocation (allocation_id) ON DELETE CASCADE,
    CONSTRAINT ck_facility_allocation_line_share CHECK (share_percent >= 0 AND share_percent <= 100)
);

ALTER TABLE loan_disbursement ADD COLUMN facility_id UUID;
