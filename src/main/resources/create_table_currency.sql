CREATE TABLE IF NOT EXISTS currency
(
    currency_id   SERIAL PRIMARY KEY,
    currency_name currency_name NOT NULL,
    currency_code currency_code NOT NULL
);