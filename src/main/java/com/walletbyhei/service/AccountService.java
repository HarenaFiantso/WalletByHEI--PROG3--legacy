package com.walletbyhei.service;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.AccountType;
import com.walletbyhei.model.Transaction;
import com.walletbyhei.model.TransactionType;
import com.walletbyhei.repository.AccountRepository;
import lombok.AllArgsConstructor;

import java.sql.SQLException;
import java.time.LocalDateTime;

@AllArgsConstructor
public class AccountService {
    private AccountRepository accountRepository;

    public void performTransaction(Account account, double amount, TransactionType transactionType) throws SQLException {
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
}

