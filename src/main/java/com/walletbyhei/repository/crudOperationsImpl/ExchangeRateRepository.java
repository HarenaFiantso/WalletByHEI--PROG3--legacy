package com.walletbyhei.repository.crudOperationsImpl;

import com.walletbyhei.dbConnection.ConnectionToDb;
import com.walletbyhei.model.ExchangeRate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateRepository {
  private final Connection connection = ConnectionToDb.getConnection();
  private static final String SELECT_EXCHANGE_RATES_BY_DATE =
      "SELECT date_time, rate, weight FROM exchange_rate WHERE DATE(date_time) = ?";

  public List<ExchangeRate> findExchangeRatesByDate(LocalDate date) throws SQLException {
    List<ExchangeRate> exchangeRates = new ArrayList<>();

    try (PreparedStatement statement = connection.prepareStatement(SELECT_EXCHANGE_RATES_BY_DATE)) {
      statement.setString(1, date.toString());

      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {

          /* TODO: Put column name in variables */
          LocalDateTime dateTime = resultSet.getTimestamp("date_time").toLocalDateTime();
          double rate = resultSet.getDouble("rate");
          int weight = resultSet.getInt("weight");

          ExchangeRate exchangeRate = new ExchangeRate();

          exchangeRate.setDateTime(dateTime);
          exchangeRate.setRate(rate);
          exchangeRate.setWeight(weight);

          exchangeRates.add(exchangeRate);
        }
      }
    }

    return exchangeRates;
  }
}
