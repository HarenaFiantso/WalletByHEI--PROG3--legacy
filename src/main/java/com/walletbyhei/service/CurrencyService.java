package com.walletbyhei.service;

import com.walletbyhei.model.ExchangeRate;
import com.walletbyhei.repository.crudOperationsImpl.ExchangeRateRepository;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CurrencyService {
  private final ExchangeRateRepository exchangeRateRepository;

  /* TODO: Calculate the weighted average of the value of the euro during this date, to calculate our balance */
  public Double calculateWeightedAverageExchangeRate(LocalDate date) throws SQLException {
    List<ExchangeRate> exchangeRates = exchangeRateRepository.findExchangeRatesByDate(date);

    double totalWeightedValue = 0.0;
    double totalWeight = 0.0;

    for (ExchangeRate rate : exchangeRates) {
      double rateValue = rate.getRate();
      int weight = rate.getWeight();

      totalWeightedValue += rateValue * weight;
      totalWeight += weight;
    }

    return totalWeightedValue / totalWeight;
  }
}
