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
        return null;
    }

    @Override
    public Currency updateById(int id, String currency_name) {
        return null;
    }
}
