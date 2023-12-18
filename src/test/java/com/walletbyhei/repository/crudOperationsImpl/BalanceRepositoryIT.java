package com.walletbyhei.repository.crudOperationsImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.walletbyhei.database.ConnectionToDb;
import com.walletbyhei.model.Balance;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceRepositoryIT {

  @Test
  public void testFindById() throws Exception {
    long balanceId = 1L;
    LocalDateTime balanceDateTime = LocalDateTime.now();
    double amount = 100.0;
    int accountId = 123;
  }
}
