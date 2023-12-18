package com.walletbyhei.repository.crudOperationsImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.walletbyhei.database.ConnectionToDb;
import com.walletbyhei.model.CurrencyValue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CurrencyValueRepositoryIT {

  @Test
  public void testFindByCurrencies() throws Exception {
    int sourceCurrencyId = 1;
    int destinationCurrencyId = 2;
  }

  @Test
  public void testFindCurrencyValueForDate() throws Exception {
    Timestamp transactionDate = Timestamp.valueOf(LocalDate.now().atStartOfDay());
  }
}
