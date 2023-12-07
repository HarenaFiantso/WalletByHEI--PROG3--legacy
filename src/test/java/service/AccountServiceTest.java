package service;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.AccountType;
import com.walletbyhei.model.TransactionType;
import com.walletbyhei.repository.AccountRepository;
import com.walletbyhei.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class AccountServiceTest {
    private AccountService accountService;
    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
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
}
