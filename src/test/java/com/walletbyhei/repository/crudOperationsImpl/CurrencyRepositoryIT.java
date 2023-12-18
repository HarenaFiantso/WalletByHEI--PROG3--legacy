package com.walletbyhei.repository.crudOperationsImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.walletbyhei.database.ConnectionToDb;
import com.walletbyhei.model.Currency;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CurrencyRepositoryIT {

  private CurrencyRepository currencyRepository;
  private Connection mockConnection;
  private PreparedStatement mockStatement;
  private ResultSet mockResultSet;

  @Before
  public void setUp() throws Exception {
    currencyRepository = new CurrencyRepository();
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
    long currencyId = 1L;

    when(mockResultSet.next()).thenReturn(true);
    when(mockResultSet.getLong("currency_id")).thenReturn(currencyId);

    Currency result = currencyRepository.findById(currencyId);

    assertEquals(currencyId, result.getCurrencyId().longValue());
  }

  @Test
  public void testFindAll() throws Exception {
    long currencyId1 = 1L;
    long currencyId2 = 2L;

    when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    when(mockResultSet.getLong("currency_id")).thenReturn(currencyId1).thenReturn(currencyId2);

    List<Currency> expectedCurrencies = new ArrayList<>();
    Currency currency1 = new Currency();
    currency1.setCurrencyId(currencyId1);
    expectedCurrencies.add(currency1);

    Currency currency2 = new Currency();
    currency2.setCurrencyId(currencyId2);
    expectedCurrencies.add(currency2);

    List<Currency> result = currencyRepository.findAll();

    assertEquals(expectedCurrencies.size(), result.size());
    assertEquals(expectedCurrencies.get(0).getCurrencyId(), result.get(0).getCurrencyId());
    assertEquals(expectedCurrencies.get(1).getCurrencyId(), result.get(1).getCurrencyId());
  }
}
