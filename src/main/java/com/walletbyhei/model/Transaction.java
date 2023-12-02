package com.walletbyhei.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Transaction {
  private long transaction_id;
  private LocalDate transaction_date;
  private BigDecimal amount;
  private String description;
  private int account_id;
}
