-- Create the transaction table if not exists
CREATE TABLE "transaction" IF NOT EXISTS (
    transaction_id SERIAL PRIMARY KEY,
    transaction_date DATE NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    description TEXT,
    account_id INT NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(account_id)
);
-- Three examples of mock data
INSERT INTO "transaction" (
        transaction_date,
        amount,
        description,
        account_id
    )
VALUES ('2023-11-28', 200.50, 'Grocery shopping', 1),
    ('2023-11-29', 50.00, 'Dinner at a restaurant', 2),
    ('2023-11-30', 1000.75, 'Electronics purchase', 3);