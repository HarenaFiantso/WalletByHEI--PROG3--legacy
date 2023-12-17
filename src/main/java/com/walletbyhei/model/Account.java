package com.walletbyhei.model;

import com.walletbyhei.model.type.AccountType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Account {
  private Long accountId;
  private String accountName;
  private AccountType accountType;
  private List<Transaction> transactionList;
  private Double balance;
  private int currencyId;
  private LocalDateTime lastTransactionDate;
}
