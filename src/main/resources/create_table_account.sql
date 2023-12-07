-- Create the account table if not exists
-- Should create account type and currency first
CREATE TABLE IF NOT EXISTS account (
    account_id SERIAL PRIMARY KEY,
    account_name VARCHAR(255) NOT NULL,
    balance DOUBLE NOT NULL,
    currency_id INT NOT NULL,
    account_type "account_type" NOT NULL,
    FOREIGN KEY (currency_id) REFERENCES currency(currency_id)
);