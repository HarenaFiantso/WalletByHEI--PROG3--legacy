package com.walletbyhei.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class Currency {
<<<<<<< HEAD
  private int currencyId; //camelCase
  private String currencyName; //camelCase
=======
  private long currency_id;
  private String currency_name;
  private String currency_code;
>>>>>>> 9c2aa5cb41b51030160c6a4c751d7ca3a2c211df
}
