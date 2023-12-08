package com.walletbyhei.repository;

import com.walletbyhei.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository implements CrudOperations<Transaction>{
    private final Connection connection;
    public TransactionRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Transaction findById(Integer id) {
        String SELECT_BY_ID_QUERY = "SELECT * FROM transaction WHERE transaction_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Transaction();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Transaction> findAll() {
        String SELECT_ALL_QUERY = "SELECT * FROM transaction";
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public List<Transaction> saveAll(List<Transaction> toSave) {
        String SAVE_ALL_QUERY = "INSERT INTO transaction (account_id, label, amount, transaction_date_time, transaction_type) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(SAVE_ALL_QUERY)) {
            for (Transaction transaction : toSave) {
                statement.setLong(1, transaction.getTransactionId());
                statement.setString(2, transaction.getLabel());
                statement.setDouble(3, transaction.getAmount());
                statement.setTimestamp(4, Timestamp.valueOf(transaction.getDateTime()));
                statement.setString(5, transaction.getTransactionType().toString());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toSave;
    }

    @Override
    public Transaction save(Transaction toSave) {
        String SAVE_QUERY = "INSERT INTO transaction (account_id, label, amount, transaction_date_time, transaction_type) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, toSave.getTransactionId());
            statement.setString(2, toSave.getLabel());
            statement.setDouble(3, toSave.getAmount());
            statement.setTimestamp(4, Timestamp.valueOf(toSave.getDateTime()));
            statement.setString(5, toSave.getTransactionType().toString());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating transaction failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    toSave.setTransactionId(generatedKeys.getLong(1));
                    return toSave;
                } else {
                    throw new SQLException("Creating transaction failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Transaction delete(Transaction toDelete) {
        String query = "DELETE FROM transaction WHERE account_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, toDelete.getTransactionId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting transaction failed, no rows affected.");
            }
            return toDelete;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
