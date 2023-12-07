-- The "currency_value" table records the history of conversion rates between different currencies. In cases where
-- transactions involve currency conversions, this table stores historical exchange rates between two specific
-- currencies at different dates. This enables tracking the evolution of exchange rates over time and applying these
-- rates when converting money from one currency to another. For instance, if you need to convert an amount from euros
-- to ariary, this table could contain historical exchange rates to facilitate this conversion.
CREATE TABLE IF NOT EXISTS currency_value (
    currency_value_id SERIAL PRIMARY KEY,
    source_currency_id INT NOT NULL,
    destination_currency_id INT NOT NULL,
    exchange_rate decimal,
    date_of_effect DATE,
    FOREIGN KEY (source_currency_id) REFERENCES currency(currency_id),
    FOREIGN KEY (destination_currency_id) REFERENCES currency(currency_id)
);
