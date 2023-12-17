package com.walletbyhei.service;

import com.walletbyhei.model.*;
import com.walletbyhei.model.type.TransactionType;
import com.walletbyhei.repository.crudOperationsImpl.CurrencyRepository;
import com.walletbyhei.repository.crudOperationsImpl.CurrencyValueRepository;
import com.walletbyhei.repository.crudOperationsImpl.TransactionRepository;
import com.walletbyhei.repository.crudOperationsImpl.TransferHistoryRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TransferService {
  private final CurrencyRepository currencyRepository = new CurrencyRepository();
  private final CurrencyValueRepository currencyValueRepository = new CurrencyValueRepository();
  private final TransactionRepository transactionRepository = new TransactionRepository();
  private final TransferHistoryRepository transferHistoryRepository =
      new TransferHistoryRepository();

  /* TODO: Create a function to transfer money between two accounts (first case) */
  public void transferMoneyFirstCase(Account debitAccount, Account creditAccount, Double amount)
      throws IllegalAccessException {
    if (debitAccount.getAccountId().equals(creditAccount.getAccountId())) {
      throw new IllegalAccessException("An account couldn't done transfer for himself");
    }

    Transaction debitTransaction = createTransaction(debitAccount, amount, TransactionType.DEBIT);
    Transaction creditTransaction =
        createTransaction(creditAccount, amount, TransactionType.CREDIT);

    /* TODO: Save transactions in the database */
    assert debitTransaction != null;
    assert creditTransaction != null;
    transactionRepository.save(debitTransaction);
    transactionRepository.save(creditTransaction);

    TransferHistory transferHistory = new TransferHistory();
    transferHistory.setTransferDate(Timestamp.valueOf(LocalDateTime.now()));
    transferHistory.setDebitTransactionId(Math.toIntExact(debitTransaction.getTransactionId()));
    transferHistory.setCreditTransactionId(Math.toIntExact(creditTransaction.getTransactionId()));

    /* TODO: Save transferHistory in the database */
    transferHistoryRepository.save(transferHistory);
  }

  /* TODO: Create a function to transfer money between two accounts (second case | First part) */
  public void transferMoneySecondCaseFirstPart(
      Account debitAccount, Account creditAccount, Double amount) {
    if (!isDifferentCurrency(debitAccount, creditAccount)) {
      throw new IllegalArgumentException("Accounts should be different (EUR -> MGA) ");
    }

    Currency sourceCurrency = getCurrencyById(debitAccount.getCurrencyId());
    Currency destinationCurrency = getCurrencyById(creditAccount.getCurrencyId());

    if (!canConvertCurrencies()) {
      throw new IllegalArgumentException("Couldn't do currency conversion (EUR -> MGA)");
    }

    Double exchangeRate = getExchangeRate(sourceCurrency, destinationCurrency);
    Double convertedAmount = amount * exchangeRate;
    Transaction debitTransaction =
        createTransaction(debitAccount, convertedAmount, TransactionType.DEBIT);
    Transaction creditTransaction =
        createTransaction(creditAccount, convertedAmount, TransactionType.CREDIT);

    transactionRepository.save(debitTransaction);
    transactionRepository.save(creditTransaction);

    TransferHistory transferHistory = new TransferHistory();
    transferHistory.setTransferDate(Timestamp.valueOf(LocalDateTime.now()));
    transferHistory.setDebitTransactionId(Math.toIntExact(debitTransaction.getTransactionId()));
    transferHistory.setCreditTransactionId(Math.toIntExact(creditTransaction.getTransactionId()));

    transferHistoryRepository.save(transferHistory);
  }

  private Transaction createTransaction(
      Account account, Double amount, TransactionType transactionType) {
    Transaction transaction = new Transaction();
    transaction.setTransactionDate(Timestamp.valueOf(LocalDateTime.now()));
    transaction.setTransactionType(transactionType);
    transaction.setAmount(amount);
    transaction.setAccountId(Math.toIntExact(account.getAccountId()));

    return transactionRepository.save(transaction);
  }

  private Double getExchangeRate(Currency sourceCurrency, Currency destinationCurrency) {
    CurrencyValue currencyValue =
        currencyValueRepository.findByCurrencies(
            Math.toIntExact(sourceCurrency.getCurrencyId()),
            Math.toIntExact(destinationCurrency.getCurrencyId()));
    return currencyValue.getExchangeRate();
  }

  private Currency getCurrencyById(int currencyId) {
    return currencyRepository.findById((long) currencyId);
  }

  private boolean isDifferentCurrency(Account account1, Account account2) {
    return account1.getCurrencyId() != account2.getCurrencyId();
  }

  private Boolean canConvertCurrencies() {
    /* TODO: If we want to allow conversion between EUR and MGA currencies, this method must be adjusted accordingly to
    return true when conversion is possible, takes sourceCurrencyId and destinationCurrencyId as parameters */
    return false;
  }
}
