CREATE TABLE IF NOT EXISTS transaction
(
    transaction_id   SERIAL PRIMARY KEY,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    transaction_type transaction_type NOT NULL,
    amount           DOUBLE PRECISION NOT NULL,
    reason           VARCHAR(255)     NOT NULL,
    account_id       INT              NOT NULL,
    category_id      INT              NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account (account_id),
    FOREIGN KEY (category_id) REFERENCES category (category_id)
);