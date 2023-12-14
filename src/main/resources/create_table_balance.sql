CREATE TABLE IF NOT EXISTS balance
(
    balance_id        SERIAL PRIMARY KEY,
    balance_date_time TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    amount            DOUBLE PRECISION NOT NULL,
    account_id        INT,
    FOREIGN KEY (account_id) REFERENCES account (account_id)
);