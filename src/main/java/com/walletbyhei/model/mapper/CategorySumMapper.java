package com.walletbyhei.model.mapper;

import com.walletbyhei.model.CategorySum;
import java.math.BigDecimal;
import java.util.Map;

public class CategorySumMapper {

  public CategorySum mapToCategorySum(Map<String, BigDecimal> categorySumMap) {
    CategorySum categorySum = new CategorySum();

    for (Map.Entry<String, BigDecimal> entry : categorySumMap.entrySet()) {
      categorySum.addCategorySum(entry.getKey(), entry.getValue());
    }

    return categorySum;
  }
}
