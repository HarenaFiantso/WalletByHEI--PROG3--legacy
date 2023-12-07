package com.walletbyhei.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    /* - Use camelCase as variable notation
    *  - Add some additional attributes
    *  - TODO: Create an Enum file for the accountType attribute
    *  */
    private Long accountId;
    private String accountName;
    private double balance;
    private List<?> transactionList;
    private int currencyId;
    private String accountType;
}
