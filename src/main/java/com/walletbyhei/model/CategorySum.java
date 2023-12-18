package com.walletbyhei.model;

import java.math.BigDecimal;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
