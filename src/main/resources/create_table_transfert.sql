-- The "transfer" table is used to record the history of money transfers between different accounts. It is linked to the
-- transactions table to record specific money movements associated with each transfer. It keeps a record of fund
-- movements between accounts by recording the IDs of the transactions involved in debit and credit for each transfer.
-- Thus, it allows tracking the history of money transfers between individual accounts.
CREATE TABLE IF NOT EXISTS transfer (
    transfer_id SERIAL PRIMARY KEY,
    debit_transaction_id INT NOT NULL,
    credit_transaction_id INT NOT NULL,
    transfer_date TIMESTAMP NOT NULL,
    FOREIGN KEY (debit_transaction_id) REFERENCES "transaction"(transaction_id),
    FOREIGN KEY (credit_transaction_id) REFERENCES "transaction"(transaction_id)
);
