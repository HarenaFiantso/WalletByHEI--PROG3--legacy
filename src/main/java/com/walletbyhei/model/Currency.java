package com.walletbyhei.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Currency {
  /* - Use camelCase as variable notation
   *  */
  private Long currencyId;
  private String currencyName;
  private String currencyCode;
}
