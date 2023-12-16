package com.walletbyhei.repository.crudOperationsImpl;

import com.walletbyhei.dbConnection.ConnectionToDb;
import com.walletbyhei.model.Account;
import com.walletbyhei.repository.CrudOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository implements CrudOperations<Account> {
  private static final String ACCOUNT_ID_COLUMN = "account_id";
  private static final String ACCOUNT_NAME_COLUMN = "account_name";
  private static final String CURRENCY_ID_COLUMN = "currency_id";
  private static final String ACCOUNT_TYPE_COLUMN = "account_type";

  @Override
  public Account findById(Account toFind) {
    Account account = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      connection = ConnectionToDb.getConnection();

      String QUERY = "SELECT * FROM account WHERE account_id = ?";
      statement = connection.prepareStatement(QUERY);
      statement.setLong(1, toFind.getAccountId());

      resultSet = statement.executeQuery();

      if (resultSet.next()) {
        account = new Account();
        account.setAccountId(resultSet.getLong(ACCOUNT_ID_COLUMN));
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to retrieve account : " + e.getMessage());
    }
    return account;
  }

  @Override
  public List<Account> findAll() {
    List<Account> accounts = new ArrayList<>();
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      connection =  ConnectionToDb.getConnection();

      String QUERY = "SELECT * FROM account";
      statement = connection.prepareStatement(QUERY);
      resultSet = statement.executeQuery();

      while (resultSet.next()) {
        Account account = new Account();
        account.setAccountId(resultSet.getLong(ACCOUNT_ID_COLUMN));
        account.setAccountName(resultSet.getString(ACCOUNT_NAME_COLUMN));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return accounts;
  }

  @Override
  public List<Account> saveAll(List<Account> toSave) {
    return null;
  }

  @Override
  public Account save(Account toSave) {
    return null;
  }

  @Override
  public Account delete(Account toDelete) {
    return null;
  }

  @Override
  public void closeResources(Connection connection, PreparedStatement statement, ResultSet resultSet) {

  }
}
