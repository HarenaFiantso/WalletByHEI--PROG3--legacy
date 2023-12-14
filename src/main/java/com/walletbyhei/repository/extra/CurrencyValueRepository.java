package com.walletbyhei.repository.extra;

import java.time.LocalDate;

public interface CurrencyValueRepository {
  double getExchangeRate(int sourceCurrencyId, int destinationCurrencyId, LocalDate date);

  int getCurrencyIdBySymbol(String symbol);
}
