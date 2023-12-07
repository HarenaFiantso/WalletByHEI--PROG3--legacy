package com.walletbyhei.repository;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionCrudOperations implements CrudOperations<Transaction, String>{
    private Connection connection;
    @Override
    public void insert(Transaction transaction) {
        String sql = "INSERT INTO transaction(" +
                "transaction_date, " +
                "amount, " +
                "description, " +
                "account_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(transaction.getTransaction_date()));
            statement.setBigDecimal(2, transaction.getAmount());
            statement.setString(3, transaction.getDescription());
            statement.setLong(4, transaction.getAccount_id());
            statement.executeUpdate();
            System.out.println("Entity inserted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Transaction> getAll() {
        List<Transaction> allTransaction = new ArrayList<>();
        String sql = "SELECT * FROM transaction";

        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                allTransaction.add(new Transaction(
                        result.getLong("transaction_id"),
                        result.getDate("transaction_date").toLocalDate(),
                        result.getBigDecimal("amount"),
                        result.getString("description"),
                        result.getLong("account_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allTransaction;
    }

    @Override
    public Transaction getById(int id) {
        String sql = "SELECT * FROM transaction WHERE transaction_id = " + id;

        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(sql);
            if (result.next()) {
                Transaction transaction = new Transaction(
                        result.getLong("transaction_id"),
                        result.getDate("transaction_date").toLocalDate(),
                        result.getBigDecimal("amount"),
                        result.getString("description"),
                        result.getLong("account_id")
                );
                return transaction;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    @Override
    public Transaction updateById(int id, String description) {
        String sql = "UPDATE transaction SET description = ? WHERE transaction_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, description);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getById(id);
    }
}
