package com.walletbyhei.model.extra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CurrencyExtra {
  private Long currencyId;
  private String currencyName;
  private String currencyCode;
}
