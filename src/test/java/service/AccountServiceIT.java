package service;

import static org.mockito.Mockito.*;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.Transaction;
import com.walletbyhei.model.type.AccountType;
import com.walletbyhei.model.type.TransactionType;
import com.walletbyhei.repository.AccountRepository;
import com.walletbyhei.service.AccountService;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AccountServiceIT {
  @InjectMocks private AccountService accountService;
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

  @Test
  public void testGetCurrentBalance() {
    Account testAccount = new Account();
    testAccount.setAccountId(1L);
    testAccount.setAccountName("Test Account");
    testAccount.setBalance(500.0);

    List<Transaction> transactions = new ArrayList<>();
    transactions.add(
        new Transaction(
            1L,
            "Salary",
            100000.0,
            LocalDateTime.parse("2023-12-01T00:15:00"),
            TransactionType.CREDIT));
    transactions.add(
        new Transaction(
            2L,
            "New shoes",
            50000.0,
            LocalDateTime.parse("2023-12-02T14:00:00"),
            TransactionType.DEBIT));
    testAccount.setTransactionList(transactions);

    LocalDateTime dateTimeToCheck = LocalDateTime.parse("2023-12-01T00:14:00");

    when(accountRepository.getBalanceAtDateTime(testAccount, dateTimeToCheck)).thenReturn(0.0);

    double balanceAtDateTime = accountService.getCurrentBalance(testAccount);
    Assertions.assertEquals(0.0, balanceAtDateTime);
  }

  @Test
  public void testGetBalanceHistoryInDateTimeRange() {
    AccountRepository accountRepository = mock(AccountRepository.class);
    AccountService accountService = new AccountService(accountRepository);

    Account account = new Account();
    LocalDateTime startDateTime = LocalDateTime.parse("2023-12-01T00:00:00");
    LocalDateTime endDateTime = LocalDateTime.parse("2023-12-02T00:00:00");

    /* Stub the accountRepository.getBalanceAtDateTime() method */
    when(accountRepository.getBalanceAtDateTime(eq(account), any())).thenReturn(500.0);

    Map<LocalDateTime, Double> balanceHistory =
        accountService.getBalanceHistoryInDateTimeRange(account, startDateTime, endDateTime);

    int expectedSize = 1441;
    Assertions.assertEquals(expectedSize, balanceHistory.size());
  }

  @Test
  public void testMakeTransfer() throws SQLException {
    Account sourceAccount = new Account();
    sourceAccount.setAccountId((1L));
    sourceAccount.setBalance(1000.0);

    Account destinationAccount = new Account();
    destinationAccount.setAccountId(2L);
    destinationAccount.setBalance(500.0);

    AccountRepository accountRepository = mock(AccountRepository.class);
    AccountService accountService = new AccountService(accountRepository);

    doNothing().when(accountRepository).updateAccount(sourceAccount);
    doNothing().when(accountRepository).updateAccount(destinationAccount);

    boolean transferResult = accountService.makeTransfer(sourceAccount, destinationAccount, 200.0);

    verify(accountRepository, times(1)).updateAccount(sourceAccount);
    verify(accountRepository, times(1)).updateAccount(destinationAccount);

    Assertions.assertTrue(transferResult);
    Assertions.assertEquals(800.0, sourceAccount.getBalance(), 0.001);
    Assertions.assertEquals(700.0, destinationAccount.getBalance(), 0.001);
  }
}
