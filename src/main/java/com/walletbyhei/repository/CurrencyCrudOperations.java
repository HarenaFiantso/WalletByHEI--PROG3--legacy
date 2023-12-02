package com.walletbyhei.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.walletbyhei.model.Currency;
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
        return null;
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
