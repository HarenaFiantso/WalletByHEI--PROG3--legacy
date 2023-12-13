package com.walletbyhei.repository.extra;

import com.walletbyhei.model.TransferHistory;

import java.time.LocalDateTime;
import java.util.List;

public interface TransferHistoryRepository {
  List<TransferHistory> getTransfersInDateRange(LocalDateTime startDate, LocalDateTime endDate);
}
