package com.walletbyhei.repository;

import java.sql.*;

import com.walletbyhei.model.Currency;

import java.util.ArrayList;
import java.util.List;

public class CurrencyCrudOperations implements CrudOperations<Currency, String>{
    private Connection connection;

    public CurrencyCrudOperations(Connection connection){
        this.connection = connection;
    }

    @Override
    public void insert(Currency currency) {
        String sql = "INSERT INTO currency(currency_name) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, currency.getCurrency_name());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Currency> getAll() {
        List<Currency> allCurrency = new ArrayList<>();
        String sql = "SELECT * FROM currency";

        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                allCurrency.add(new Currency(
                        result.getInt("currency_id"),
                        result.getString("currency_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCurrency;
    }

    @Override
    public Currency getById(int id) {
        String sql = "SELECT * FROM currency WHERE currency_id = " + id;

        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(sql);
            if (result.next()) {
                Currency currency = new Currency(
                        result.getInt("currency_id"),
                        result.getString("currency_name")
                );
                return currency;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    @Override
    public Currency updateById(int id, String currency_name) {
        String sql = "UPDATE currency SET currency_name = ? WHERE currency_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, currency_name);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getById(id);
    }
}
