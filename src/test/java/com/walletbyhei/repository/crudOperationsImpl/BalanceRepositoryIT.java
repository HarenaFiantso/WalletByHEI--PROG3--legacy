package com.walletbyhei.repository.crudOperationsImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.walletbyhei.database.ConnectionToDb;
import com.walletbyhei.model.Balance;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceRepositoryIT {

  private BalanceRepository balanceRepository;
  private Connection mockConnection;
  private PreparedStatement mockStatement;
  private ResultSet mockResultSet;

  @Before
  public void setUp() throws Exception {
    balanceRepository = new BalanceRepository();
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
    long balanceId = 1L;
    LocalDateTime balanceDateTime = LocalDateTime.now();
    double amount = 100.0;
    int accountId = 123;

    when(mockResultSet.next()).thenReturn(true);
    when(mockResultSet.getLong("balance_id")).thenReturn(balanceId);
    when(mockResultSet.getTimestamp("balance_date_time"))
        .thenReturn(Timestamp.valueOf(balanceDateTime));
    when(mockResultSet.getDouble("amount")).thenReturn(amount);
    when(mockResultSet.getInt("account_id")).thenReturn(accountId);

    Balance result = balanceRepository.findById(balanceId);

    assertEquals(balanceId, result.getBalanceId().longValue());
    assertEquals(balanceDateTime, result.getBalanceDateTime());
    assertEquals(amount, result.getAmount(), 0.001);
    assertEquals(accountId, result.getAccountId());
  }
}
