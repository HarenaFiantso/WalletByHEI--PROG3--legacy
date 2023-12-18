package com.walletbyhei.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.walletbyhei.model.Category;
import com.walletbyhei.model.CategorySum;
import com.walletbyhei.model.Transaction;
import com.walletbyhei.model.mapper.CategorySumMapper;
import com.walletbyhei.repository.crudOperationsImpl.CategoryRepository;
import com.walletbyhei.repository.crudOperationsImpl.TransactionRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CategorySumServiceIT {

  private CategorySumService categorySumService;

  @Mock
  private TransactionRepository mockTransactionRepository;

  @Mock
  private CategoryRepository mockCategoryRepository;

  @Before
  public void setUp() {
    categorySumService = new CategorySumService();
    categorySumService.transactionRepository = mockTransactionRepository;
    categorySumService.categoryRepository = mockCategoryRepository;
    categorySumService.categorySumMapper = new CategorySumMapper();
  }

  @Test
  public void testGetCategorySumOne() {
    int accountId = 1;
    LocalDateTime startDateTime = LocalDateTime.now().minusDays(7);
    LocalDateTime endDateTime = LocalDateTime.now();
  }

  @Test
  public void testGetCategorySumTwo() {
    int accountId = 1;
    LocalDateTime startDateTime = LocalDateTime.now().minusDays(7);
    LocalDateTime endDateTime = LocalDateTime.now();

    Transaction transaction1 = new Transaction();
    Transaction transaction2 = new Transaction();
    List<Transaction> transactions = List.of(transaction1, transaction2);
  }
}

