package com.walletbyhei.model;

import com.walletbyhei.model.type.CurrencyCodeType;
import com.walletbyhei.model.type.CurrencyNameType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Currency {
  private Long currencyId;
  private CurrencyNameType currencyName;
  private CurrencyCodeType currencyCode;
}
