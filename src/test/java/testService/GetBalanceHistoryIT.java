package testService;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.Transaction;
import com.walletbyhei.model.type.TransactionType;
import com.walletbyhei.service.GetBalanceHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetBalanceHistoryIT {

  private GetBalanceHistory getBalanceHistory;

  @BeforeEach
  public void setUp() {
    getBalanceHistory = new GetBalanceHistory();
  }

  @Test
  public void testGetBalanceHistoryInDateTimeInterval() {
    Account account = mock(Account.class);
    List<Transaction> transactions = new ArrayList<>();
    transactions.add(createTransaction(1L, "Salary", 100000, TransactionType.CREDIT, LocalDateTime.of(2023, 12, 1, 0, 15)));
    transactions.add(createTransaction(2L, "Shopping", 50000, TransactionType.DEBIT, LocalDateTime.of(2023, 12, 2, 14, 0)));
    when(account.getTransactionList()).thenReturn(transactions);

    LocalDateTime startDate = LocalDateTime.of(2023, 12, 1, 0, 0);
    LocalDateTime endDate = LocalDateTime.of(2023, 12, 3, 0, 0);
    List<Double> balanceHistory = getBalanceHistory.getBalanceHistoryInDateTimeInterval(account, startDate, endDate);

    List<Double> expectedBalanceHistory = new ArrayList<>();
    expectedBalanceHistory.add(100000.0);
    expectedBalanceHistory.add(50000.0);

    assertEquals(expectedBalanceHistory, balanceHistory);
  }

  private Transaction createTransaction(Long id, String label, double amount, TransactionType type, LocalDateTime dateTime) {
    Transaction transaction = new Transaction();
    transaction.setTransactionId(id);
    transaction.setLabel(label);
    transaction.setAmount(amount);
    transaction.setTransactionType(type);
    transaction.setDateTime(dateTime);
    return transaction;
  }
}
