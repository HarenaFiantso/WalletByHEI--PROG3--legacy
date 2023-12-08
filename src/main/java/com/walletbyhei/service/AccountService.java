package com.walletbyhei.service;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.AccountType;
import com.walletbyhei.model.Transaction;
import com.walletbyhei.model.TransactionType;
import com.walletbyhei.repository.AccountRepository;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class AccountService {
  private AccountRepository accountRepository;

  /* TODO: Create a function that can do transaction in an account (DEBIT or CREDIT)✅ */
  public void performTransaction(Account account, double amount, TransactionType transactionType)
      throws SQLException {
    Transaction transaction = new Transaction();
    transaction.setAmount(amount);
    transaction.setTransactionType(transactionType);
    transaction.setDateTime(LocalDateTime.now());

    accountRepository.beginTransaction();

    if (transactionType == TransactionType.DEBIT && account.getAccountType() != AccountType.BANK) {
      if (account.getBalance() >= amount) {
        account.setBalance(account.getBalance() - amount);
        account.getTransactionList().add(transaction);
      } else {
        System.out.println("Insufficient funds for this transaction.");
      }
    } else {
      account.setBalance(account.getBalance() + amount);
      account.getTransactionList().add(transaction);
    }

    /* - Update the account in the database
     *  - Validate the SQL transaction
     *  */
    accountRepository.updateAccount(account);
    accountRepository.commitTransaction();
    System.out.println("Transaction completed successfully.");
  }

  /* TODO: Create a function that can get balance of an account from a specified date ✅ */
  public double getBalanceAtDateTime(Account account, LocalDateTime dateTime) {
    List<Transaction> transactions = account.getTransactionList();
    double balance = 0.0;

    transactions.sort(Comparator.comparing(Transaction::getDateTime));

    for (Transaction transaction : transactions) {
      LocalDateTime transactionDateTime = transaction.getDateTime();

      if (!transactionDateTime.isAfter(dateTime)) {
        if (transaction.getTransactionType() == TransactionType.CREDIT) {
          balance += transaction.getAmount();
        } else {
          balance -= transaction.getAmount();
        }
      } else {
        break;
      }
    }
    return balance;
  }

  /* TODO: (Bonus) Create a function that get the balance of the actual account ✅ */
  public double getCurrentBalance(Account account) {
    LocalDateTime currentDateTime = LocalDateTime.now();
    return accountRepository.getBalanceAtDateTime(account, currentDateTime);
  }

  /* TODO: Create function that can create the balance history of an account in DateTime range */
  public Map<LocalDateTime, Double> getBalanceHistoryInDateTimeRange(
      Account account, LocalDateTime startDateTime, LocalDateTime endDateTime) {
    Map<LocalDateTime, Double> balanceHistory = new HashMap<>();

    LocalDateTime currentDateTime = startDateTime;
    while (!currentDateTime.isAfter(endDateTime)) {
      double balanceAtDateTime = accountRepository.getBalanceAtDateTime(account, currentDateTime);
      balanceHistory.put(currentDateTime, balanceAtDateTime);

      /* Increment the date to pass at the next moment, for example for each minute */
      currentDateTime = currentDateTime.plusMinutes(1);
    }

    return balanceHistory;
  }
}