package com.walletbyhei.repository.crudOperationsImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.walletbyhei.database.ConnectionToDb;
import com.walletbyhei.model.ExchangeRate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExchangeRateRepositoryIT {

  private ExchangeRateRepository exchangeRateRepository;
  private Connection mockConnection;
  private PreparedStatement mockStatement;
  private ResultSet mockResultSet;

  @Before
  public void setUp() throws Exception {
    exchangeRateRepository = new ExchangeRateRepository();
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
  public void testFindExchangeRatesByDate() throws Exception {
    LocalDate date = LocalDate.now();

    when(mockResultSet.next()).thenReturn(true).thenReturn(false);
    when(mockResultSet.getTimestamp("date_time"))
        .thenReturn(Timestamp.valueOf(LocalDateTime.now()));
    when(mockResultSet.getDouble("rate")).thenReturn(1.5);
    when(mockResultSet.getInt("weight")).thenReturn(10);

    List<ExchangeRate> expectedExchangeRates = new ArrayList<>();
    ExchangeRate exchangeRate = new ExchangeRate();
    exchangeRate.setDateTime(LocalDateTime.now());
    exchangeRate.setRate(1.5);
    exchangeRate.setWeight(10);
    expectedExchangeRates.add(exchangeRate);

    List<ExchangeRate> result = exchangeRateRepository.findExchangeRatesByDate(date);

    assertEquals(expectedExchangeRates.size(), result.size());
    assertEquals(expectedExchangeRates.get(0).getDateTime(), result.get(0).getDateTime());
    assertEquals(expectedExchangeRates.get(0).getRate(), result.get(0).getRate(), 0.001);
    assertEquals(expectedExchangeRates.get(0).getWeight(), result.get(0).getWeight());
  }
}
