package com.walletbyhei.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Balance {
  private Long balanceId;
  private LocalDateTime balanceDateTime;
  private LocalDateTime balanceLastUpdate;
  private Double amount;
  private Account account;
}
