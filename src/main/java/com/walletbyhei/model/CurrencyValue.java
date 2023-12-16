package com.walletbyhei.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CurrencyValue {
  private Long currencyValueId;
  private LocalDate currencyValueDate;
  private Double exchangeRate;
  private Currency sourceCurrencyId;
  private Currency destinationCurrencyId;
}
