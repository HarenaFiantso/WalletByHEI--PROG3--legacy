package com.walletbyhei.model.extra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class BalanceExtra {
  private Long balanceId;
  private Double amount;
  private LocalDateTime lastUpdate;
}
