package com.walletbyhei.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.CurrencyValue;
import com.walletbyhei.model.Transaction;
import com.walletbyhei.repository.crudOperationsImpl.CurrencyValueRepository;
import com.walletbyhei.repository.crudOperationsImpl.TransactionRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class BalanceServiceIT {

  private BalanceService balanceService;

  @Mock
  private TransactionRepository mockTransactionRepository;

  @Mock
  private CurrencyValueRepository mockCurrencyValueRepository;

  @Before
  public void setUp() {
    balanceService = new BalanceService(mockTransactionRepository, mockCurrencyValueRepository);
  }

  @Test
  public void testCalculateAriaryBalance() {
    Account euroAccount = new Account();
    Account ariaryAccount = new Account();

    LocalDateTime dateTime = LocalDateTime.of(2023, 1, 1, 12, 0);
  }
}

