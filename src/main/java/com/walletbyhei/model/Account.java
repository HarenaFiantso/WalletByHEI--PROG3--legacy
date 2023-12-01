package com.walletbyhei.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Account {
    private int account_id;
    private String first_name;
    private String last_name;
    private String password;
    // BigDecimal is more accurate for financial calculations
    private BigDecimal balance;
    private int currency_id;

    public void setAccount_id(int account_id) {
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
