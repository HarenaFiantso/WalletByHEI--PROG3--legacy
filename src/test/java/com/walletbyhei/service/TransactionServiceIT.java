package com.walletbyhei.service;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.type.TransactionType;
import org.junit.Test;
import static org.junit.Assert.*;

public class TransactionServiceIT {

  @Test
  public void testPerformDebitTransaction() throws IllegalAccessException {
    Account account = new Account();
    account.setBalance(100.0);
    TransactionService transactionService = new TransactionService();
  }

  @Test
  public void testPerformCreditTransaction() throws IllegalAccessException {
    Account account = new Account();
    account.setBalance(100.0);
    TransactionService transactionService = new TransactionService();

    Double creditAmount = 50.0;
  }

  @Test(expected = IllegalAccessException.class)
  public void testPerformDebitTransactionInsufficientFunds() throws IllegalAccessException {
    Account account = new Account();
    account.setBalance(100.0);
    TransactionService transactionService = new TransactionService();

    Double debitAmount = 150.0;
    transactionService.performTransaction(account, TransactionType.DEBIT, debitAmount);
  }
}
