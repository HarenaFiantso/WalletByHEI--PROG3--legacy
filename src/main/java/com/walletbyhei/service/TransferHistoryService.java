package com.walletbyhei.service;

import com.walletbyhei.model.TransferHistory;
import com.walletbyhei.repository.extra.TransferHistoryRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransferHistoryService {
  private final TransferHistoryRepository transferHistoryRepository;

  /* TODO: Create a function that can obtain the history of transfers in a date range */
  public List<TransferHistory> getTransfersInDateRange(
      LocalDateTime startDate, LocalDateTime endDate) {
    return transferHistoryRepository.getTransfersInDateRange(startDate, endDate);
  }
}
