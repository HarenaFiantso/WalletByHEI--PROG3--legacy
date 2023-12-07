package com.walletbyhei.model;

import java.util.List;

public class Account {
    /* - Use camelCase as variable notation
    *  - Add some additional attributs
    *  - TODO: Create an Enum file for the type attribut
    *  */
    private Long accountId;
    private String accountName;
    private double balance;
    private List<?> transactionList;
    private String currency;
    private String type;
}
