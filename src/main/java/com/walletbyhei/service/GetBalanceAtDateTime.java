package com.walletbyhei.service;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.Transaction;
import com.walletbyhei.model.type.TransactionType;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GetBalanceAtDateTime {

  public double getBalanceAtDateTime(Account account, LocalDateTime dateTime) {
    List<Transaction> sortedTransactions = sortTransactionsByDateTime(account.getTransactionList());

    double balance = 0;

    for (Transaction transaction : sortedTransactions) {
      if (transaction.getDateTime().isEqual(dateTime) || transaction.getDateTime().isBefore(dateTime)) {
        balance = calculateBalance(transaction, balance);
      } else {
        break;
      }
    }

    return balance;
  }

  private double calculateBalance(Transaction transaction, double currentBalance) {
    Double transactionAmount = transaction.getAmount();
    if (transactionAmount != null) {
      if (transaction.getTransactionType() == TransactionType.CREDIT) {
        return currentBalance + transactionAmount;
      } else {
        return currentBalance - transactionAmount;
      }
    } else {
      throw new IllegalArgumentException("Transaction amount cannot be null");
    }
  }

  private List<Transaction> sortTransactionsByDateTime(List<Transaction> transactions) {
    return transactions.stream()
        .sorted(Comparator.comparing(Transaction::getDateTime))
        .collect(Collectors.toList());
  }
}

