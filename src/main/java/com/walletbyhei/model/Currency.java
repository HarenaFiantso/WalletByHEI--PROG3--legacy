package com.walletbyhei.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Currency {
  /* - Use camelCase as variable notation
   *  - TODO: Create an Enum file for the currencyCode attribute ✅
   *  */
  private Long currencyId;
  private String currencyName;
  private CurrencyCode currencyCode;
}
