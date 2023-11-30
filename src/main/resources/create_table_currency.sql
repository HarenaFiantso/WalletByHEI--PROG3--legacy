-- Create the currency table if not exists
CREATE TABLE currency IF NOT EXISTS (
    currency_id SERIAL PRIMARY KEY,
    currency_name VARCHAR(50) NOT NULL
);
-- Three examples of mock data
INSERT INTO currency (currency_name)
VALUES ('US Dollar'),
    ('Euro'),
    ('British Pound'),
    ('Ariary');