package com.walletbyhei.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode

public class Transaction {
    private int transaction_id;
    private LocalDate transaction_date;
    private BigDecimal amount;
    private String description;
    private int account_id;
}
