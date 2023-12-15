CREATE OR REPLACE FUNCTION get_debit_credit_sum(
    selected_account_id INT,
    start_datetime TIMESTAMP,
    end_datetime TIMESTAMP
)
    RETURNS TABLE (transaction_type VARCHAR, total_sum DECIMAL)
    LANGUAGE PLPGSQL
AS
$$
BEGIN
    RETURN QUERY
        SELECT
            'CREDIT'::VARCHAR AS transaction_type,
            COALESCE(SUM(t.amount), 0) AS total_sum
        FROM
            transaction t
        WHERE
                t.account_id = selected_account_id
          AND t.transaction_type = 'CREDIT'
          AND t.transaction_date_time BETWEEN start_datetime AND end_datetime;

    RETURN QUERY
        SELECT
            'DEBIT'::VARCHAR AS transaction_type,
            COALESCE(SUM(t.amount), 0) AS total_sum
        FROM
            transaction t
        WHERE
                t.account_id = selected_account_id
          AND t.transaction_type = 'DEBIT'
          AND t.transaction_date_time BETWEEN start_datetime AND end_datetime;
END;
$$;