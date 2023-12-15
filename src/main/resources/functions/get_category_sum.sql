CREATE OR REPLACE FUNCTION get_category_sum(
    category_account_id INT,
    start_datetime TIMESTAMP,
    end_datetime TIMESTAMP
)
    RETURNS TABLE (transaction_category VARCHAR, total_amount DECIMAL)
    LANGUAGE PLPGSQL
AS
$$
BEGIN
    RETURN QUERY
        SELECT
            "transaction".transaction_category,
            COALESCE(SUM("transaction".amount), 0) AS total_amount
        FROM
            "transaction"
        WHERE
                "transaction".account_id = category_account_id
          AND "transaction".transaction_date_time BETWEEN start_datetime AND end_datetime
        GROUP BY
            "transaction".transaction_category;

    RETURN;
END;
$$;