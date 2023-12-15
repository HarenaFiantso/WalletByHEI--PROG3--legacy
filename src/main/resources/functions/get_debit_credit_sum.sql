-- Create a function to get the sum of amounts for "CREDIT" and "DEBIT" transaction types within a date range
CREATE OR REPLACE FUNCTION get_debit_credit_sum(
    selected_account_id INT,
    start_datetime TIMESTAMP,
    end_datetime TIMESTAMP
) RETURNS TABLE (transaction_type VARCHAR, total_sum DOUBLE PRECISION)
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY
        SELECT
            'CREDIT'::VARCHAR AS transaction_type,
            COALESCE(SUM(t.amount), 0) AS total_sum
        FROM
            "transaction" t
        WHERE
                t.account_id = selected_account_id AND
                t.transaction_type = 'CREDIT' AND
            t.transaction_date_time BETWEEN start_datetime AND end_datetime

        UNION ALL

        SELECT
            'DEBIT'::VARCHAR AS transaction_type,
            COALESCE(SUM(t.amount), 0) AS total_sum
        FROM
            "transaction" t
        WHERE
                t.account_id = selected_account_id AND
                t.transaction_type = 'DEBIT' AND
            t.transaction_date_time BETWEEN start_datetime AND end_datetime;
END;
$$;