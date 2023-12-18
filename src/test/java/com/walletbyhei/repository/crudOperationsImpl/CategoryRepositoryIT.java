package com.walletbyhei.repository.crudOperationsImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.walletbyhei.database.ConnectionToDb;
import com.walletbyhei.model.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CategoryRepositoryIT {

  private CategoryRepository categoryRepository;
  private Connection mockConnection;
  private PreparedStatement mockStatement;
  private ResultSet mockResultSet;

  @Test
  public void testFindById() throws Exception {
    long categoryId = 1L;
  }

  @Test
  public void testFindAll() throws Exception {
    long categoryId1 = 1L;
    long categoryId2 = 2L;
  }
}
