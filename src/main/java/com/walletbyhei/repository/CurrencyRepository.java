package com.walletbyhei.repository;

import com.walletbyhei.model.Currency;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencyRepository implements CrudOperations<Currency> {

  private final Connection connection;

  public CurrencyRepository(Connection connection) {
    this.connection = connection;
  }

  @Override
  public Currency findById(Integer id) {
    String SELECT_BY_ID_QUERY = "SELECT * FROM currency WHERE currency_id = ?";
    try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
      statement.setLong(1, id);
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        return new Currency();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public List<Currency> findAll() {
    String SELECT_ALL_QUERY = "SELECT * FROM currency";
    List<Currency> currencies = new ArrayList<>();
    try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        Currency currency = new Currency();
        currencies.add(currency);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return currencies;
  }

  @Override
  public List<Currency> saveAll(List<Currency> toSave) {
    String SAVE_ALL_QUERY = "INSERT INTO currency (currency_name, currency_code) VALUES (?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(SAVE_ALL_QUERY)) {
      for (Currency currency : toSave) {
        statement.setString(1, currency.getCurrencyName());
        statement.setString(2, currency.getCurrencyCode());
        statement.addBatch();
      }
      statement.executeBatch();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return toSave;
  }

  @Override
  public Currency save(Currency toSave) {
    String SAVE_QUERY = "INSERT INTO currency (currency_name, currency_code) VALUES (?, ?)";
    try (PreparedStatement statement =
        connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, toSave.getCurrencyName());
      statement.setString(2, toSave.getCurrencyCode());
      int affectedRows = statement.executeUpdate();
      if (affectedRows == 0) {
        throw new SQLException("Creating currency failed, no rows affected.");
      }
      try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          toSave.setCurrencyId(generatedKeys.getLong(1));
          return toSave;
        } else {
          throw new SQLException("Creating currency failed, no ID obtained.");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public Currency delete(Currency toDelete) {
    String query = "DELETE FROM currency WHERE currency_id = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setLong(1, toDelete.getCurrencyId());
      int affectedRows = statement.executeUpdate();
      if (affectedRows == 0) {
        throw new SQLException("Deleting currency failed, no rows affected.");
      }
      return toDelete;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
