package com.walletbyhei.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public interface CrudOperations<T> {
  List<T> findAll();

  List<T> saveAll(List<T> toSave);

  T save(T toSave);

  T delete(T toDelete);

  void closeResources(Connection connection, PreparedStatement statement, ResultSet resultSet);
}
