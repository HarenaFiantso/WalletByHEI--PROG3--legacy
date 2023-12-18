package com.walletbyhei.repository.crudOperationsImpl;

import com.walletbyhei.database.ConnectionToDb;
import com.walletbyhei.model.Category;
import com.walletbyhei.model.type.CategoryType;
import com.walletbyhei.repository.CrudOperations;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CategoryRepository implements CrudOperations<Category> {
  private static final String CATEGORY_ID_COLUMN = "category_id";
  private static final String CATEGORY_NAME_COLUMN = "category_name";
  private static final String CATEGORY_TYPE_COLUMN = "category_type";

  private static final String SELECT_BY_ID_QUERY = "SELECT * FROM category WHERE category_id = ?";
  private static final String SELECT_ALL_QUERY = "SELECT * FROM category";
  private static final String INSERT_QUERY =
      "INSERT INTO category (category_name, category_type) VALUES (?, CAST(? AS"
          + " category_type)) RETURNING *";
  private static final String UPDATE_QUERY =
      "UPDATE category SET category_name = ?, category_type = CAST(? AS category_type)"
          + " WHERE category_id = ? RETURNING *";
  private static final String DELETE_QUERY = "DELETE FROM category WHERE category_id = ?";

  @Override
  public Category findById(Long toFind) {
    Category category = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      connection = ConnectionToDb.getConnection();

      statement = connection.prepareStatement(SELECT_BY_ID_QUERY);
      statement.setLong(1, toFind);

      resultSet = statement.executeQuery();

      if (resultSet.next()) {
        category = new Category();
        category.setCategoryId(resultSet.getLong(CATEGORY_ID_COLUMN));
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to retrieve category : " + e.getMessage());
    } finally {
      closeResources(connection, statement, resultSet);
    }
    return category;
  }

  @Override
  public List<Category> findAll() {
    List<Category> categories = new ArrayList<>();
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      connection = ConnectionToDb.getConnection();

      statement = connection.prepareStatement(SELECT_ALL_QUERY);
      resultSet = statement.executeQuery();

      while (resultSet.next()) {
        Category category = new Category();
        category.setCategoryId(resultSet.getLong(CATEGORY_ID_COLUMN));
        category.setCategoryName(resultSet.getString(CATEGORY_NAME_COLUMN));
        category.setCategoryType(CategoryType.valueOf(resultSet.getString(CATEGORY_TYPE_COLUMN)));

        categories.add(category);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to retrieve category : " + e.getMessage());
    } finally {
      closeResources(connection, statement, resultSet);
    }
    return categories;
  }

  @Override
  public List<Category> saveAll(List<Category> toSave) {
    return toSave.stream()
        .filter(Objects::nonNull)
        .map(this::save)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  @Override
  public Category save(Category toSave) {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    String QUERY;
    boolean isNewCategory = toSave.getCategoryId() == null;

    try {
      connection = ConnectionToDb.getConnection();
      if (isNewCategory) {
        QUERY = INSERT_QUERY;
        statement = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, toSave.getCategoryName());
        statement.setString(2, String.valueOf(toSave.getCategoryType()));
      } else {
        QUERY = UPDATE_QUERY;
        statement = connection.prepareStatement(QUERY);
        statement.setString(1, toSave.getCategoryName());
        statement.setString(2, String.valueOf(toSave.getCategoryType()));
        statement.setLong(4, toSave.getCategoryId());
      }

      boolean isResultSet = statement.execute();

      if (isResultSet) {
        resultSet = statement.getResultSet();

        if (resultSet.next()) {
          Category savedCategory = new Category();
          savedCategory.setCategoryName(resultSet.getString(CATEGORY_NAME_COLUMN));
          savedCategory.setCategoryType(
              CategoryType.valueOf(resultSet.getString(CATEGORY_TYPE_COLUMN)));

          return savedCategory;
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to save category : " + e.getMessage());
    } finally {
      closeResources(connection, statement, resultSet);
    }
    return null;
  }

  @Override
  public void delete(Category toDelete) {
    Connection connection = null;
    PreparedStatement statement = null;

    try {
      connection = ConnectionToDb.getConnection();
      statement = connection.prepareStatement(DELETE_QUERY);
      statement.setLong(1, toDelete.getCategoryId());

    } catch (SQLException e) {
      throw new RuntimeException("Failed to delete category :" + e.getMessage());
    } finally {
      closeResources(connection, statement, null);
    }
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
