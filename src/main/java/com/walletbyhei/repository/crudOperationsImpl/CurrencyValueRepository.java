package com.walletbyhei.repository.crudOperationsImpl;

import com.walletbyhei.dbConnection.ConnectionToDb;
import com.walletbyhei.model.CurrencyValue;
import java.sql.*;
import java.time.LocalDate;

public class CurrencyValueRepository {
  private static final String CURRENCY_VALUE_ID_COLUMN = "currency_value_id";
  private static final String CURRENCY_VALUE_DATE_COLUMN = "currency_value_date";
  private static final String EXCHANGE_RATE_COLUMN = "exchange_rate";
  private static final String SOURCE_CURRENCY_ID_COLUMN = "source_currency_id";
  private static final String DESTINATION_CURRENCY_ID_COLUMN = "destination_currency_id";

  private static final String SELECT_BY_CURRENCIES =
      "SELECT * FROM currency_value WHERE source_currency_id = ? AND destination_currency_id = ?";
  private static final String SELECT_FOR_DATE =
      "SELECT * FROM currency_value WHERE currency_value_date = ?";

  public CurrencyValue findByCurrencies(int sourceCurrencyId, int destinationCurrencyId) {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    CurrencyValue currencyValue = null;

    try {
      connection = ConnectionToDb.getConnection();
      statement = connection.prepareStatement(SELECT_BY_CURRENCIES);
      statement.setInt(1, sourceCurrencyId);
      statement.setInt(2, destinationCurrencyId);

      resultSet = statement.executeQuery();

      if (resultSet.next()) {
        currencyValue = new CurrencyValue();
        currencyValue.setCurrencyValueId(resultSet.getLong(CURRENCY_VALUE_ID_COLUMN));
        currencyValue.setCurrencyValueDate(
            LocalDate.from(resultSet.getTimestamp(CURRENCY_VALUE_DATE_COLUMN).toLocalDateTime()));
        currencyValue.setExchangeRate(resultSet.getDouble(EXCHANGE_RATE_COLUMN));
        currencyValue.setSourceCurrencyId(resultSet.getInt(SOURCE_CURRENCY_ID_COLUMN));
        currencyValue.setDestinationCurrencyId(resultSet.getInt(DESTINATION_CURRENCY_ID_COLUMN));
      }
    } catch (SQLException e) {
      throw new RuntimeException(
          "Failed to retrieve currency value by currencies : " + e.getMessage());
    }

    /* TODO: Need to implement the closeResources method */

    return currencyValue;
  }

  public CurrencyValue findCurrencyValueForDate(Timestamp transactionDate) {
    CurrencyValue currencyValue = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      connection = ConnectionToDb.getConnection();
      statement = connection.prepareStatement(SELECT_FOR_DATE);
      statement.setTimestamp(1, transactionDate);

      resultSet = statement.executeQuery();

      if (resultSet.next()) {
        currencyValue = new CurrencyValue();
        currencyValue.setCurrencyValueId(resultSet.getLong(CURRENCY_VALUE_ID_COLUMN));
        currencyValue.setCurrencyValueDate(
            resultSet.getDate(CURRENCY_VALUE_DATE_COLUMN).toLocalDate());
        currencyValue.setExchangeRate(resultSet.getDouble(EXCHANGE_RATE_COLUMN));
        currencyValue.setSourceCurrencyId(resultSet.getInt(SOURCE_CURRENCY_ID_COLUMN));
        currencyValue.setDestinationCurrencyId(resultSet.getInt(DESTINATION_CURRENCY_ID_COLUMN));
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to retrieve currency value for date : " + e.getMessage());
    }

    /* TODO: Need to implement the closeResources here !  */

    return currencyValue;
  }
}
