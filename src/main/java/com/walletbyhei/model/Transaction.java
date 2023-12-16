package com.walletbyhei.model;

import com.walletbyhei.model.type.TransactionType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Transaction {
  private Long transactionId;
  private LocalDateTime transactionDate;
  private TransactionType transactionType;
  private Double amount;
  private String reason;
  private Account account;
  private Category category;
}
