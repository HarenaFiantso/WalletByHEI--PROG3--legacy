package com.walletbyhei.model;

import com.walletbyhei.model.type.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Category {
  private Long categoryId;
  private String categoryName;
  private CategoryType categoryType;
}
