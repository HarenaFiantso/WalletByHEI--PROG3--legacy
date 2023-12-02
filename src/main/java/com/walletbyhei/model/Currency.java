package com.walletbyhei.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Currency {
  private int currency_id;
  private String currency_name;
}
