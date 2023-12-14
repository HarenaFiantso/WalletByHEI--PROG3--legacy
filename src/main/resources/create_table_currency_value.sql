CREATE TABLE IF NOT EXISTS currency_value
(
    currency_value_id       SERIAL PRIMARY KEY,
    source_currency_id      INT NOT NULL,
    destination_currency_id INT NOT NULL,
    value                   DOUBLE PRECISION NOT NULL,
    effective_date          DATE NOT NULL ,
    FOREIGN KEY (source_currency_id) REFERENCES currency (currency_id),
    FOREIGN KEY (destination_currency_id) REFERENCES currency (currency_id)
);
