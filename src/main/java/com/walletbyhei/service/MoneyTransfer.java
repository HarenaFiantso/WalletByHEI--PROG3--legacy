package com.walletbyhei.service;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.Transaction;
import com.walletbyhei.model.type.TransactionType;
import com.walletbyhei.repository.crudOperationsImpl.CurrencyValueRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class MoneyTransfer {
  CurrencyValueRepository currencyValueRepository = new CurrencyValueRepository();

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

  public void convertCurrency(Account fromAccount, Account toAccount, double amount, LocalDate date) {
    if (fromAccount.equals(toAccount)) {
      throw new IllegalArgumentException("Cannot transfer money to the same account.");
    }

    if (!fromAccount.getCurrency().equals(toAccount.getCurrency())) {
      double conversionRate = currencyValueRepository.getConversionRate(fromAccount.getCurrency().getCurrencyId(), toAccount.getCurrency().getCurrencyId(), date);

      if (conversionRate == 0) {
        throw new RuntimeException("Conversion rate not found for given currencies and date.");
      }

      double convertedAmount = amount * conversionRate;

      Transaction debitTransaction = createDebitTransaction(fromAccount, amount);
      Transaction creditTransaction = createCreditTransaction(toAccount, convertedAmount);

      fromAccount.getTransactionList().add(debitTransaction);
      toAccount.getTransactionList().add(creditTransaction);

      updateAccountBalance(fromAccount, amount, TransactionType.DEBIT);
      updateAccountBalance(toAccount, convertedAmount, TransactionType.CREDIT);
    } else {
      Transaction debitTransaction = createDebitTransaction(fromAccount, amount);
      Transaction creditTransaction = createCreditTransaction(toAccount, amount);

      fromAccount.getTransactionList().add(debitTransaction);
      toAccount.getTransactionList().add(creditTransaction);

      updateAccountBalance(fromAccount, amount, TransactionType.DEBIT);
      updateAccountBalance(toAccount, amount, TransactionType.CREDIT);
    }
  }
}
