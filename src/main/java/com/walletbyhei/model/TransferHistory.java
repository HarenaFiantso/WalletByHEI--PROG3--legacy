package com.walletbyhei.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferHistory {
  private Long transferHistoryId;
  private int debitTransactionId;
  private int creditTransactionId;
  private LocalDateTime transferData;
}
