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

  @Before
  public void setUp() throws Exception {
    categoryRepository = new CategoryRepository();
    mockConnection = mock(Connection.class);
    mockStatement = mock(PreparedStatement.class);
    mockResultSet = mock(ResultSet.class);

    when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);

    doReturn(mockConnection).when(ConnectionToDb.class);
  }

  @After
  public void tearDown() throws Exception {
    verify(mockResultSet, atMostOnce()).close();
    verify(mockStatement, atMostOnce()).close();
    verify(mockConnection, atMostOnce()).close();
  }

  @Test
  public void testFindById() throws Exception {
    long categoryId = 1L;

    when(mockResultSet.next()).thenReturn(true);
    when(mockResultSet.getLong("category_id")).thenReturn(categoryId);

    Category result = categoryRepository.findById(categoryId);

    assertEquals(categoryId, result.getCategoryId().longValue());
  }

  @Test
  public void testFindAll() throws Exception {
    long categoryId1 = 1L;
    long categoryId2 = 2L;

    when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    when(mockResultSet.getLong("category_id")).thenReturn(categoryId1).thenReturn(categoryId2);

    List<Category> expectedCategories = new ArrayList<>();
    Category category1 = new Category();
    category1.setCategoryId(categoryId1);
    expectedCategories.add(category1);

    Category category2 = new Category();
    category2.setCategoryId(categoryId2);
    expectedCategories.add(category2);

    List<Category> result = categoryRepository.findAll();

    assertEquals(expectedCategories.size(), result.size());
    assertEquals(expectedCategories.get(0).getCategoryId(), result.get(0).getCategoryId());
    assertEquals(expectedCategories.get(1).getCategoryId(), result.get(1).getCategoryId());
  }
}
