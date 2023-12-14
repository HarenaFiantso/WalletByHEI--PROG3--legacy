package com.walletbyhei.repository;

import java.sql.SQLException;
import java.util.List;

public interface CrudOperations<T> {
  T findById(Integer id);

  List<T> findAll() throws SQLException;

  List<T> saveAll(List<T> toSave);

  T save(T toSave);

  T delete(T toDelete);
}
