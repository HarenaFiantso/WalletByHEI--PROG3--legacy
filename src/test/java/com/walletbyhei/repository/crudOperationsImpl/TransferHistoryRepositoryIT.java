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

  @Mock private Connection mockConnection;

  @Mock private PreparedStatement mockStatement;

  @Mock private ResultSet mockResultSet;

  private TransferHistoryRepository transferHistoryRepository;

  @Before
  public void setUp() throws Exception {
    transferHistoryRepository = new TransferHistoryRepository();
    when(ConnectionToDb.getConnection()).thenReturn(mockConnection);
    when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
  }

  @Test
  public void testFindById() throws Exception {
    Long idToFind = 1L;
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true).thenReturn(false);
    when(mockResultSet.getLong("transfer_history_id")).thenReturn(idToFind);

    TransferHistory transferHistory = transferHistoryRepository.findById(idToFind);

    assertNotNull(transferHistory);
    assertEquals(idToFind, transferHistory.getTransferHistoryId());
  }

  /* TODO: Need to implement other method similarly */
}
