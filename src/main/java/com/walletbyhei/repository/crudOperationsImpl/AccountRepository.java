package com.walletbyhei.repository.crudOperationsImpl;

import com.walletbyhei.dbConnection.ConnectionToDb;
import com.walletbyhei.model.Account;
import com.walletbyhei.model.type.AccountType;
import com.walletbyhei.repository.CrudOperations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountRepository implements CrudOperations<Account> {
  private static final String ACCOUNT_ID_COLUMN = "account_id";
  private static final String ACCOUNT_NAME_COLUMN = "account_name";
  private static final String ACCOUNT_TYPE_COLUMN = "account_type";
  private static final String CURRENCY_ID_COLUMN = "currency_id";

  @Override
  public Account findById(long toFind) {
    Account account = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      connection = ConnectionToDb.getConnection();

      String QUERY = "SELECT * FROM account WHERE account_id = ?";
      statement = connection.prepareStatement(QUERY);
      statement.setLong(1, toFind);

      resultSet = statement.executeQuery();

      if (resultSet.next()) {
        account = new Account();
        account.setAccountId(resultSet.getLong(ACCOUNT_ID_COLUMN));
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to retrieve account : " + e.getMessage());
    } finally {
      closeResources(connection, statement, resultSet);
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
      connection = ConnectionToDb.getConnection();

      String QUERY = "SELECT * FROM account";
      statement = connection.prepareStatement(QUERY);
      resultSet = statement.executeQuery();

      while (resultSet.next()) {
        Account account = new Account();
        account.setAccountId(resultSet.getLong(ACCOUNT_ID_COLUMN));
        account.setAccountName(resultSet.getString(ACCOUNT_NAME_COLUMN));
        account.setAccountType(AccountType.valueOf(resultSet.getString(ACCOUNT_TYPE_COLUMN)));
        account.setCurrencyId(resultSet.getInt(CURRENCY_ID_COLUMN));

        accounts.add(account);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to retrieve account : " + e.getMessage());
    } finally {
      closeResources(connection, statement, resultSet);
    }
    return accounts;
  }

  @Override
  public List<Account> saveAll(List<Account> toSave) {
    return toSave.stream().map(this::save).collect(Collectors.toList());
  }

  @Override
  public Account save(Account toSave) {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    String QUERY;
    boolean isNewAccount = toSave.getAccountId() == null;

    try {
      connection = ConnectionToDb.getConnection();
      if (isNewAccount) {
        QUERY =
            "INSERT INTO account (account_name, account_type, currency_id) VALUES (?, CAST(? AS"
                + " account_type), ?) RETURNING *";
      } else {
        QUERY =
            "UPDATE account SET account_name = ?, account_type = CAST(? AS account_type),"
                + " currency_id = ? WHERE account_id = ? RETURNING *";
      }

      statement = connection.prepareStatement(QUERY);
      statement.setString(1, toSave.getAccountName());
      statement.setString(2, String.valueOf(toSave.getAccountType()));
      statement.setInt(3, toSave.getCurrencyId());

      boolean isResultSet = statement.execute();

      if (isResultSet) {
        resultSet = statement.getResultSet();

        if (resultSet.next()) {
          Account savedAccount = new Account();
          savedAccount.setAccountId(resultSet.getLong(ACCOUNT_ID_COLUMN));
          savedAccount.setAccountType(
              AccountType.valueOf(resultSet.getString(ACCOUNT_TYPE_COLUMN)));
          savedAccount.setCurrencyId(resultSet.getInt(CURRENCY_ID_COLUMN));

          return savedAccount;
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to save account : " + e.getMessage());
    } finally {
      closeResources(connection, statement, resultSet);
    }
    return null;
  }

  @Override
  public Account delete(Account toDelete) {
    Connection connection = null;
    PreparedStatement statement = null;

    try {
      connection = ConnectionToDb.getConnection();
      String QUERY = "DELETE FROM account WHERE account_id = ?";
      statement = connection.prepareStatement(QUERY);
      statement.setLong(1, toDelete.getAccountId());

      int rowsAffected = statement.executeUpdate();

      if (rowsAffected > 0) {
        return toDelete;
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to delete account :" + e.getMessage());
    } finally {
      closeResources(connection, statement, null);
    }
    return null;
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
