-- V4 - CONVERT LONG ID FIELDS TO UUID
-- This migration converts all BIGSERIAL primary keys and BIGINT foreign keys to UUID

-- Add UUID extension if not exists
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Step 1: Add new UUID columns for all tables
ALTER TABLE loan_servicing_case 
ADD COLUMN loan_servicing_case_uuid UUID DEFAULT uuid_generate_v4(),
ADD COLUMN contract_uuid UUID,
ADD COLUMN product_uuid UUID,
ADD COLUMN account_uuid UUID;

ALTER TABLE loan_disbursement 
ADD COLUMN loan_disbursement_uuid UUID DEFAULT uuid_generate_v4(),
ADD COLUMN loan_servicing_case_uuid UUID,
ADD COLUMN transaction_uuid UUID;

ALTER TABLE loan_repayment_schedule 
ADD COLUMN loan_repayment_schedule_uuid UUID DEFAULT uuid_generate_v4(),
ADD COLUMN loan_servicing_case_uuid UUID;

ALTER TABLE loan_repayment_record 
ADD COLUMN loan_repayment_record_uuid UUID DEFAULT uuid_generate_v4(),
ADD COLUMN loan_servicing_case_uuid UUID,
ADD COLUMN loan_repayment_schedule_uuid UUID,
ADD COLUMN transaction_uuid UUID;

ALTER TABLE loan_accrual 
ADD COLUMN loan_accrual_uuid UUID DEFAULT uuid_generate_v4(),
ADD COLUMN loan_servicing_case_uuid UUID;

ALTER TABLE loan_rate_change 
ADD COLUMN loan_rate_change_uuid UUID DEFAULT uuid_generate_v4(),
ADD COLUMN loan_servicing_case_uuid UUID;

ALTER TABLE loan_servicing_event 
ADD COLUMN loan_servicing_event_uuid UUID DEFAULT uuid_generate_v4(),
ADD COLUMN loan_servicing_case_uuid UUID;

-- Step 2: Update foreign key relationships using the mapping from loan_servicing_case
UPDATE loan_disbursement SET loan_servicing_case_uuid = (
    SELECT loan_servicing_case_uuid FROM loan_servicing_case 
    WHERE loan_servicing_case.loan_servicing_case_id = loan_disbursement.loan_servicing_case_id
);

UPDATE loan_repayment_schedule SET loan_servicing_case_uuid = (
    SELECT loan_servicing_case_uuid FROM loan_servicing_case 
    WHERE loan_servicing_case.loan_servicing_case_id = loan_repayment_schedule.loan_servicing_case_id
);

UPDATE loan_repayment_record SET loan_servicing_case_uuid = (
    SELECT loan_servicing_case_uuid FROM loan_servicing_case 
    WHERE loan_servicing_case.loan_servicing_case_id = loan_repayment_record.loan_servicing_case_id
);

UPDATE loan_repayment_record SET loan_repayment_schedule_uuid = (
    SELECT loan_repayment_schedule_uuid FROM loan_repayment_schedule 
    WHERE loan_repayment_schedule.loan_repayment_schedule_id = loan_repayment_record.loan_repayment_schedule_id
);

UPDATE loan_accrual SET loan_servicing_case_uuid = (
    SELECT loan_servicing_case_uuid FROM loan_servicing_case 
    WHERE loan_servicing_case.loan_servicing_case_id = loan_accrual.loan_servicing_case_id
);

UPDATE loan_rate_change SET loan_servicing_case_uuid = (
    SELECT loan_servicing_case_uuid FROM loan_servicing_case 
    WHERE loan_servicing_case.loan_servicing_case_id = loan_rate_change.loan_servicing_case_id
);

UPDATE loan_servicing_event SET loan_servicing_case_uuid = (
    SELECT loan_servicing_case_uuid FROM loan_servicing_case 
    WHERE loan_servicing_case.loan_servicing_case_id = loan_servicing_event.loan_servicing_case_id
);

-- Step 3: Drop existing foreign key constraints
ALTER TABLE loan_disbursement DROP CONSTRAINT IF EXISTS fk_disb_serv_case;
ALTER TABLE loan_repayment_schedule DROP CONSTRAINT IF EXISTS fk_sched_serv_case;
ALTER TABLE loan_repayment_record DROP CONSTRAINT IF EXISTS fk_repay_serv_case;
ALTER TABLE loan_accrual DROP CONSTRAINT IF EXISTS fk_accrual_serv_case;
ALTER TABLE loan_rate_change DROP CONSTRAINT IF EXISTS fk_ratechg_serv_case;
ALTER TABLE loan_servicing_event DROP CONSTRAINT IF EXISTS fk_event_serv_case;

-- Step 4: Drop old BIGINT columns
ALTER TABLE loan_servicing_case 
DROP COLUMN loan_servicing_case_id,
DROP COLUMN contract_id,
DROP COLUMN product_id,
DROP COLUMN account_id;

ALTER TABLE loan_disbursement 
DROP COLUMN loan_disbursement_id,
DROP COLUMN loan_servicing_case_id,
DROP COLUMN transaction_id;

ALTER TABLE loan_repayment_schedule 
DROP COLUMN loan_repayment_schedule_id,
DROP COLUMN loan_servicing_case_id;

ALTER TABLE loan_repayment_record 
DROP COLUMN loan_repayment_record_id,
DROP COLUMN loan_servicing_case_id,
DROP COLUMN loan_repayment_schedule_id,
DROP COLUMN transaction_id;

ALTER TABLE loan_accrual 
DROP COLUMN loan_accrual_id,
DROP COLUMN loan_servicing_case_id;

ALTER TABLE loan_rate_change 
DROP COLUMN loan_rate_change_id,
DROP COLUMN loan_servicing_case_id;

ALTER TABLE loan_servicing_event 
DROP COLUMN loan_servicing_event_id,
DROP COLUMN loan_servicing_case_id;

-- Step 5: Rename UUID columns to original names
ALTER TABLE loan_servicing_case 
RENAME COLUMN loan_servicing_case_uuid TO loan_servicing_case_id;
ALTER TABLE loan_servicing_case 
RENAME COLUMN contract_uuid TO contract_id;
ALTER TABLE loan_servicing_case 
RENAME COLUMN product_uuid TO product_id;
ALTER TABLE loan_servicing_case 
RENAME COLUMN account_uuid TO account_id;

ALTER TABLE loan_disbursement 
RENAME COLUMN loan_disbursement_uuid TO loan_disbursement_id;
ALTER TABLE loan_disbursement 
RENAME COLUMN loan_servicing_case_uuid TO loan_servicing_case_id;
ALTER TABLE loan_disbursement 
RENAME COLUMN transaction_uuid TO transaction_id;

ALTER TABLE loan_repayment_schedule 
RENAME COLUMN loan_repayment_schedule_uuid TO loan_repayment_schedule_id;
ALTER TABLE loan_repayment_schedule 
RENAME COLUMN loan_servicing_case_uuid TO loan_servicing_case_id;

ALTER TABLE loan_repayment_record 
RENAME COLUMN loan_repayment_record_uuid TO loan_repayment_record_id;
ALTER TABLE loan_repayment_record 
RENAME COLUMN loan_servicing_case_uuid TO loan_servicing_case_id;
ALTER TABLE loan_repayment_record 
RENAME COLUMN loan_repayment_schedule_uuid TO loan_repayment_schedule_id;
ALTER TABLE loan_repayment_record 
RENAME COLUMN transaction_uuid TO transaction_id;

ALTER TABLE loan_accrual 
RENAME COLUMN loan_accrual_uuid TO loan_accrual_id;
ALTER TABLE loan_accrual 
RENAME COLUMN loan_servicing_case_uuid TO loan_servicing_case_id;

ALTER TABLE loan_rate_change 
RENAME COLUMN loan_rate_change_uuid TO loan_rate_change_id;
ALTER TABLE loan_rate_change 
RENAME COLUMN loan_servicing_case_uuid TO loan_servicing_case_id;

ALTER TABLE loan_servicing_event 
RENAME COLUMN loan_servicing_event_uuid TO loan_servicing_event_id;
ALTER TABLE loan_servicing_event 
RENAME COLUMN loan_servicing_case_uuid TO loan_servicing_case_id;

-- Step 6: Add primary key constraints
ALTER TABLE loan_servicing_case ADD PRIMARY KEY (loan_servicing_case_id);
ALTER TABLE loan_disbursement ADD PRIMARY KEY (loan_disbursement_id);
ALTER TABLE loan_repayment_schedule ADD PRIMARY KEY (loan_repayment_schedule_id);
ALTER TABLE loan_repayment_record ADD PRIMARY KEY (loan_repayment_record_id);
ALTER TABLE loan_accrual ADD PRIMARY KEY (loan_accrual_id);
ALTER TABLE loan_rate_change ADD PRIMARY KEY (loan_rate_change_id);
ALTER TABLE loan_servicing_event ADD PRIMARY KEY (loan_servicing_event_id);

-- Step 7: Recreate foreign key constraints
ALTER TABLE loan_disbursement 
ADD CONSTRAINT fk_disb_serv_case 
FOREIGN KEY (loan_servicing_case_id) REFERENCES loan_servicing_case (loan_servicing_case_id);

ALTER TABLE loan_repayment_schedule 
ADD CONSTRAINT fk_sched_serv_case 
FOREIGN KEY (loan_servicing_case_id) REFERENCES loan_servicing_case (loan_servicing_case_id);

ALTER TABLE loan_repayment_record 
ADD CONSTRAINT fk_repay_serv_case 
FOREIGN KEY (loan_servicing_case_id) REFERENCES loan_servicing_case (loan_servicing_case_id);

ALTER TABLE loan_accrual 
ADD CONSTRAINT fk_accrual_serv_case 
FOREIGN KEY (loan_servicing_case_id) REFERENCES loan_servicing_case (loan_servicing_case_id);

ALTER TABLE loan_rate_change 
ADD CONSTRAINT fk_ratechg_serv_case 
FOREIGN KEY (loan_servicing_case_id) REFERENCES loan_servicing_case (loan_servicing_case_id);

ALTER TABLE loan_servicing_event 
ADD CONSTRAINT fk_event_serv_case 
FOREIGN KEY (loan_servicing_case_id) REFERENCES loan_servicing_case (loan_servicing_case_id);

-- Step 8: Make UUID columns NOT NULL where appropriate
ALTER TABLE loan_servicing_case ALTER COLUMN loan_servicing_case_id SET NOT NULL;
ALTER TABLE loan_disbursement ALTER COLUMN loan_disbursement_id SET NOT NULL;
ALTER TABLE loan_disbursement ALTER COLUMN loan_servicing_case_id SET NOT NULL;
ALTER TABLE loan_repayment_schedule ALTER COLUMN loan_repayment_schedule_id SET NOT NULL;
ALTER TABLE loan_repayment_schedule ALTER COLUMN loan_servicing_case_id SET NOT NULL;
ALTER TABLE loan_repayment_record ALTER COLUMN loan_repayment_record_id SET NOT NULL;
ALTER TABLE loan_repayment_record ALTER COLUMN loan_servicing_case_id SET NOT NULL;
ALTER TABLE loan_accrual ALTER COLUMN loan_accrual_id SET NOT NULL;
ALTER TABLE loan_accrual ALTER COLUMN loan_servicing_case_id SET NOT NULL;
ALTER TABLE loan_rate_change ALTER COLUMN loan_rate_change_id SET NOT NULL;
ALTER TABLE loan_rate_change ALTER COLUMN loan_servicing_case_id SET NOT NULL;
ALTER TABLE loan_servicing_event ALTER COLUMN loan_servicing_event_id SET NOT NULL;
ALTER TABLE loan_servicing_event ALTER COLUMN loan_servicing_case_id SET NOT NULL;