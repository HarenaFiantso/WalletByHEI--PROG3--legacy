package com.walletbyhei.service;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.Transaction;
import com.walletbyhei.model.type.TransactionType;
import com.walletbyhei.repository.crudOperationsImpl.AccountRepository;
import java.time.LocalDateTime;

public class PerformTransaction {

  /* TODO: Create a function that can do transaction in an account (DEBIT or CREDIT)
   *     - Need debug session
   * */
  public static void performTransaction(
      Account account, double amount, TransactionType transactionType) {
    AccountRepository accountRepository = new AccountRepository();

    Transaction transaction = new Transaction();
    transaction.setAmount(amount);
    transaction.setTransactionType(transactionType);
    transaction.setDateTime(LocalDateTime.now());

    if (transactionType == TransactionType.CREDIT) {
      account.getBalance().credit(amount);
    } else {
      if (account.getBalance().getBalance() >= amount) {
        account.getBalance().debit(amount);
      } else {
        throw new RuntimeException("Insufficient funds");
      }
    }

    account.getTransactionList().add(transaction);

    accountRepository.save(account);
  }
}
