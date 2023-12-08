-- Creating database
SELECT 'CREATE DATABASE wallet_by_hei'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'wallet_by_hei')\gexec

\c wallet_by_hei ;

-- Addind "uuid-ospp" extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
SELECT uuid_generate_v4();

-- Creating type for account
DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'account_type') THEN
        CREATE TYPE "account_type" AS ENUM ('BANK', 'CASH', 'MOBILE_MONEY');
    END IF;
END $$;

-- Creating table currency
CREATE TABLE IF NOT EXISTS currency (
    currency_id SERIAL PRIMARY KEY,
    currency_name VARCHAR(50) NOT NULL,
    currency_code VARCHAR(5) NOT NULL
);

-- Creating account table
CREATE TABLE IF NOT EXISTS account (
    account_id uuid DEFAULT uuid_generate_v4 (),
    account_name VARCHAR(255) NOT NULL,
    balance decimal NOT NULL,
    currency_id INT NOT NULL,
    account_type "account_type" NOT NULL,
    FOREIGN KEY (currency_id) REFERENCES currency(currency_id)
);

-- Creating type for transaction
DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'transaction_type') THEN
        CREATE TYPE "transaction_type" AS ENUM ('CREDIT', 'DEBIT');
    END IF;
END $$;

--Creating transaction table
CREATE TABLE IF NOT EXISTS "transaction" (
    transaction_id SERIAL PRIMARY KEY,
    account_id INT NOT NULL,
    label VARCHAR(255) NOT NULL,
    amount decimal NOT NULL,
    transaction_type "transaction_type" NOT NULL,
    transaction_date_time TIMESTAMP NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(account_id)
);

-- Creating the table transfer history
CREATE TABLE IF NOT EXISTS transfer (
    transfer_id SERIAL PRIMARY KEY,
    debit_transaction_id INT NOT NULL,
    credit_transaction_id INT NOT NULL,
    transfer_date TIMESTAMP NOT NULL,
    FOREIGN KEY (debit_transaction_id) REFERENCES "transaction"(transaction_id),
    FOREIGN KEY (credit_transaction_id) REFERENCES "transaction"(transaction_id)
);

-- Creating the table currency_value
CREATE TABLE IF NOT EXISTS currency_value (
    currency_value_id SERIAL PRIMARY KEY,
    source_currency_id INT NOT NULL,
    destination_currency_id INT NOT NULL,
    exchange_rate decimal,
    date_of_effect DATE,
    FOREIGN KEY (source_currency_id) REFERENCES currency(currency_id),
    FOREIGN KEY (destination_currency_id) REFERENCES currency(currency_id)
);
