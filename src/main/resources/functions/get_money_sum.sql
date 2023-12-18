-- Create a function to get the sum of money
CREATE OR REPLACE FUNCTION get_money_sum(
    account_id_param INT,
    start_date_time TIMESTAMP,
    end_date_time TIMESTAMP
)
    RETURNS TABLE
            (
                total_credit DECIMAL,
                total_debit  DECIMAL
            )
AS
$$
DECLARE
    total_credit_amount DECIMAL;
    total_debit_amount  DECIMAL;
BEGIN
    SELECT COALESCE(SUM(amount), 0)
    INTO total_credit_amount
    FROM transaction
    WHERE account_id_param = account_id
      AND transaction_type = 'CREDIT'
      AND transaction_date BETWEEN start_date_time AND end_date_time;

    SELECT COALESCE(SUM(amount), 0)
    INTO total_debit_amount
    FROM transaction
    WHERE account_id_param = account_id
      AND transaction_type = 'DEBIT'
      AND transaction_date BETWEEN start_date_time AND end_date_time;

    RETURN QUERY SELECT total_credit_amount, total_debit_amount;
END;
$$ LANGUAGE PLPGSQL;
