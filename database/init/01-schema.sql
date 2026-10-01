CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,CREATE TABLE cards (
                                 id BIGSERIAL PRIMARY KEY,
                                 card_number VARCHAR(16) NOT NULL UNIQUE,
                                 expiration_date DATE NOT NULL,
                                 status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
                                 credit_limit NUMERIC(15, 2) NOT NULL DEFAULT 0.00,
                                 available_balance NUMERIC(15, 2) NOT NULL DEFAULT 0.00,
                                 created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

                                 -- Restricción para asegurar estados válidos
                                 CONSTRAINT chk_card_status CHECK (status IN ('ACTIVE', 'BLOCKED', 'EXPIRED'))
                             );
    username VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL DEFAULT 'USER'
);



CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    card_id BIGINT NOT NULL,
    amount NUMERIC(15, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL DEFAULT 'USD',
    status VARCHAR(20) NOT NULL,
    authorization_code VARCHAR(10),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    -- Clave foránea hacia la tabla cards
    CONSTRAINT fk_transactions_card FOREIGN KEY (card_id)
        REFERENCES cards (id) ON DELETE RESTRICT,

    -- Restricción para asegurar estados válidos
    CONSTRAINT chk_transaction_status CHECK (status IN ('APPROVED', 'DECLINED'))
);

CREATE TABLE audit_events (
    id BIGSERIAL PRIMARY KEY,
    transaction_id BIGINT,
    event_type VARCHAR(50) NOT NULL,
    username VARCHAR(100) NOT NULL,
    payload JSONB, -- Se usa JSONB para mayor flexibilidad y rendimiento en PostgreSQL
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    -- Clave foránea hacia la tabla transactions (opcional/anulable por si hay eventos sin transacción)
    CONSTRAINT fk_audit_events_transaction FOREIGN KEY (transaction_id)
        REFERENCES transactions (id) ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS idx_audit_events_created_at ON audit_events(created_at);
CREATE INDEX IF NOT EXISTS idx_audit_events_username ON audit_events(username);
