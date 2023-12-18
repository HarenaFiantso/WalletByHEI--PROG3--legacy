package com.walletbyhei.service;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.Currency;
import com.walletbyhei.repository.crudOperationsImpl.CurrencyRepository;
import org.junit.jupiter.api.Test;

public class TransferServiceIT {

  @Test
  public void transferMoneyFirstCase_shouldTransferBetweenDifferentAccounts() {
    Account debitAccount = new Account();
    Account creditAccount = new Account();

    Double transferAmount = 200.0;

  }

  @Test
  public void transferMoneySecondCaseFirstPart_shouldTransferBetweenDifferentCurrencies() {
    Currency euroCurrency = new Currency();
    Currency ariaryCurrency = new Currency();

    CurrencyRepository currencyRepository = new CurrencyRepository();

  }
}
