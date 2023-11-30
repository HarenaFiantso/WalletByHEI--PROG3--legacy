-- Create the account table if not exists
CREATE TABLE IF NOT EXISTS account (
    account_id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL CHECK (balance >= 0),
    currency_id INT NOT NULL,
    FOREIGN KEY (currency_id) REFERENCES currencies(currency_id)
);
-- Three examples of mock data
INSERT INTO account (
        first_name,
        last_name,
        password,
        balance,
        currency_id
    )
VALUES ('John', 'Doe', 'password1', 1500, 1),
    ('Alice', 'Smith', 'password2', 500, 2),
    ('Bob', 'Johnson', 'password3', 7500, 3);