package com.walletbyhei.model;

import com.walletbyhei.model.type.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Account {
  private Long accountId;
  private String accountName;
  private AccountType accountType;
  private List<Transaction> transactionList;
  private Balance balance;
  private Currency currency;
}
