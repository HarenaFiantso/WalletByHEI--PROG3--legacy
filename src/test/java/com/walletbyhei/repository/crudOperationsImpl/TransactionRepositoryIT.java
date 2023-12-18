package com.walletbyhei.repository.crudOperationsImpl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.walletbyhei.database.ConnectionToDb;
import com.walletbyhei.model.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionRepositoryIT {

  @Mock private Connection mockConnection;

  @Mock private PreparedStatement mockStatement;

  @Mock private ResultSet mockResultSet;

  private TransactionRepository transactionRepository;

  @Test
  public void testFindById() throws Exception {
    Long idToFind = 1L;
  }

  /* TODO: Need to implement all method similarly */
}
