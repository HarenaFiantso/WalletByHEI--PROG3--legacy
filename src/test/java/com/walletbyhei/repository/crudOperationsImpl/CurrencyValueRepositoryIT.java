package com.walletbyhei.repository.crudOperationsImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.walletbyhei.database.ConnectionToDb;
import com.walletbyhei.model.CurrencyValue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CurrencyValueRepositoryIT {

  private CurrencyValueRepository currencyValueRepository;
  private Connection mockConnection;
  private PreparedStatement mockStatement;
  private ResultSet mockResultSet;

  @Before
  public void setUp() throws Exception {
    currencyValueRepository = new CurrencyValueRepository();
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
  public void testFindByCurrencies() throws Exception {
    int sourceCurrencyId = 1;
    int destinationCurrencyId = 2;

    when(mockResultSet.next()).thenReturn(true);
    when(mockResultSet.getLong("currency_value_id")).thenReturn(1L);
    when(mockResultSet.getTimestamp("currency_value_date"))
        .thenReturn(Timestamp.valueOf(LocalDate.now().atStartOfDay()));
    when(mockResultSet.getDouble("exchange_rate")).thenReturn(1.5);
    when(mockResultSet.getInt("source_currency_id")).thenReturn(sourceCurrencyId);
    when(mockResultSet.getInt("destination_currency_id")).thenReturn(destinationCurrencyId);

    CurrencyValue result =
        currencyValueRepository.findByCurrencies(sourceCurrencyId, destinationCurrencyId);

    assertEquals(1L, result.getCurrencyValueId().longValue());
    assertEquals(LocalDate.now(), result.getCurrencyValueDate());
    assertEquals(1.5, result.getExchangeRate(), 0.001);
    assertEquals(sourceCurrencyId, result.getSourceCurrencyId());
    assertEquals(destinationCurrencyId, result.getDestinationCurrencyId());
  }

  @Test
  public void testFindCurrencyValueForDate() throws Exception {
    Timestamp transactionDate = Timestamp.valueOf(LocalDate.now().atStartOfDay());

    when(mockResultSet.next()).thenReturn(true);
    when(mockResultSet.getLong("currency_value_id")).thenReturn(1L);
    when(mockResultSet.getDate("currency_value_date"))
        .thenReturn(java.sql.Date.valueOf(LocalDate.now()));
    when(mockResultSet.getDouble("exchange_rate")).thenReturn(1.5);
    when(mockResultSet.getInt("source_currency_id")).thenReturn(1);
    when(mockResultSet.getInt("destination_currency_id")).thenReturn(2);

    CurrencyValue result = currencyValueRepository.findCurrencyValueForDate(transactionDate);

    assertEquals(1L, result.getCurrencyValueId().longValue());
    assertEquals(LocalDate.now(), result.getCurrencyValueDate());
    assertEquals(1.5, result.getExchangeRate(), 0.001);
    assertEquals(1, result.getSourceCurrencyId());
    assertEquals(2, result.getDestinationCurrencyId());
  }
}
