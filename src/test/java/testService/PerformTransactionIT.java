package testService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.Balance;
import com.walletbyhei.model.Transaction;
import com.walletbyhei.model.type.TransactionType;
import com.walletbyhei.repository.crudOperationsImpl.AccountRepository;
import com.walletbyhei.service.PerformTransaction;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PerformTransactionIT {

  @Mock private AccountRepository accountRepository;

  @Test
  public void testPerformTransactionCredit() {
    Account account = new Account();
    account.setTransactionList(new ArrayList<>());
    account.setAccountId(1L);

    Balance balance = new Balance();
    balance.setAmount(100.0);
    account.setBalance(balance);

    Transaction transaction = new Transaction();
    transaction.setAmount(50.0);
    transaction.setTransactionType(TransactionType.CREDIT);
    transaction.setDateTime(LocalDateTime.now());

    when(accountRepository.save(account)).thenReturn(account);

    PerformTransaction.performTransaction(account, 50.0, TransactionType.CREDIT);

    assertEquals(150.0, account.getBalance().getAmount(), 0.01);
    assertEquals(1, account.getTransactionList().size());

    verify(accountRepository, times(1)).save(account);
  }

  @Test
  public void testPerformTransactionInsufficientFunds() {
    Account account = new Account();
    account.setAccountId(1L);

    Balance balance = new Balance();
    balance.setAmount(50.0);
    account.setBalance(balance);

    assertThrows(
        RuntimeException.class,
        () -> PerformTransaction.performTransaction(account, 100.0, TransactionType.DEBIT));

    verifyNoInteractions(accountRepository);
  }
}
