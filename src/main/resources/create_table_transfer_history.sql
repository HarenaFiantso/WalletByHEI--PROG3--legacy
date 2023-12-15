CREATE TABLE IF NOT EXISTS transfer_history
(
    transfer_history      SERIAL PRIMARY KEY,
    transfer_date         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    debit_transaction_id  INT       NOT NULL,
    credit_transaction_id INT       NOT NULL,
    FOREIGN KEY (debit_transaction_id) REFERENCES transaction (transaction_id),
    FOREIGN KEY (credit_transaction_id) REFERENCES transaction (transaction_id)
);