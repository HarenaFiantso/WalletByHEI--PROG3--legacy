CREATE TABLE IF NOT EXISTS account
(
    account_id   SERIAL PRIMARY KEY,
    account_name VARCHAR(255) NOT NULL,
    currency_id  INT NOT NULL,
    account_type account_type NOT NULL DEFAULT 'CASH',
    FOREIGN KEY (currency_id) REFERENCES currency (currency_id)
);
