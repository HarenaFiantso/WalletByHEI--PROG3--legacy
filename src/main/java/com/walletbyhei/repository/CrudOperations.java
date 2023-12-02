package com.walletbyhei.repository;

import java.util.List;

public interface CrudOperations<T, U> {
  void insert(T entity);

  List<T> getAll();

  T getById(int id);

  T updateById(int id, U entityToUpdate);
}
