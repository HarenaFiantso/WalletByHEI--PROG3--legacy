package com.walletbyhei.service;

import com.walletbyhei.model.ExchangeRate;
import com.walletbyhei.model.type.OperationType;
import com.walletbyhei.repository.crudOperationsImpl.ExchangeRateRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyServiceIT {

  @Mock
  private ExchangeRateRepository exchangeRateRepository;

  @InjectMocks
  private CurrencyService currencyService;

  @Test
  public void testCalculateWeightedAverageExchangeRate() throws SQLException {
    LocalDate date = LocalDate.now();
    List<ExchangeRate> exchangeRates = new ArrayList<>();
  }

  @Test
  public void testCalculateExchangeRate() throws SQLException, IllegalAccessException {
    LocalDate date = LocalDate.now();
    List<ExchangeRate> exchangeRates = new ArrayList<>();
  }
}

