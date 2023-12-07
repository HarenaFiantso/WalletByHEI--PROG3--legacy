package com.walletbyhei.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Currency {
  private int currencyId; //camelCase
  private String currencyName; //camelCase
}
