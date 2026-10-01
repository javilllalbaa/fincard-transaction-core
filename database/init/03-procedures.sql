CREATE OR REPLACE FUNCTION audit_event_count(p_username VARCHAR)
RETURNS BIGINT
LANGUAGE SQL
AS $$
    SELECT COUNT(*) FROM audit_events WHERE username = p_username;
$$;

CREATE OR REPLACE FUNCTION authorize_transaction(
    p_card_id BIGINT,
    p_amount NUMERIC
)
RETURNS BOOLEAN
LANGUAGE plpgsql
AS $$
DECLARE
    v_balance NUMERIC;
BEGIN

    SELECT available_balance
    INTO v_balance
    FROM cards
    WHERE id = p_card_id
    FOR UPDATE;

    IF v_balance IS NULL THEN
        RETURN FALSE;
    END IF;

    IF v_balance < p_amount THEN
        RETURN FALSE;
    END IF;

    UPDATE cards
    SET available_balance = available_balance - p_amount,
        updated_at = CURRENT_TIMESTAMP
    WHERE id = p_card_id;

    RETURN TRUE;

END;
$$;