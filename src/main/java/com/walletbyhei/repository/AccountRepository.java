package com.walletbyhei.repository;

import com.walletbyhei.model.Account;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository implements CrudOperations<Account> {
  private final Connection connection;

  public AccountRepository(Connection connection) {
    this.connection = connection;
  }

  @Override
  public Account findById(Integer id) {
    String SELECT_BY_ID_QUERY = "SELECT * FROM account WHERE account_id = ?";
    try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
      statement.setLong(1, id);
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        return new Account();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public List<Account> findAll() {
    String SELECT_ALL_QUERY = "SELECT * FROM account";
    List<Account> accounts = new ArrayList<>();
    try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        Account account = new Account();
        accounts.add(account);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return accounts;
  }

  @Override
  public List<Account> saveAll(List<Account> toSave) {
    String SAVE_ALL_QUERY =
        "INSERT INTO account (account_name, balance, currency_id, account_type) VALUES (?, ?, ?,"
            + " ?)";
    try (PreparedStatement statement = connection.prepareStatement(SAVE_ALL_QUERY)) {
      for (Account account : toSave) {
        statement.setString(1, account.getAccountName());
        statement.setDouble(2, account.getBalance());
        statement.setInt(3, account.getCurrencyId());
        statement.setString(4, account.getAccountType().toString());
        statement.addBatch();
      }
      statement.executeBatch();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return toSave;
  }

  @Override
  public Account save(Account toSave) {
    String SAVE_QUERY =
        "INSERT INTO account (account_name, balance, currency_id, account_type) VALUES (?, ?, ?,"
            + " ?)";
    try (PreparedStatement statement =
        connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, toSave.getAccountName());
      statement.setDouble(2, toSave.getBalance());
      statement.setInt(3, toSave.getCurrencyId());
      statement.setString(4, toSave.getAccountType().toString());
      int affectedRows = statement.executeUpdate();
      if (affectedRows == 0) {
        throw new SQLException("Creating account failed, no rows affected.");
      }
      try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          toSave.setAccountId(generatedKeys.getLong(1));
          return toSave;
        } else {
          throw new SQLException("Creating account failed, no ID obtained.");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public Account delete(Account toDelete) {
    String query = "DELETE FROM account WHERE account_id = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setLong(1, toDelete.getAccountId());
      int affectedRows = statement.executeUpdate();
      if (affectedRows == 0) {
        throw new SQLException("Deleting account failed, no rows affected.");
      }
      return toDelete;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
