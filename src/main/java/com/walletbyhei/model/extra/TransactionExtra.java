package com.walletbyhei.model.extra;

import com.walletbyhei.model.type.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TransactionExtra {
  private Long transactionId;
  private String label;
  private Double amount;
  private LocalDateTime dateTime;
  private TransactionType transactionType;
}
