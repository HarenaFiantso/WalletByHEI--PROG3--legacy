package com.walletbyhei.model;

import com.walletbyhei.model.type.AccountType;
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
  private Balance balanceId;
  private int currencyId;
}
