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
  private int debitTransactionId;
  private int creditTransactionId;
  private LocalDateTime transferDate;
}
