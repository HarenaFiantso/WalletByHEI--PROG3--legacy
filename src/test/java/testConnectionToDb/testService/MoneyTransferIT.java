package testConnectionToDb.testService;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.Balance;
import com.walletbyhei.model.Transaction;
import com.walletbyhei.model.type.TransactionType;
import com.walletbyhei.service.MoneyTransfer;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class MoneyTransferIT {

  /* TODO: Need a better code testing */
  @Test
  void transferMoneyBetweenAccountsTest() {
    Account fromAccount = mock(Account.class);
    Account toAccount = mock(Account.class);

    Transaction debitTransaction = mock(Transaction.class);
    Transaction creditTransaction = mock(Transaction.class);

    Balance fromBalance = mock(Balance.class);
    Balance toBalance = mock(Balance.class);

    when(fromBalance.getAmount()).thenReturn(1000.0);
    when(toBalance.getAmount()).thenReturn(500.0);

    when(fromAccount.getBalance()).thenReturn(fromBalance);
    when(toAccount.getBalance()).thenReturn(toBalance);

    MoneyTransfer moneyTransfer = spy(new MoneyTransfer());
    doReturn(debitTransaction).when(moneyTransfer).createDebitTransaction(fromAccount, 200.0);
    doReturn(creditTransaction).when(moneyTransfer).createCreditTransaction(toAccount, 200.0);

    moneyTransfer.transferMoneyBetweenAccounts(fromAccount, toAccount, 200.0);

    verify(fromAccount.getTransactionList()).add(debitTransaction);
    verify(toAccount.getTransactionList()).add(creditTransaction);

    verify(moneyTransfer).updateAccountBalance(fromAccount, 200.0, TransactionType.DEBIT);
    verify(moneyTransfer).updateAccountBalance(toAccount, 200.0, TransactionType.CREDIT);
  }
}
