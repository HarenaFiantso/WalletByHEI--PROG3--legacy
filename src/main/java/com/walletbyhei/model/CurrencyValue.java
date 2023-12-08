package com.walletbyhei.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyValue {
  private Long currencyValueId;
  private int sourceCurrencyId;
  private int destinationCurrencyId;
  private double value;
  private LocalDate effectiveDate;
}
