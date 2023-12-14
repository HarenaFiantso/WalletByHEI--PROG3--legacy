package com.walletbyhei.repository.crudOperationsImpl;

import com.walletbyhei.dbConnection.ConnectionToDb;
import com.walletbyhei.model.Currency;
import com.walletbyhei.repository.CrudOperations;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencyRepository implements CrudOperations<Currency> {
  @Override
  public Integer findById(Currency toFind) {
    return null;
  }

  @Override
  public List<Currency> findAll() {
    List<Currency> currencies = new ArrayList<>();

    Connection connection = ConnectionToDb.getConnection();
    String SELECT_ALL_QUERY = "SELECT * FROM currency";

    try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        Currency currency = new Currency();
        currencies.add(currency);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to retrieve all currencies : " + e.getMessage());
    } finally {
      closeResources(connection, null, null);
    }
    return currencies;
  }

  @Override
  public List<Currency> saveAll(List<Currency> toSave) {
    List<Currency> savedCurrencies = new ArrayList<>();

    for (Currency currency : toSave) {
      Currency savedCurrency = this.save(currency);
      savedCurrencies.add(savedCurrency);
    }

    return savedCurrencies;
  }

  @Override
  public Currency save(Currency toSave) {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      connection = ConnectionToDb.getConnection();
      String SAVE_QUERY;

      if (toSave.getCurrencyId() == null) {
        SAVE_QUERY =
            "INSERT INTO currency (currency_name, currency_code) " + "VALUES(?, ?) RETURNING *";
        statement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, toSave.getCurrencyName());
        statement.setString(2, toSave.getCurrencyCode());
        statement.executeUpdate();

        resultSet = statement.getGeneratedKeys();
      } else {
        SAVE_QUERY =
            "UPDATE currency "
                + "SET currency_name = ?, currency_code = ? "
                + "WHERE currency_id = ? RETURNING *";
        statement = connection.prepareStatement(SAVE_QUERY);
        statement.setString(1, toSave.getCurrencyName());
        statement.setString(2, toSave.getCurrencyCode());
        statement.setLong(3, toSave.getCurrencyId());
        statement.executeUpdate();
      }

      if (resultSet != null && resultSet.next()) {
        toSave.setCurrencyId(resultSet.getLong(1));
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to save currency: " + e.getMessage());
    } finally {
      closeResources(connection, statement, resultSet);
    }
    return toSave;
  }

  @Override
  public Currency delete(Currency toDelete) {
    Connection connection = null;
    PreparedStatement statement = null;

    try {
      connection = ConnectionToDb.getConnection();
      String DELETE_QUERY = "DELETE FROM currency WHERE currency_id = ?";
      statement = connection.prepareStatement(DELETE_QUERY);
      statement.setLong(1, toDelete.getCurrencyId());
      statement.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException("Failed to delete currency: " + e.getMessage());
    } finally {
      closeResources(connection, statement, null);
    }
    return toDelete;
  }

  @Override
  public void closeResources(
      Connection connection, PreparedStatement statement, ResultSet resultSet) {
    try {
      if (resultSet != null) {
        resultSet.close();
      }
      if (statement != null) {
        statement.close();
      }
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to close resources: " + e.getMessage());
    }
  }
}
