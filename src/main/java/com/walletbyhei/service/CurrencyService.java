package com.walletbyhei.service;

import com.walletbyhei.model.ExchangeRate;
import com.walletbyhei.model.type.OperationType;
import com.walletbyhei.repository.crudOperationsImpl.ExchangeRateRepository;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
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

  /* TODO: Customize your function to take not only the weighted average in a given date, but one could also choose the
  minimum value, the value maximum or median. So add a parameter to choose which one (ONLY ONE from they) we will
  use */
  public Double calculateExchangeRate(LocalDate date, OperationType operationType)
      throws SQLException, IllegalAccessException {
    List<ExchangeRate> exchangeRates = exchangeRateRepository.findExchangeRatesByDate(date);

    if (exchangeRates.isEmpty()) {
      return null;
    }

    return switch (operationType) {
      case WEIGHTED_AVERAGE -> calculateWeightedAverage(exchangeRates);
      case MINIMUM -> calculateMinimum(exchangeRates);
      case MAXIMUM -> calculateMaximum(exchangeRates);
      case MEDIAN -> calculateMedian(exchangeRates);
    };
  }

  private Double calculateMedian(List<ExchangeRate> exchangeRates) {
    List<Double> rates = exchangeRates.stream().map(ExchangeRate::getRate).sorted().toList();

    int size = rates.size();
    if (size % 2 == 0) {
      return (rates.get(size / 2 - 1) + rates.get(size / 2)) / 2.0;
    } else {
      return rates.get(size / 2);
    }
  }

  private Double calculateMaximum(List<ExchangeRate> exchangeRates) {
    return exchangeRates.stream()
        .max(Comparator.comparingDouble(ExchangeRate::getRate))
        .map(ExchangeRate::getRate)
        .orElse(null);
  }

  private Double calculateMinimum(List<ExchangeRate> exchangeRates) {
    return exchangeRates.stream()
        .min(Comparator.comparingDouble(ExchangeRate::getRate))
        .map(ExchangeRate::getRate)
        .orElse(null);
  }

  private Double calculateWeightedAverage(List<ExchangeRate> exchangeRates) {
    double totalWeightedValue = 0.0;
    double totalWeight = 0.0;

    for (ExchangeRate rate : exchangeRates) {
      double rateValue = rate.getRate();
      int weight = rate.getWeight();

      totalWeightedValue += rateValue * weight;
      totalWeight += weight;
    }

    if (totalWeight == 0.0) {
      return 0.0;
    }

    return totalWeightedValue / totalWeight;
  }
}
