package com.walletbyhei.repository.crudOperationsImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.walletbyhei.database.ConnectionToDb;
import com.walletbyhei.model.ExchangeRate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExchangeRateRepositoryIT {

  @Test
  public void testFindExchangeRatesByDate() throws Exception {
    LocalDate date = LocalDate.now();
  }
}
