package com.walletbyhei.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Account {
  private long account_id;
  private String first_name;
  private String last_name;
  private String password;
  // BigDecimal is more accurate for financial calculations
  private BigDecimal balance;
  private long currency_id;

  public void setAccount_id(long account_id) {
    this.account_id = account_id;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setBalance(BigDecimal balance) {
    if (balance.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Balance must be greater than or equal to 0");
    }
    this.balance = balance;
  }

  public void setCurrency_id(int currency_id) {
    this.currency_id = currency_id;
  }
}
