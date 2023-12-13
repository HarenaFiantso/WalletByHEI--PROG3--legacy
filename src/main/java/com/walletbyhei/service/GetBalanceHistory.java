package com.walletbyhei.service;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.Transaction;
import com.walletbyhei.model.type.TransactionType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GetBalanceHistory {

  public List<Double> getBalanceHistoryInDateTimeInterval(Account account, LocalDateTime startDate, LocalDateTime endDate) {
    List<Transaction> sortedTransactions = sortTransactionsByDateTime(account.getTransactionList());

    List<Double> balanceHistory = new ArrayList<>();
    double currentBalance = 0;

    for (Transaction transaction : sortedTransactions) {
      if (transaction.getDateTime().isAfter(endDate)) {
        break;
      }
      if (transaction.getDateTime().isEqual(startDate) || transaction.getDateTime().isAfter(startDate)) {
        currentBalance = calculateBalance(transaction, currentBalance);
        balanceHistory.add(currentBalance);
      }
    }

    return balanceHistory;
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
