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
  private String label;
  private Double amount;
  private LocalDateTime dateTime;
  private TransactionType transactionType;
  private Account account;
}
