package testRepository;

import com.walletbyhei.model.Balance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.walletbyhei.repository.crudOperationsImpl.BalanceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceRepositoryIT {
  private BalanceRepository balanceRepository;

  @BeforeEach
  public void setUp() {
    balanceRepository = new BalanceRepository();
  }

  @Test
  public void testFindAll() {
    List<Balance> balances = balanceRepository.findAll();

    /* TODO: Add more assertions based on the expected behavior of this method */
    Assertions.assertNotNull(balances);
  }

  @Test
  public void testDelete() {
    Balance balanceToDelete = new Balance();
    balanceToDelete.setBalanceId(1L);

    Balance deletedBalance = balanceRepository.delete(balanceToDelete);

    /* TODO: Add more assertions based on the expected behavior of this method */
    Assertions.assertEquals(balanceToDelete.getAccountId(), deletedBalance.getAccountId());
  }
}
