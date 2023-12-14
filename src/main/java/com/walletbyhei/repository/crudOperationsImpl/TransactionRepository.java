package com.walletbyhei.repository.crudOperationsImpl;

import com.walletbyhei.dbConnection.ConnectionToDb;
import com.walletbyhei.model.Transaction;
import com.walletbyhei.repository.CrudOperations;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository implements CrudOperations<Transaction> {

  @Override
  public Integer findById(Transaction toFind) {
    return null;
  }

  @Override
  public List<Transaction> findAll() {
    List<Transaction> transactions = new ArrayList<>();

    Connection connection = ConnectionToDb.getConnection();
    String SELECT_ALL_QUERY = "SELECT * FROM \"transaction\"";

    try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        Transaction transaction = new Transaction();
        transactions.add(transaction);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to retrieve all transactions : " + e.getMessage());
    } finally {
      closeResources(connection, null, null);
    }
    return transactions;
  }

  @Override
  public List<Transaction> saveAll(List<Transaction> toSave) {
    List<Transaction> savedTransactions = new ArrayList<>();

    for (Transaction transaction : toSave) {
      Transaction savedTransaction = this.save(transaction);
      savedTransactions.add(savedTransaction);
    }

    return savedTransactions;
  }

  @Override
  public Transaction save(Transaction toSave) {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      connection = ConnectionToDb.getConnection();
      String SAVE_QUERY;

      if (toSave.getTransactionId() == null) {
        SAVE_QUERY =
            "INSERT INTO \"transaction\" (label, amount, transaction_date_time, account_id,"
                + " transaction_type) VALUES(?, ?, ?, ?, CAST(? AS transaction_type)) RETURNING *";
        statement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, toSave.getLabel());
        statement.setDouble(2, toSave.getAmount());
        statement.setTimestamp(3, Timestamp.valueOf(toSave.getDateTime()));
        statement.setInt(4, toSave.getAccountId());
        statement.setString(5, String.valueOf(toSave.getTransactionType()));
        statement.executeUpdate();

        resultSet = statement.getGeneratedKeys();
      } else {
        SAVE_QUERY =
            "UPDATE \"transaction\" SET label = ?, amount = ?, transaction_date_time = ?,"
                + " account_id = ? , transaction_type = CAST(? AS account_type)WHERE transaction_id"
                + " = ? RETURNING *";
        statement = connection.prepareStatement(SAVE_QUERY);
        statement.setString(1, toSave.getLabel());
        statement.setDouble(2, toSave.getAmount());
        statement.setTimestamp(3, Timestamp.valueOf(toSave.getDateTime()));
        statement.setInt(4, toSave.getAccountId());
        statement.setString(5, String.valueOf(toSave.getTransactionType()));
        statement.setLong(6, toSave.getTransactionId());
        statement.executeUpdate();
      }

      if (resultSet != null && resultSet.next()) {
        toSave.setTransactionId(resultSet.getLong(1));
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to save transaction : " + e.getMessage());
    } finally {
      closeResources(connection, statement, resultSet);
    }
    return toSave;
  }

  @Override
  public Transaction delete(Transaction toDelete) {
    Connection connection = null;
    PreparedStatement statement = null;

    try {
      connection = ConnectionToDb.getConnection();
      String DELETE_QUERY = "DELETE FROM \"transaction\" WHERE transaction_id = ?";
      statement = connection.prepareStatement(DELETE_QUERY);
      statement.setLong(1, toDelete.getTransactionId());
      statement.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException("Failed to delete transaction : " + e.getMessage());
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
