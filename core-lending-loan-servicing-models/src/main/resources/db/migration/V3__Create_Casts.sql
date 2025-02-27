-- V3 - CASTS USING "WITH INOUT AS IMPLICIT" FOR ALL ENUM TYPES

-------------------------
-- servicing_status
-------------------------
CREATE CAST (varchar AS servicing_status)
    WITH INOUT
    AS IMPLICIT;

-------------------------
-- accrual_type
-------------------------
CREATE CAST (varchar AS accrual_type)
    WITH INOUT
    AS IMPLICIT;

-------------------------
-- reason_code
-------------------------
CREATE CAST (varchar AS reason_code)
    WITH INOUT
    AS IMPLICIT;

-------------------------
-- event_type
-------------------------
CREATE CAST (varchar AS event_type)
    WITH INOUT
    AS IMPLICIT;
