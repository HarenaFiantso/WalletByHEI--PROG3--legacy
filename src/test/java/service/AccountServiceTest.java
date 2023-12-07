package service;

import static org.mockito.Mockito.*;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.AccountType;
import com.walletbyhei.model.Transaction;
import com.walletbyhei.model.TransactionType;
import com.walletbyhei.repository.AccountRepository;
import com.walletbyhei.service.AccountService;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AccountServiceTest {
  private AccountService accountService;
  @Mock private AccountRepository accountRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    accountRepository = mock(AccountRepository.class);
    accountService = new AccountService(accountRepository);
  }

  @Test
  public void testPerformTransaction() throws SQLException {
    Account testAccount = new Account();
    testAccount.setAccountId(1L);
    testAccount.setAccountType(AccountType.CASH);
    testAccount.setBalance(500.0);

    doNothing().when(accountRepository).updateAccount(testAccount);

    accountService.performTransaction(testAccount, 100.0, TransactionType.CREDIT);
    Assertions.assertEquals(600.0, testAccount.getBalance(), 0.001);

    accountService.performTransaction(testAccount, 50.0, TransactionType.DEBIT);
    Assertions.assertEquals(550.0, testAccount.getBalance(), 0.001);

    verify(accountRepository, times(2)).updateAccount(testAccount);
  }

  @Test
  public void testGetBalanceAtDateTime() {
    /* Mock date for transactions */
    Transaction transaction1 = mock(Transaction.class);
    when(transaction1.getAmount()).thenReturn(100000.0);
    when(transaction1.getTransactionType()).thenReturn(TransactionType.CREDIT);
    when(transaction1.getDateTime()).thenReturn(LocalDateTime.parse("2023-12-01T00:15:00"));

    Transaction transaction2 = mock(Transaction.class);
    when(transaction2.getAmount()).thenReturn(50000.0);
    when(transaction2.getTransactionType()).thenReturn(TransactionType.DEBIT);
    when(transaction2.getDateTime()).thenReturn(LocalDateTime.parse("2023-12-02T14:00:00"));

    Transaction transaction3 = mock(Transaction.class);
    when(transaction3.getAmount()).thenReturn(20000.0);
    when(transaction3.getTransactionType()).thenReturn(TransactionType.DEBIT);
    when(transaction3.getDateTime()).thenReturn(LocalDateTime.parse("2023-12-06T16:00:00"));

    /* Simulate the list of transaction */
    List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

    Account account = mock(Account.class);
    when(account.getTransactionList()).thenReturn(transactions);

    AccountService accountService = new AccountService();

    Assertions.assertEquals(
        0,
        accountService.getBalanceAtDateTime(account, LocalDateTime.parse("2023-12-01T00:14:00")));
    Assertions.assertEquals(
        100000,
        accountService.getBalanceAtDateTime(account, LocalDateTime.parse("2023-12-01T00:15:00")));
    Assertions.assertEquals(
        50000,
        accountService.getBalanceAtDateTime(account, LocalDateTime.parse("2023-12-02T14:00:00")));
    Assertions.assertEquals(
        50000,
        accountService.getBalanceAtDateTime(account, LocalDateTime.parse("2023-12-06T15:45:00")));
    Assertions.assertEquals(
        30000,
        accountService.getBalanceAtDateTime(account, LocalDateTime.parse("2023-12-06T16:00:00")));
  }
}
