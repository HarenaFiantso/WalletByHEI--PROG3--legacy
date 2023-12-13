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
  private Double amount;
  private Account account;

  public Double getBalance() {
    return this.amount;
  }

  public void credit(double amount) {
    this.amount += amount;
  }

  public void debit(double amount) {
    if (this.amount >= amount) {
      this.amount -= amount;
    } else {
      throw new RuntimeException("Insufficient funds");
    }
  }
}
