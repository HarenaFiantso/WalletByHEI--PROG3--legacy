package com.walletbyhei.repository.crudOperationsImpl;

import com.walletbyhei.dbConnection.ConnectionToDb;
import com.walletbyhei.model.Account;
import com.walletbyhei.model.Category;
import com.walletbyhei.model.Transaction;
import com.walletbyhei.repository.CrudOperations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository implements CrudOperations<Category> {
    @Override
    public Account findById(int toFind) {
        return null;
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();

        Connection connection = ConnectionToDb.getConnection();
        String SELECT_ALL_QUERY = "SELECT * FROM category";

        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Category category = new Category();
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve all category : " + e.getMessage());
        } finally {
            closeResources(connection, null, null);
        }
        return categories;
    }

    @Override
    public List<Category> saveAll(List<Category> toSave) {
        List<Category> savedCategories = new ArrayList<>();

        for (Category category : toSave) {
            Category savedCategory = this.save(category);
            savedCategories.add(savedCategory);
        }

        return savedCategories;
    }

    @Override
    public Category save(Category toSave) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionToDb.getConnection();
            String SAVE_QUERY;

            if (toSave.getCategoryId() == null) {
                SAVE_QUERY =
                        "INSERT INTO category (category_name) VALUES(?) RETURNING *";
                statement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, toSave.getCategoryName());
                statement.executeUpdate();

                resultSet = statement.getGeneratedKeys();
            } else {
                SAVE_QUERY =
                        "UPDATE category "
                                + "SET category_name = ? "
                                + "WHERE category_id = ? RETURNING *";
                statement = connection.prepareStatement(SAVE_QUERY);
                statement.setString(1, toSave.getCategoryName());
                statement.setLong(2, toSave.getCategoryId());
                statement.executeUpdate();
            }

            if (resultSet != null && resultSet.next()) {
                toSave.setCategoryId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save category: " + e.getMessage());
        } finally {
            closeResources(connection, statement, resultSet);
        }
        return toSave;
    }

    @Override
    public Category delete(Category toDelete) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionToDb.getConnection();
            String DELETE_QUERY = "DELETE FROM category WHERE category_id = ?";
            statement = connection.prepareStatement(DELETE_QUERY);
            statement.setLong(1, toDelete.getCategoryId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete category: " + e.getMessage());
        } finally {
            closeResources(connection, statement, null);
        }
        return toDelete;
    }

    @Override
    public void closeResources(Connection connection, PreparedStatement statement, ResultSet resultSet) {
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
            throw new RuntimeException("Failed to close resources: " + e.getMessage());
        }
    }
}
