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
  private int sourceCurrencyId;
  private int destinationCurrencyId;
  private Double value;
  private LocalDate effectiveDate;
}
