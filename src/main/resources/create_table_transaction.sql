-- Create the transaction table if not exists
-- Should create transaction_type first
CREATE TABLE IF NOT EXISTS "transaction" (
    transaction_id SERIAL PRIMARY KEY,
    account_id INT NOT NULL,
    label VARCHAR(255) NOT NULL,
    amount DOUBLE NOT NULL,
    transaction_type "transaction_type" NOT NULL,
    transaction_date_time TIMESTAMP NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(account_id)
);