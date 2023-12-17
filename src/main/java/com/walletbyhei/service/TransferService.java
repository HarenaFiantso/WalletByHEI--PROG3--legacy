package com.walletbyhei.service;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.Transaction;
import com.walletbyhei.model.TransferHistory;
import com.walletbyhei.model.type.TransactionType;
import com.walletbyhei.repository.crudOperationsImpl.TransactionRepository;
import com.walletbyhei.repository.crudOperationsImpl.TransferHistoryRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TransferService {
  private final TransactionRepository transactionRepository = new TransactionRepository();
  private final TransferHistoryRepository transferHistoryRepository = new TransferHistoryRepository();

  /* TODO: Create a function to transfer money between two accounts */
  public void transferMoney(Account debitAccount, Account creditAccount, Double amount) throws IllegalAccessException {
    if (debitAccount.getAccountId().equals(creditAccount.getAccountId())) {
      throw new IllegalAccessException("An account couldn't done transfer for himself");
    }

    Transaction debitTransaction = createTransaction(debitAccount, amount, TransactionType.DEBIT);
    Transaction creditTransaction = createTransaction(creditAccount, amount, TransactionType.CREDIT);

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

  private Transaction createTransaction(Account account, Double amount, TransactionType transactionType) {
    Transaction transaction = new Transaction();
    transaction.setTransactionDate(Timestamp.valueOf(LocalDateTime.now()));
    transaction.setTransactionType(transactionType);
    transaction.setAmount(amount);
    transaction.setAccountId(Math.toIntExact(account.getAccountId()));

    return transactionRepository.save(transaction);
  }
}
