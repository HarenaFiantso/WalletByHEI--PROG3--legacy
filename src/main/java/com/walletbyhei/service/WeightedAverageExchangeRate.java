package com.walletbyhei.service;

import com.walletbyhei.model.CurrencyValue;
import com.walletbyhei.repository.crudOperationsImpl.CurrencyValueRepository;

import java.time.LocalDate;
import java.util.List;

public class WeightedAverageExchangeRate {
  CurrencyValueRepository currencyValueRepository = new CurrencyValueRepository();

  /* TODO: Retrieve te exchange rate of the day data from database */
  public double calculateWeightedAverageExchangeRate(LocalDate date) {
    List<CurrencyValue> currencyValues = currencyValueRepository.retrieveCurrencyValuesForDay(date);

    double weightedSum = 0;
    double totalHours = 0;

    assert currencyValues != null;
    for (CurrencyValue value : currencyValues) {
      double exchangeRate = value.getValue();
      int hourOfDay = value.getEffectiveDate().atStartOfDay().getHour();
      double weight = calculateWeight(hourOfDay);

      weightedSum += exchangeRate * weight;
      totalHours += weight;
    }

    return weightedSum / totalHours;
  }

  private double calculateWeight(int hourOfDay) {
    double weight = 0.00;

    if (hourOfDay >= 9 && hourOfDay <= 17) {
      weight = 2.00;
    }  else {
      weight = 1.00;
    }

    return weight;
  }
}
