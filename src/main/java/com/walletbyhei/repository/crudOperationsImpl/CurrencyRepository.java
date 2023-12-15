package com.walletbyhei.repository.crudOperationsImpl;

import com.walletbyhei.database.ConnectionToDb;
import com.walletbyhei.model.Currency;
import com.walletbyhei.model.type.CurrencyCodeType;
import com.walletbyhei.model.type.CurrencyNameType;
import com.walletbyhei.repository.CrudOperations;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CurrencyRepository implements CrudOperations<Currency> {
  private static final String CURRENCY_ID_COLUMN = "currency_id";
  private static final String CURRENCY_NAME_COLUMN = "currency_name";
  private static final String CURRENCY_CODE_COLUMN = "currency_code";

  private static final String SELECT_BY_ID_QUERY = "SELECT * FROM currency WHERE currency_id = ?";
  private static final String SELECT_ALL_QUERY = "SELECT * FROM currency";
  private static final String INSERT_QUERY =
      "INSERT INTO currency (currency_name, currency_code) VALUES (CAST(? AS currency_name), CAST(?"
          + " AS currency_code)) RETURNING *";
  private static final String UPDATE_QUERY =
      "UPDATE currency SET currency_name = CAST(? AS currency_name), currency_code = CAST(? AS"
          + " currency_code) WHERE currency_id = ? RETURNING *";
  private static final String DELETE_QUERY = "DELETE FROM currency WHERE currency_id = ?";

  @Override
  public Currency findById(Long toFind) {
    Currency currency = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      connection = ConnectionToDb.getConnection();

      statement = connection.prepareStatement(SELECT_BY_ID_QUERY);
      statement.setLong(1, toFind);

      resultSet = statement.executeQuery();

      if (resultSet.next()) {
        currency = new Currency();
        currency.setCurrencyId(resultSet.getLong(CURRENCY_ID_COLUMN));
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to retrieve currency : " + e.getMessage());
    } finally {
      closeResources(connection, statement, resultSet);
    }
    return currency;
  }

  @Override
  public List<Currency> findAll() {
    List<Currency> currencies = new ArrayList<>();
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      connection = ConnectionToDb.getConnection();

      statement = connection.prepareStatement(SELECT_ALL_QUERY);
      resultSet = statement.executeQuery();

      while (resultSet.next()) {
        Currency currency = new Currency();
        currency.setCurrencyId(resultSet.getLong(CURRENCY_ID_COLUMN));
        currency.setCurrencyName(
            CurrencyNameType.valueOf(resultSet.getString(CURRENCY_NAME_COLUMN)));
        currency.setCurrencyCode(
            CurrencyCodeType.valueOf(resultSet.getString(CURRENCY_CODE_COLUMN)));

        currencies.add(currency);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to retrieve currency : " + e.getMessage());
    } finally {
      closeResources(connection, statement, resultSet);
    }
    return currencies;
  }

  @Override
  public List<Currency> saveAll(List<Currency> toSave) {
    return toSave.stream()
        .filter(Objects::nonNull)
        .map(this::save)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  @Override
  public Currency save(Currency toSave) {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    String QUERY;
    boolean isNewCurrency = toSave.getCurrencyId() == null;

    try {
      connection = ConnectionToDb.getConnection();
      if (isNewCurrency) {
        QUERY = INSERT_QUERY;
        statement = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, String.valueOf(toSave.getCurrencyName()));
        statement.setString(2, String.valueOf(toSave.getCurrencyCode()));
      } else {
        QUERY = UPDATE_QUERY;
        statement = connection.prepareStatement(QUERY);
        statement.setString(1, String.valueOf(toSave.getCurrencyName()));
        statement.setString(2, String.valueOf(toSave.getCurrencyCode()));
        statement.setLong(4, toSave.getCurrencyId());
      }

      boolean isResultSet = statement.execute();

      if (isResultSet) {
        resultSet = statement.getResultSet();

        if (resultSet.next()) {
          Currency savedCurrency = new Currency();
          savedCurrency.setCurrencyName(
              CurrencyNameType.valueOf(resultSet.getString(CURRENCY_NAME_COLUMN)));
          savedCurrency.setCurrencyCode(
              CurrencyCodeType.valueOf(resultSet.getString(CURRENCY_CODE_COLUMN)));

          return savedCurrency;
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to save currency : " + e.getMessage());
    } finally {
      closeResources(connection, statement, resultSet);
    }
    return null;
  }

  @Override
  public void delete(Currency toDelete) {
    Connection connection = null;
    PreparedStatement statement = null;

    try {
      connection = ConnectionToDb.getConnection();
      statement = connection.prepareStatement(DELETE_QUERY);
      statement.setLong(1, toDelete.getCurrencyId());

    } catch (SQLException e) {
      throw new RuntimeException("Failed to delete currency :" + e.getMessage());
    } finally {
      closeResources(connection, statement, null);
    }
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
      throw new RuntimeException(e);
    }
  }
}
