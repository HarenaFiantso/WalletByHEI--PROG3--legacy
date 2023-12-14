package testRepository;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.type.AccountType;
import com.walletbyhei.repository.crudOperationsImpl.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryIT {
  private AccountRepository accountRepository;

  @BeforeEach
  public void setUp() {
    accountRepository = new AccountRepository();
  }

  @Test
  public void testFindAll() {
    List<Account> accounts = accountRepository.findAll();

    /* TODO: Add more assertions based on the expected behavior of this method */
    Assertions.assertNotNull(accounts);
  }

  @Test
  public void testSaveAll() {
    List<Account> accountsToSave = new ArrayList<>();
    Account account1 = new Account();
    account1.setAccountName("Test Account1");
    account1.setAccountType(AccountType.valueOf("BANK"));
    account1.setCurrencyId(1);

    Account account2 = new Account();
    account2.setAccountName("Test Account2");
    account2.setAccountType(AccountType.valueOf("MOBILE_MONEY"));
    account2.setCurrencyId(2);

    accountsToSave.add(account1);
    accountsToSave.add(account2);

    List<Account> savedAccounts = accountRepository.saveAll(accountsToSave);

    /* TODO: Add more assertions based on the expected behavior of this method */
    Assertions.assertNotNull(savedAccounts);
    Assertions.assertEquals(2, savedAccounts.size());
  }

  @Test
  public void testSave() {
    Account accountToSave = new Account();
    accountToSave.setAccountName("Savings account");
    accountToSave.setAccountType(AccountType.valueOf("CASH"));
    accountToSave.setCurrencyId(1);

    Account savedAccount = accountRepository.save(accountToSave);

    /* TODO: Add more assertions based on the expected behavior of this method */
    Assertions.assertNotNull(savedAccount.getAccountId());
  }

  @Test
  public void testDelete() {
    Account accountToDelete = new Account();
    accountToDelete.setAccountId(1L);

    Account deletedAccount = accountRepository.delete(accountToDelete);

    /* TODO: Add more assertions based on the expected behavior of this method */
    Assertions.assertEquals(accountToDelete.getAccountId(), deletedAccount.getAccountId());
  }
}
