package com.walletbyhei.repository.crudOperationsImpl;

import com.walletbyhei.dbConnection.ConnectionToDb;
import com.walletbyhei.model.Account;
import com.walletbyhei.model.CurrencyValue;
import com.walletbyhei.repository.CrudOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CurrencyValueRepository implements CrudOperations<CurrencyValue> {

  @Override
  public Account findById(int toFind) {
    return null;
  }

  @Override
  public List<CurrencyValue> findAll() {
    return null;
  }

  @Override
  public List<CurrencyValue> saveAll(List<CurrencyValue> toSave) {
    return null;
  }

  @Override
  public CurrencyValue save(CurrencyValue toSave) {
    return null;
  }

  @Override
  public CurrencyValue delete(CurrencyValue toDelete) {
    return null;
  }

  @Override
  public void closeResources(Connection connection, PreparedStatement statement, ResultSet resultSet) {

  }

  public double getConversionRate(Long sourceCurrencyId, Long destinationCurrencyId, LocalDate date) {
    Connection connection = ConnectionToDb.getConnection();
    String QUERY = "SELECT value " +
        "FROM currency_value " +
        "WHERE currency_value_id = ? AND destination_currency_id = ? AND effective_date <= ? " +
        "ORDER BY effective_date DESC LIMIT 1";

    try (
        PreparedStatement statement = connection.prepareStatement(QUERY)) {
      statement.setLong(1, sourceCurrencyId);
      statement.setLong(2, destinationCurrencyId);
      statement.setDate(3, java.sql.Date.valueOf(date));

      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return resultSet.getDouble("amount");
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to fetch conversion rate: " + e.getMessage());
    }

    throw new RuntimeException("Conversion rate not found for given currencies and date");
  }
}
