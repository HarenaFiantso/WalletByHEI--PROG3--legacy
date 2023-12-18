package com.walletbyhei.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.Transaction;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceIT {

  private AccountService accountService;

  @Mock
  private Account mockAccount;

  @Before
  public void setUp() {
    accountService = new AccountService();
  }

  @Test
  public void testGetBalanceAtDateTime() {
    LocalDateTime dateTime = LocalDateTime.of(2023, 1, 1, 12, 0);

    List<Transaction> transactions = new ArrayList<>();
    Transaction creditTransaction = new Transaction();
    Transaction debitTransaction = new Transaction();
  }

  @Test
  public void testGetCurrentBalance() {
    List<Transaction> transactions = new ArrayList<>();
    Transaction creditTransaction = new Transaction();
    Transaction debitTransaction = new Transaction();
  }

  @Test
  public void testGetBalanceHistoryInInterval() {
    LocalDateTime startDate = LocalDateTime.of(2023, 1, 1, 0, 0);
    LocalDateTime endDate = LocalDateTime.of(2023, 1, 2, 0, 0);

    List<Transaction> transactions = new ArrayList<>();
    Transaction creditTransaction = new Transaction();
    Transaction debitTransaction = new Transaction();
  }
}
