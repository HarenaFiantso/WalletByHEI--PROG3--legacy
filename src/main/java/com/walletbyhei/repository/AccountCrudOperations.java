package com.walletbyhei.repository;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountCrudOperations implements CrudOperations<Account, String>{
    private Connection connection;
    @Override
    public void insert(Account account) {
        String sql = "INSERT INTO account(" +
                "first_name, " +
                "last_name, " +
                "password, " +
                "balance, " +
                "currency_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, account.getFirst_name());
            statement.setString(3, account.getLast_name());
            statement.setString(6, account.getPassword());
            statement.setBigDecimal(7, account.getBalance());
            statement.setLong(9, account.getCurrency_id());
            statement.executeUpdate();
            System.out.println("Entity inserted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Account> getAll() {
        List<Account> allAccount = new ArrayList<>();
        String sql = "SELECT * FROM account";

        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                allAccount.add(new Account(
                        result.getLong("account_id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("password"),
                        result.getBigDecimal("balance"),
                        result.getLong("currency_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAccount;
    }

    @Override
    public Account getById(int id) {
        String sql = "SELECT * FROM account WHERE account_id = " + id;

        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(sql);
            if (result.next()) {
                Account account = new Account(
                        result.getLong("account_id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("password"),
                        result.getBigDecimal("balance"),
                        result.getLong("currency_id")
                );
                return account;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    @Override
    public Account updateById(int id, String password) {
        String sql = "UPDATE account SET password = ? WHERE account_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, password);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getById(id);
    }
}
