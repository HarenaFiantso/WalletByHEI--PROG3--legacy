-- All SQL script for creating database and tables
CREATE DATABASE wallet_by_hei;
-- Connect to database
\ c wallet_by_hei 

CREATE TABLE IF NOT EXISTS currency (
    currency_id SERIAL PRIMARY KEY,
    currency_name VARCHAR(50) NOT NULL
);
CREATE TABLE IF NOT EXISTS account (
    account_id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL CHECK (balance >= 0),
    currency_id INT NOT NULL,
    FOREIGN KEY (currency_id) REFERENCES currency(currency_id)
);
CREATE TABLE IF NOT EXISTS "transaction" (
    transaction_id SERIAL PRIMARY KEY,
    transaction_date DATE NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    description TEXT,
    account_id INT NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(account_id)
);