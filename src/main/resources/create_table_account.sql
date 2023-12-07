-- Create the account table if not exists
-- Should create account type and currency first
CREATE TABLE IF NOT EXISTS account (
    account_id SERIAL PRIMARY KEY,
    account_name VARCHAR(100) NOT NULL,
    balance DOUBLE PRECISION NOT NULL,
    currency_id INT  REFERENCES currency(currency_id) NOT NULL,
    account_type "account_type" NOT NULL,
);