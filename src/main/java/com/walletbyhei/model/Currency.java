package com.walletbyhei.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class Currency {
  private long currency_id;
  private String currency_name;
  private String currency_code;
}
