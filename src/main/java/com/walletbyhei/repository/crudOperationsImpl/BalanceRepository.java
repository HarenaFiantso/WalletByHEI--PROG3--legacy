package com.walletbyhei.repository.crudOperationsImpl;

import com.walletbyhei.database.ConnectionToDb;
import com.walletbyhei.model.Balance;
import com.walletbyhei.repository.CrudOperations;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BalanceRepository implements CrudOperations<Balance> {
  private static final String BALANCE_ID_COLUMN = "balance_id";
  private static final String BALANCE_DATE_TIME_COLUMN = "balance_date_time";
  private static final String AMOUNT_COLUMN = "amount";
  private static final String ACCOUNT_ID_COLUMN = "account_id";

  private static final String SELECT_BY_ID_QUERY = "SELECT * FROM balance WHERE balance_id = ?";
  private static final String SELECT_ALL_QUERY = "SELECT * FROM balance";
  private static final String INSERT_QUERY =
      "INSERT INTO balance (balance_date_time, amount, account_id) VALUES (?, ?, ?) RETURNING *";
  private static final String UPDATE_QUERY =
      "UPDATE balance SET balance_date_time = ?, amount = ?, account_id = ? WHERE balance_id = ?"
          + " RETURNING *";
  private static final String DELETE_QUERY = "DELETE FROM balance WHERE balance_id = ?";

  @Override
  public Balance findById(Long toFind) {
    Balance balance = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      connection = ConnectionToDb.getConnection();

      statement = connection.prepareStatement(SELECT_BY_ID_QUERY);
      statement.setLong(1, toFind);

      resultSet = statement.executeQuery();

      if (resultSet.next()) {
        balance = new Balance();
        balance.setBalanceId(resultSet.getLong(BALANCE_ID_COLUMN));
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to retrieve balance : " + e.getMessage());
    } finally {
      closeResources(connection, statement, resultSet);
    }
    return balance;
  }

  @Override
  public List<Balance> findAll() {
    List<Balance> balances = new ArrayList<>();
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      connection = ConnectionToDb.getConnection();

      statement = connection.prepareStatement(SELECT_ALL_QUERY);
      resultSet = statement.executeQuery();

      while (resultSet.next()) {
        Balance balance = new Balance();
        balance.setBalanceId(resultSet.getLong(BALANCE_ID_COLUMN));
        balance.setBalanceDateTime(
            resultSet.getTimestamp(BALANCE_DATE_TIME_COLUMN).toLocalDateTime());
        balance.setAmount(resultSet.getDouble(AMOUNT_COLUMN));
        balance.setAccountId(resultSet.getInt(ACCOUNT_ID_COLUMN));

        balances.add(balance);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to retrieve balance : " + e.getMessage());
    } finally {
      closeResources(connection, statement, resultSet);
    }
    return balances;
  }

  @Override
  public List<Balance> saveAll(List<Balance> toSave) {
    return toSave.stream()
        .filter(Objects::nonNull)
        .map(this::save)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  @Override
  public Balance save(Balance toSave) {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    String QUERY;
    boolean isNewBalance = toSave.getBalanceId() == null;

    try {
      connection = ConnectionToDb.getConnection();
      if (isNewBalance) {
        QUERY = INSERT_QUERY;
        statement = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
        statement.setTimestamp(1, Timestamp.valueOf(toSave.getBalanceDateTime()));
        statement.setDouble(2, toSave.getAmount());
        statement.setInt(3, toSave.getAccountId());
      } else {
        QUERY = UPDATE_QUERY;
        statement = connection.prepareStatement(QUERY);
        statement.setTimestamp(1, Timestamp.valueOf(toSave.getBalanceDateTime()));
        statement.setDouble(2, toSave.getAmount());
        statement.setInt(3, toSave.getAccountId());
        statement.setLong(4, toSave.getBalanceId());
      }

      boolean isResultSet = statement.execute();

      if (isResultSet) {
        resultSet = statement.getResultSet();

        if (resultSet.next()) {
          Balance savedBalance = new Balance();
          savedBalance.setBalanceDateTime(
              resultSet.getTimestamp(BALANCE_DATE_TIME_COLUMN).toLocalDateTime());
          savedBalance.setAmount(resultSet.getDouble(AMOUNT_COLUMN));
          savedBalance.setAccountId(resultSet.getInt(ACCOUNT_ID_COLUMN));

          return savedBalance;
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to save balance : " + e.getMessage());
    } finally {
      closeResources(connection, statement, resultSet);
    }
    return null;
  }

  @Override
  public void delete(Balance toDelete) {
    Connection connection = null;
    PreparedStatement statement = null;

    try {
      connection = ConnectionToDb.getConnection();
      statement = connection.prepareStatement(DELETE_QUERY);
      statement.setLong(1, toDelete.getBalanceId());

    } catch (SQLException e) {
      throw new RuntimeException("Failed to delete balance :" + e.getMessage());
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
