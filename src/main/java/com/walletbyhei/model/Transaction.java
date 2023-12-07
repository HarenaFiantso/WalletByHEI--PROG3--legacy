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
<<<<<<< HEAD
  private int transaction_id;
  private LocalDate transaction_date;
  private BigDecimal amount; // Double
  private String description;
  private int account_id;
=======
  private long transaction_id;
  private String label;
  private BigDecimal amount;
  private LocalDateTime transaction_date_time;
  private String type;
  private long account_id;
>>>>>>> 9c2aa5cb41b51030160c6a4c751d7ca3a2c211df
}
