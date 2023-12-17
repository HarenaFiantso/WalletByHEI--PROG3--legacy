package com.walletbyhei.repository.crudOperationsImpl;

import com.walletbyhei.dbConnection.ConnectionToDb;
import com.walletbyhei.model.Account;
import com.walletbyhei.model.Transaction;
import com.walletbyhei.model.type.AccountType;
import com.walletbyhei.model.type.TransactionType;
import com.walletbyhei.repository.CrudOperations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TransactionRepository implements CrudOperations<Transaction> {
  private static final String TRANSACTION_ID_COLUMN = "transaction_id";
  private static final String TRANSACTION_DATE_COLUMN = "transaction_date";
  private static final String TRANSACTION_TYPE_COLUMN = "transaction_type";
  private static final String AMOUNT_COLUMN = "amount";
  private static final String REASON_COLUMN = "reason";
  private static final String ACCOUNT_ID_COLUMN = "account_id";
  private static final String CATEGORY_ID_COLUMN = "category_id";

  private static final String SELECT_BY_ID_QUERY = "SELECT * FROM transaction WHERE transaction_id = ?";
  private static final String SELECT_ALL_QUERY = "SELECT * FROM transaction";
  private static final String INSERT_QUERY =
      "INSERT INTO transaction (transaction_date, transaction_type, amount, reason, account_id, category_id) VALUES (?, CAST(? AS"
          + " transaction_type), ?, ?, ?, ?) RETURNING *";
  private static final String UPDATE_QUERY =
      "UPDATE account SET transaction_date = ?, transaction_type = CAST(? AS account_type), amount = ?, reason = ?, account_id = ?, category_id = ?"
          + " WHERE transaction_id = ? RETURNING *";
  private static final String DELETE_QUERY = "DELETE FROM transaction WHERE transaction_id = ?";


  @Override
  public Transaction findById(Long toFind) {
    Transaction transaction = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      connection = ConnectionToDb.getConnection();

      statement = connection.prepareStatement(SELECT_BY_ID_QUERY);
      statement.setLong(1, toFind);

      resultSet = statement.executeQuery();

      if (resultSet.next()) {
        transaction = new Transaction();
        transaction.setTransactionId(resultSet.getLong(TRANSACTION_ID_COLUMN));
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to retrieve transaction : " + e.getMessage());
    } finally {
      closeResources(connection, statement, resultSet);
    }
    return transaction;
  }

  @Override
  public List<Transaction> findAll() {
    List<Transaction> transactions = new ArrayList<>();
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      connection = ConnectionToDb.getConnection();

      statement = connection.prepareStatement(SELECT_ALL_QUERY);
      resultSet = statement.executeQuery();

      while (resultSet.next()) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(resultSet.getLong(TRANSACTION_ID_COLUMN));
        transaction.setTransactionDate(resultSet.getTimestamp(TRANSACTION_DATE_COLUMN));
        transaction.setTransactionType(TransactionType.valueOf(resultSet.getString(TRANSACTION_TYPE_COLUMN)));
        transaction.setAmount(resultSet.getDouble(AMOUNT_COLUMN));
        transaction.setReason(resultSet.getString(REASON_COLUMN));
        transaction.setAccountId(resultSet.getInt(ACCOUNT_ID_COLUMN));
        transaction.setCategoryId(resultSet.getInt(CATEGORY_ID_COLUMN));

        transactions.add(transaction);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to retrieve transaction : " + e.getMessage());
    } finally {
      closeResources(connection, statement, resultSet);
    }
    return transactions;
  }

  @Override
  public List<Transaction> saveAll(List<Transaction> toSave) {
    return toSave.stream()
        .filter(Objects::nonNull)
        .map(this::save)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  @Override
  public Transaction save(Transaction toSave) {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    String QUERY;
    boolean isNewTransaction = toSave.getTransactionId() == null;

    try {
      connection = ConnectionToDb.getConnection();
      if (isNewTransaction) {
        QUERY = INSERT_QUERY;
        statement = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
        statement.setTimestamp(1, toSave.getTransactionDate());
        statement.setString(2, String.valueOf(toSave.getTransactionType()));
        statement.setDouble(3, toSave.getAmount());
        statement.setInt(4, toSave.getAccountId());
        statement.setInt(5, toSave.getCategoryId());
      } else {
        QUERY = UPDATE_QUERY;
        statement = connection.prepareStatement(QUERY);
        statement.setTimestamp(1, toSave.getTransactionDate());
        statement.setString(2, String.valueOf(toSave.getTransactionType()));
        statement.setDouble(3, toSave.getAmount());
        statement.setInt(4, toSave.getAccountId());
        statement.setInt(5, toSave.getCategoryId());
        statement.setLong(6, toSave.getTransactionId());
      }

      boolean isResultSet = statement.execute();

      if (isResultSet) {
        resultSet = statement.getResultSet();

        if (resultSet.next()) {
          Transaction savedTransaction = new Transaction();
          savedTransaction.setTransactionDate(resultSet.getTimestamp(TRANSACTION_DATE_COLUMN));
          savedTransaction.setTransactionType(
              TransactionType.valueOf(resultSet.getString(TRANSACTION_TYPE_COLUMN)));
          savedTransaction.setAmount(resultSet.getDouble(AMOUNT_COLUMN));
          savedTransaction.setReason(resultSet.getString(REASON_COLUMN));
          savedTransaction.setAccountId(resultSet.getInt(ACCOUNT_ID_COLUMN));
          savedTransaction.setCategoryId(resultSet.getInt(CATEGORY_ID_COLUMN));

          return savedTransaction;
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to save transaction : " + e.getMessage());
    } finally {
      closeResources(connection, statement, resultSet);
    }
    return null;
  }

  @Override
  public void delete(Transaction toDelete) {
    Connection connection = null;
    PreparedStatement statement = null;

    try {
      connection = ConnectionToDb.getConnection();
      statement = connection.prepareStatement(DELETE_QUERY);
      statement.setLong(1, toDelete.getTransactionId());

    } catch (SQLException e) {
      throw new RuntimeException("Failed to delete transaction :" + e.getMessage());
    } finally {
      closeResources(connection, statement, null);
    }
  }

  @Override
  public void closeResources(Connection connection, PreparedStatement statement, ResultSet resultSet) {
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
