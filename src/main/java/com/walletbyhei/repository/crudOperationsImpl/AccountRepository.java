package com.walletbyhei.repository.crudOperationsImpl;

import com.walletbyhei.dbConnection.ConnectionToDb;
import com.walletbyhei.model.Account;
import com.walletbyhei.repository.CrudOperations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository implements CrudOperations<Account> {

  @Override
  public Account findById(Integer id) {
    return null;
  }

  @Override
  public List<Account> findAll() throws SQLException {
    List<Account> accounts = new ArrayList<>();

    Connection connection = ConnectionToDb.getConnection();
    String SELECT_ALL_QUERY = "SELECT * FROM account";

    try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        Account account = new Account();
        accounts.add(account);
      }
    }
    return accounts;
  }

  @Override
  public List<Account> saveAll(List<Account> toSave) {
    List<Account> savedAccounts = new ArrayList<>();

    for (Account account : toSave) {
      Account savedAccount = this.save(account);
      savedAccounts.add(savedAccount);
    }

    return savedAccounts;
  }

  @Override
  public Account save(Account toSave) {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    try {
      connection = ConnectionToDb.getConnection();
      String QUERY;

      if (toSave.getAccountId() == null) {
        QUERY = "INSERT INTO account (account_type, currency_id) VALUES (?, ?)";
        statement = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, String.valueOf(toSave.getAccountType()));
        statement.setString(2, String.valueOf(toSave.getCurrencyId()));
        statement.executeUpdate();

        resultSet = statement.getGeneratedKeys();
      } else {
        QUERY = "UPDATE account SET account_type = ?, currency_id = ? WHERE account_id = ?";
        statement = connection.prepareStatement(QUERY);
        statement.setString(1, String.valueOf(toSave.getAccountType()));
        statement.setString(2, String.valueOf(toSave.getCurrencyId()));
        statement.setLong(3, toSave.getAccountId());
        statement.executeUpdate();
      }

      if (resultSet != null && resultSet.next()) {
        toSave.setAccountId(resultSet.getLong(1));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Failed to save account: " + e.getMessage());
    } finally {
      closeResources(connection, statement, resultSet);
    }
    return toSave;
  }

  @Override
  public Account delete(Account toDelete) {
    return null;
  }
}
