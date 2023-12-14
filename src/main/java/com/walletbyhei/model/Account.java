package com.walletbyhei.model;

import com.walletbyhei.model.type.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Account {
  /* TODO: Create an Enum file for the accountType attribute ✅ */
  private Long accountId;
  private String accountName;
  private AccountType accountType;
  private int currencyId;
}
