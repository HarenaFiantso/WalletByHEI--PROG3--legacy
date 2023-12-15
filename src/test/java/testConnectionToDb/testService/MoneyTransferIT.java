package testConnectionToDb.testService;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.Balance;
import com.walletbyhei.model.Currency;
import com.walletbyhei.model.Transaction;
import com.walletbyhei.model.type.TransactionType;
import com.walletbyhei.repository.crudOperationsImpl.CurrencyValueRepository;
import com.walletbyhei.service.MoneyTransfer;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

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

  /* TODO: Need a better code testing */
  @Test
  public void convertCurrencyTest() {
    Account fromAccount = mock(Account.class);
    Account toAccount = mock(Account.class);

    CurrencyValueRepository currencyValueRepository = mock(CurrencyValueRepository.class);

    double amount = 100.0;
    LocalDate date = LocalDate.of(2023, 12, 6);
    double conversionRate = 4600.0;

    when(fromAccount.equals(toAccount)).thenReturn(false);

    Currency fromCurrency = new Currency(1L, "Euro", "EUR");
    Currency toCurrency = new Currency(2L, "Ariary", "ARI");
    when(fromAccount.getCurrency()).thenReturn(fromCurrency);
    when(toAccount.getCurrency()).thenReturn(toCurrency);

    when(currencyValueRepository.getConversionRate(fromCurrency.getCurrencyId(), toCurrency.getCurrencyId(), date))
        .thenReturn(conversionRate);

    MoneyTransfer moneyTransfer = new MoneyTransfer();
    moneyTransfer.convertCurrency(fromAccount, toAccount, amount, date);

    verify(fromAccount).getTransactionList().add(any(Transaction.class));
    verify(toAccount).getTransactionList().add(any(Transaction.class));

    verify(fromAccount).getBalance().setAmount(0.00);
    verify(toAccount).getBalance().setAmount(amount * conversionRate);
  }
}
