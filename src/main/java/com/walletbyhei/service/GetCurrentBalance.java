package com.walletbyhei.service;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.Transaction;
import com.walletbyhei.model.type.TransactionType;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GetCurrentBalance {

  /* TODO: (Bonus) Create a function that get the balance of the actual account */
  public double getCurrentBalance(Account account) {
    LocalDateTime currentDateTime = LocalDateTime.now();
    List<Transaction> sortedTransactions = sortTransactionsByDateTime(account.getTransactionList());

    double balance = 0;

    for (Transaction transaction : sortedTransactions) {
      if (transaction.getDateTime().isAfter(currentDateTime)) {
        break;
      }
      balance = calculateBalance(transaction, balance);
    }

    return balance;
  }

  private double calculateBalance(Transaction transaction, double currentBalance) {
    if (transaction.getTransactionType() == TransactionType.CREDIT) {
      return currentBalance + transaction.getAmount();
    } else {
      return currentBalance - transaction.getAmount();
    }
  }

  private List<Transaction> sortTransactionsByDateTime(List<Transaction> transactions) {
    return transactions.stream()
        .sorted(Comparator.comparing(Transaction::getDateTime))
        .collect(Collectors.toList());
  }
}
