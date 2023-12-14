CREATE TABLE IF NOT EXISTS "transaction"
(
    transaction_id        SERIAL PRIMARY KEY,
    label                 VARCHAR(255)                        NOT NULL,
    amount                DOUBLE PRECISION                    NOT NULL,
    transaction_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    account_id            INT                                 NOT NULL,
    transaction_type      "transaction_type"                  NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account (account_id)
);