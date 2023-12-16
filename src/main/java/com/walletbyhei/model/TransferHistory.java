package com.walletbyhei.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TransferHistory {
  private Long transferHistoryId;
  private Transaction debitTransaction;
  private Transaction creditTransaction;
  private Double amount;
  private LocalDateTime transferDate;
}
