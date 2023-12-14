package com.walletbyhei.model.extra;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TransferHistoryExtra {
  private Long transferHistoryId;
  private int debitTransactionId;
  private int creditTransactionId;
  private Double amount;
  private LocalDateTime transferDate;
}
