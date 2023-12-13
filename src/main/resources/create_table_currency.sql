CREATE TABLE IF NOT EXISTS currency
(
    currency_id   SERIAL PRIMARY KEY,
    currency_name VARCHAR(50) NOT NULL,
    currency_code VARCHAR(5)  NOT NULL
);