package com.walletbyhei.service;

import com.walletbyhei.model.Category;
import com.walletbyhei.model.CategorySum;
import com.walletbyhei.model.Transaction;
import com.walletbyhei.model.mapper.CategorySumMapper;
import com.walletbyhei.model.type.TransactionType;
import com.walletbyhei.repository.crudOperationsImpl.CategoryRepository;
import com.walletbyhei.repository.crudOperationsImpl.TransactionRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategorySumService {
  Connection connection;
  CategorySumMapper categorySumMapper = new CategorySumMapper();
  TransactionRepository transactionRepository = new TransactionRepository();
  CategoryRepository categoryRepository = new CategoryRepository();

  /* TODO:  Map date from the SQL function of the second question inside a class */
  public CategorySum getCategorySumOne(int accountId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
    CategorySum categorySum = new CategorySum();

    try (CallableStatement callableStatement = connection.prepareCall("{call get_category_sum(?, ?, ?)}")) {
      callableStatement.setInt(1, accountId);
      callableStatement.setTimestamp(2, Timestamp.valueOf(startDateTime));
      callableStatement.setTimestamp(3, Timestamp.valueOf(endDateTime));

      if (callableStatement.execute()) {
        try (ResultSet resultSet = callableStatement.getResultSet()) {
          if (resultSet.next()) {
            categorySum.setRestaurant(resultSet.getBigDecimal("restaurant"));
            categorySum.setSalary(resultSet.getBigDecimal("salary"));
          }
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to map data from the SQL function of the second question : " + e.getMessage());
    }

    return categorySum;
  }

  public CategorySum getCategorySumTwo(int accountId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
    List<Transaction> transactions = Collections.singletonList(transactionRepository.findById((long) accountId));
    List<Category> categories = categoryRepository.findAll();

    List<Transaction> filteredTransactions = transactions.stream()
        .filter(transaction -> transaction.getTransactionDate().after(Timestamp.valueOf(startDateTime))
            && transaction.getTransactionDate().before(Timestamp.valueOf(endDateTime)))
        .toList();

    Map<String, BigDecimal> categorySumMap = initializeCategoryMap(categories);

    for (Transaction transaction : filteredTransactions) {
      BigDecimal amount = BigDecimal.valueOf(transaction.getAmount());
      String categoryName = getCategoryName(categories, transaction.getCategoryId());

      if (transaction.getTransactionType() == TransactionType.CREDIT) {
        categorySumMap.merge(categoryName, amount, BigDecimal::add);
      } else {
        categorySumMap.merge(categoryName, amount.negate(), BigDecimal::add);
      }
    }

    return categorySumMapper.mapToCategorySum(categorySumMap);
  }

  private Map<String, BigDecimal> initializeCategoryMap(List<Category> categories) {
    Map<String, BigDecimal> categorySumMap = new HashMap<>();
    for (Category category : categories) {
      categorySumMap.put(category.getCategoryName(), BigDecimal.ZERO);
    }
    return categorySumMap;
  }

  private String getCategoryName(List<Category> categories, int categoryId) {
    return categories.stream()
        .filter(category -> category.getCategoryId() == categoryId)
        .findFirst()
        .orElse(new Category())
        .getCategoryName();
  }
}
