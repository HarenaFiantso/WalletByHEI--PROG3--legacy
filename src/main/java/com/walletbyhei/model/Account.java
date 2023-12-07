package com.walletbyhei.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Account {
  /* - Use camelCase as variable notation
   *  - Add some additional attributes
   *  - TODO: Create an Enum file for the accountType attribute ✅
   *  */
  private Long accountId;
  private String accountName;
  private double balance;
  private List<Transaction> transactionList;
  private int currencyId;
  private AccountType accountType;

  public Account() {
    this.transactionList = new ArrayList<>();
  }
}
