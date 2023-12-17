CREATE TABLE IF NOT EXISTS category
(
    category_id   SERIAL PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL,
    display_name  VARCHAR(255)
);