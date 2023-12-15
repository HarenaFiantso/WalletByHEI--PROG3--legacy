package com.walletbyhei.service;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.CurrencyValue;
import com.walletbyhei.model.Transaction;
import com.walletbyhei.repository.crudOperationsImpl.CurrencyValueRepository;
import com.walletbyhei.repository.crudOperationsImpl.TransactionRepository;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceService {
  private final TransactionRepository transactionRepository;
  private final CurrencyValueRepository currencyValueRepository;

  /* TODO: Create a function to transfer money between two accounts (second case | Second part) */
  public Double calculateAriaryBalance(Account euroAccount, Account ariaryAccount) {
    List<Transaction> euroToAriaryTransfers =
        transactionRepository.findTransfersBetweenAccounts(euroAccount, ariaryAccount);

    double ariaryBalance = 0.0;

    for (Transaction transfer : euroToAriaryTransfers) {
      Double euroAmount = transfer.getAmount();

      CurrencyValue currencyValue =
          currencyValueRepository.findCurrencyValueForDate(transfer.getTransactionDate());

      Double exchangeRate = currencyValue.getExchangeRate();
      double ariaryEquivalent = euroAmount * exchangeRate;

      ariaryBalance += ariaryEquivalent;
    }

    return ariaryBalance;
  }
}
