package testService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.Transaction;
import com.walletbyhei.model.type.TransactionType;
import com.walletbyhei.service.GetBalanceAtDateTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetBalanceAtDateTimeIT {

  private GetBalanceAtDateTime getBalanceAtDateTime;

  @BeforeEach
  public void setUp() {
    getBalanceAtDateTime = new GetBalanceAtDateTime();
  }

  @Test
  public void testGetBalanceAtDateTime() {
    Account account = mock(Account.class);
    List<Transaction> transactions = new ArrayList<>();
    transactions.add(
        createTransaction(
            1L, "Salary",
            100000,
            TransactionType.CREDIT,
            LocalDateTime.of(2023, 12, 1, 0, 15)));
    transactions.add(
        createTransaction(
            2L,
            "Christmas Gift",
            50000,
            TransactionType.DEBIT,
            LocalDateTime.of(2023, 12, 2, 14, 0)));
    transactions.add(
        createTransaction(
            3L,
            "New shoes",
            20000,
            TransactionType.DEBIT,
            LocalDateTime.of(2023, 12, 6, 16, 0)));
    when(account.getTransactionList()).thenReturn(transactions);

    double balance =
        getBalanceAtDateTime
            .getBalanceAtDateTime(
                account,
                LocalDateTime.of(2023, 12, 6, 15, 45)
            );

    double expectedBalance = 50000;

    assertEquals(expectedBalance, balance);
  }

  private Transaction createTransaction(
      Long id, String label, double amount, TransactionType type, LocalDateTime dateTime) {
    Transaction transaction = new Transaction();
    transaction.setTransactionId(id);
    transaction.setLabel(label);
    transaction.setAmount(amount);
    transaction.setTransactionType(type);
    transaction.setDateTime(dateTime);
    return transaction;
  }
}
