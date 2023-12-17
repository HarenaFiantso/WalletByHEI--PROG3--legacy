package com.walletbyhei.repository.crudOperationsImpl;

import com.walletbyhei.dbConnection.ConnectionToDb;
import com.walletbyhei.model.TransferHistory;
import com.walletbyhei.repository.CrudOperations;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TransferHistoryRepository implements CrudOperations<TransferHistory> {
  private static final String TRANSFER_HISTORY_ID_COLUMN = "transfer_history_id";
  private static final String TRANSFER_DATE_COLUMN = "transfer_date";
  private static final String DEBIT_TRANSACTION_ID_COLUMN = "debit_transaction_id";
  private static final String CREDIT_TRANSACTION_ID_COLUMN = "credit_transaction_id";

  private static final String SELECT_BY_ID_QUERY =
      "SELECT * FROM transfer_history WHERE transfer_history_id = ?";
  private static final String SELECT_ALL_QUERY = "SELECT * FROM transfer_history";
  private static final String INSERT_QUERY =
      "INSERT INTO transfer_history (transfer_date, debit_transaction_id, credit_transaction_id)"
          + " VALUES (?, ?, ?) RETURNING *";
  private static final String UPDATE_QUERY =
      "UPDATE transfer_history SET transfer_date = ?, debit_transaction_id = ?,"
          + " credit_transaction_id = ? WHERE transfer_history_id = ? RETURNING *";
  private static final String DELETE_QUERY =
      "DELETE FROM transfer_history WHERE transfer_history_id = ?";

  /* ===== Additional Query(ies) ===== */
  private static final String SELECT_BY_TRANSFER_DATE_RANGE = "SELECT * FROM transfer_history WHERE transfer_date BETWEEN ? AND ?";

  @Override
  public TransferHistory findById(Long toFind) {
    TransferHistory transferHistory = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      connection = ConnectionToDb.getConnection();

      statement = connection.prepareStatement(SELECT_BY_ID_QUERY);
      statement.setLong(1, toFind);

      resultSet = statement.executeQuery();

      if (resultSet.next()) {
        transferHistory = new TransferHistory();
        transferHistory.setTransferHistoryId(resultSet.getLong(TRANSFER_HISTORY_ID_COLUMN));
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to retrieve account : " + e.getMessage());
    } finally {
      closeResources(connection, statement, resultSet);
    }
    return transferHistory;
  }

  @Override
  public List<TransferHistory> findAll() {
    List<TransferHistory> transferHistories = new ArrayList<>();
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      connection = ConnectionToDb.getConnection();

      statement = connection.prepareStatement(SELECT_ALL_QUERY);
      resultSet = statement.executeQuery();

      while (resultSet.next()) {
        TransferHistory transferHistory = new TransferHistory();
        transferHistory.setTransferHistoryId(resultSet.getLong(TRANSFER_HISTORY_ID_COLUMN));
        transferHistory.setTransferDate(resultSet.getTimestamp(TRANSFER_DATE_COLUMN));
        transferHistory.setDebitTransactionId(resultSet.getInt(DEBIT_TRANSACTION_ID_COLUMN));
        transferHistory.setCreditTransactionId(resultSet.getInt(CREDIT_TRANSACTION_ID_COLUMN));

        transferHistories.add(transferHistory);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to retrieve transfer history : " + e.getMessage());
    } finally {
      closeResources(connection, statement, resultSet);
    }
    return transferHistories;
  }

  @Override
  public List<TransferHistory> saveAll(List<TransferHistory> toSave) {
    return toSave.stream()
        .filter(Objects::nonNull)
        .map(this::save)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  @Override
  public TransferHistory save(TransferHistory toSave) {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    String QUERY;
    boolean isNewTransferHistory = toSave.getTransferHistoryId() == null;

    try {
      connection = ConnectionToDb.getConnection();
      if (isNewTransferHistory) {
        QUERY = INSERT_QUERY;
        statement = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
        statement.setTimestamp(1, toSave.getTransferDate());
        statement.setInt(2, toSave.getDebitTransactionId());
        statement.setInt(3, toSave.getCreditTransactionId());
      } else {
        QUERY = UPDATE_QUERY;
        statement = connection.prepareStatement(QUERY);
        statement.setTimestamp(1, toSave.getTransferDate());
        statement.setInt(2, toSave.getDebitTransactionId());
        statement.setInt(3, toSave.getCreditTransactionId());
        statement.setLong(4, toSave.getTransferHistoryId());
      }

      boolean isResultSet = statement.execute();

      if (isResultSet) {
        resultSet = statement.getResultSet();

        if (resultSet.next()) {
          TransferHistory savedTransferHistory = new TransferHistory();
          savedTransferHistory.setTransferDate(resultSet.getTimestamp(TRANSFER_DATE_COLUMN));
          savedTransferHistory.setDebitTransactionId(resultSet.getInt(DEBIT_TRANSACTION_ID_COLUMN));
          savedTransferHistory.setCreditTransactionId(resultSet.getInt(CREDIT_TRANSACTION_ID_COLUMN));

          return savedTransferHistory;
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to save transfer history : " + e.getMessage());
    } finally {
      closeResources(connection, statement, resultSet);
    }
    return null;
  }

  @Override
  public void delete(TransferHistory toDelete) {
    Connection connection = null;
    PreparedStatement statement = null;

    try {
      connection = ConnectionToDb.getConnection();
      statement = connection.prepareStatement(DELETE_QUERY);
      statement.setLong(1, toDelete.getTransferHistoryId());

    } catch (SQLException e) {
      throw new RuntimeException("Failed to delete transfer history :" + e.getMessage());
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

  public List<TransferHistory> getTransferHistoryInInterval(LocalDateTime startDate, LocalDateTime endDate) {
    List<TransferHistory> transferHistories = new ArrayList<>();
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      connection = ConnectionToDb.getConnection();

      statement = connection.prepareStatement(SELECT_BY_TRANSFER_DATE_RANGE);
      statement.setTimestamp(1, Timestamp.valueOf(startDate));
      statement.setTimestamp(2, Timestamp.valueOf(endDate));

      resultSet = statement.executeQuery();

      while (resultSet.next()) {
        TransferHistory transferHistory = new TransferHistory();
        transferHistory.setTransferHistoryId(resultSet.getLong(TRANSFER_HISTORY_ID_COLUMN));
        transferHistory.setTransferDate(resultSet.getTimestamp(TRANSFER_DATE_COLUMN));
        transferHistory.setDebitTransactionId(resultSet.getInt(DEBIT_TRANSACTION_ID_COLUMN));
        transferHistory.setCreditTransactionId(resultSet.getInt(CREDIT_TRANSACTION_ID_COLUMN));

        transferHistories.add(transferHistory);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to retrieve transfer history by date range : " + e.getMessage());
    } finally {
      closeResources(connection, statement, resultSet);
    }

    return transferHistories;
  }
}
