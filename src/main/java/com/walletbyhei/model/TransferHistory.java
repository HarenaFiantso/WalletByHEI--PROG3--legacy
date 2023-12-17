package com.walletbyhei.model;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TransferHistory {
  private Long transferHistoryId;
  private Timestamp transferDate;
  private Double amount;
  private int debitTransactionId;
  private int creditTransactionId;
}
