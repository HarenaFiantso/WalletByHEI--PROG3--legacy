-- Create the transaction table if not exists
-- Should create transaction_type first
CREATE TABLE IF NOT EXISTS "transaction" (
    transaction_id SERIAL PRIMARY KEY,
    label VARCHAR(100) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    date_time DATE,
    transaction_type "transaction_type" NOT NULL,
);