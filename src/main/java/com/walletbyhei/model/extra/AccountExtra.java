package com.walletbyhei.model.extra;

import com.walletbyhei.model.type.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class AccountExtra {
  private Long accountId;
  private String accountName;
  private BalanceExtra balance;
  private CurrencyExtra currency;
  private List<TransactionExtra> transactionExtraList;
  private AccountType accountType;
}
