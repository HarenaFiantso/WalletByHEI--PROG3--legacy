package com.walletbyhei.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CategorySum {
  private BigDecimal restaurant;
  private BigDecimal salary;
  private Map<String, BigDecimal> categorySums;

  public void addCategorySum(String category, BigDecimal sum) {
    categorySums.put(category, sum);
  }
}
