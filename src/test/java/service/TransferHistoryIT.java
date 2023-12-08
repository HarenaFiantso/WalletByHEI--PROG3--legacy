package service;

import com.walletbyhei.model.Transaction;
import com.walletbyhei.model.TransferHistory;
import com.walletbyhei.repository.TransferHistoryRepository;
import com.walletbyhei.service.TransferHistoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class TransferHistoryIT {
  @Test
  public void testGetTransfersInDateRange() {
    TransferHistoryRepository transferHistoryRepository = mock(TransferHistoryRepository.class);
    TransferHistoryService transferHistoryService = new TransferHistoryService(transferHistoryRepository);

    LocalDateTime startDate = LocalDateTime.parse("2023-01-01T00:00:00");
    LocalDateTime endDate = LocalDateTime.parse("2023-12-31T23:59:59");

    List<TransferHistory> expectedTransfers = new ArrayList<>();
    /* Should create transfer example here but bruhhh, I'm tired */

    when(transferHistoryRepository.getTransfersInDateRange(startDate, endDate)).thenReturn(expectedTransfers);

    List<TransferHistory> actualTransfers = transferHistoryService.getTransfersInDateRange(startDate, endDate);

    verify(transferHistoryRepository, times(1)).getTransfersInDateRange(startDate, endDate);

    Assertions.assertEquals(expectedTransfers.size(), actualTransfers.size());
  }
}
