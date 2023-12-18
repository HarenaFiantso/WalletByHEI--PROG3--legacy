package com.walletbyhei.repository.crudOperationsImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.walletbyhei.database.ConnectionToDb;
import com.walletbyhei.model.Currency;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CurrencyRepositoryIT {

  private CurrencyRepository currencyRepository;
  private Connection mockConnection;
  private PreparedStatement mockStatement;
  private ResultSet mockResultSet;

  @Test
  public void testFindById() throws Exception {
    long currencyId = 1L;
  }

  @Test
  public void testFindAll() throws Exception {
    long currencyId1 = 1L;
    long currencyId2 = 2L;
  }
}
