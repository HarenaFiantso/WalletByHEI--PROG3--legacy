package com.walletbyhei.repository.crudOperationsImpl;

import com.walletbyhei.model.Account;
import com.walletbyhei.model.type.AccountType;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountRepositoryIT {

  @Mock
  private AccountRepository accountRepository;

  @Captor
  private ArgumentCaptor<Account> accountCaptor;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testFindById() {
    Account mockAccount = createMockAccount();

    when(accountRepository.findById(1L)).thenReturn(mockAccount);

    Account foundAccount = accountRepository.findById(1L);

    assertNotNull(foundAccount);
    assertEquals("Mock Account", foundAccount.getAccountName());
    assertEquals(AccountType.CASH, foundAccount.getAccountType());
    assertEquals(1, foundAccount.getCurrencyId());
  }

  @Test
  public void testFindAll() {
    List<Account> mockAccounts = createMockAccountList();

    when(accountRepository.findAll()).thenReturn(mockAccounts);

    List<Account> allAccounts = accountRepository.findAll();

    assertNotNull(allAccounts);
    assertFalse(allAccounts.isEmpty());
    assertEquals(1, allAccounts.size());

    Account retrievedAccount = allAccounts.get(0);
    assertEquals("Mock Account", retrievedAccount.getAccountName());
    assertEquals(AccountType.CASH, retrievedAccount.getAccountType());
    assertEquals(1, retrievedAccount.getCurrencyId());
  }

  @Test
  public void testSave() {
    Account newAccount = createMockAccount();

    when(accountRepository.save(any(Account.class))).thenReturn(newAccount);
    Account savedAccount = accountRepository.save(newAccount);

    assertNotNull(savedAccount);
    assertEquals("Mock Account", savedAccount.getAccountName());
    assertEquals(AccountType.CASH, savedAccount.getAccountType());
    assertEquals(1, savedAccount.getCurrencyId());
  }

  @Test
  public void testDelete() {
    Account mockAccount = createMockAccount();

    accountRepository.delete(mockAccount);

    verify(accountRepository, times(1)).delete(accountCaptor.capture());

    assertEquals(mockAccount, accountCaptor.getValue());
  }

  private Account createMockAccount() {
    Account account = new Account();
    account.setAccountId(1L);
    account.setAccountName("Mock Account");
    account.setAccountType(AccountType.CASH);
    account.setCurrencyId(1);
    return account;
  }

  private List<Account> createMockAccountList() {
    List<Account> accounts = new ArrayList<>();
    accounts.add(createMockAccount());
    return accounts;
  }
}
