package com.walletbyhei.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Transaction {
  private long transaction_id;
  private String label;
  private BigDecimal amount;
  private LocalDateTime transaction_date_time;
  private String type;
  private long account_id;
}
