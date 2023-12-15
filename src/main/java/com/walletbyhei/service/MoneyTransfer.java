package com.walletbyhei.service;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.Transaction;
import com.walletbyhei.model.type.TransactionType;

import java.time.LocalDateTime;

public class MoneyTransfer {

  public void transferMoneyBetweenAccounts(Account fromAccount, Account toAccount, double amount) {
    if (fromAccount.equals(toAccount)) {
      throw new IllegalArgumentException("Cannot transfer money to the same account.");
    }

    Transaction debitTransaction = createDebitTransaction(fromAccount, amount);
    Transaction creditTransaction = createCreditTransaction(toAccount, amount);

    fromAccount.getTransactionList().add(debitTransaction);
    toAccount.getTransactionList().add(creditTransaction);

    updateAccountBalance(fromAccount, amount, TransactionType.DEBIT);
    updateAccountBalance(toAccount, amount, TransactionType.CREDIT);
  }

  public Transaction createDebitTransaction(Account account, double amount) {
    Transaction transaction = new Transaction();
    transaction.setAmount(amount);
    transaction.setTransactionType(TransactionType.DEBIT);
    transaction.setDateTime(LocalDateTime.now());
    transaction.setAccount(account);

    return transaction;
  }

  public Transaction createCreditTransaction(Account account, double amount) {
    Transaction transaction = new Transaction();
    transaction.setAmount(amount);
    transaction.setTransactionType(TransactionType.CREDIT);
    transaction.setDateTime(LocalDateTime.now());
    transaction.setAccount(account);

    return transaction;
  }

  public void updateAccountBalance(Account account, double amount, TransactionType transactionType) {
    double currentBalance = account.getBalance().getAmount();
    if (transactionType == TransactionType.DEBIT) {
      currentBalance -= amount;
    } else {
      currentBalance += amount;
    }
    account.getBalance().setAmount(currentBalance);
  }
}
