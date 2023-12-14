package com.walletbyhei.model;

import java.util.ArrayList;
import java.util.List;

import com.walletbyhei.model.type.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force=true)
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
