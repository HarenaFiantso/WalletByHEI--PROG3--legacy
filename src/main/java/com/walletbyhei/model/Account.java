package com.walletbyhei.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Account {
<<<<<<< HEAD
  private int account_id;
  private String first_name;
  private String last_name;
  private String password;
  // BigDecimal is more accurate for financial calculations
  private BigDecimal balance; // Double
  private int currency_id;
=======
    private long account_id;
    private String account_name;
    // BigDecimal is more accurate for financial calculations
    private BigDecimal balance;
    private LocalDate balanceUpdate;
    private List<Transaction> transactionList;
    private String account_type;
    private long currency_id;
>>>>>>> 9c2aa5cb41b51030160c6a4c751d7ca3a2c211df

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public void setFirst_name(String account_name) {
        this.account_name = account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public void setBalanceUpdate(LocalDate balanceUpdate) {
        this.balanceUpdate = balanceUpdate;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public void setCurrency_id(long currency_id) {
        this.currency_id = currency_id;
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
