CREATE TABLE IF NOT EXISTS transfer
(
    transfer_id           SERIAL PRIMARY KEY,
    debit_transaction_id  INT                                 NOT NULL,
    credit_transaction_id INT                                 NOT NULL,
    transfer_date         TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (debit_transaction_id) REFERENCES "transaction" (transaction_id),
    FOREIGN KEY (credit_transaction_id) REFERENCES "transaction" (transaction_id)
);
