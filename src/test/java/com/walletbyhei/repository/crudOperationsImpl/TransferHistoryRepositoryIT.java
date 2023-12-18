package com.walletbyhei.repository.crudOperationsImpl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.walletbyhei.database.ConnectionToDb;
import com.walletbyhei.model.TransferHistory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransferHistoryRepositoryIT {

  @Test
  public void testFindById() throws Exception {
    Long idToFind = 1L;
  }

  /* TODO: Need to implement other method similarly */
}
