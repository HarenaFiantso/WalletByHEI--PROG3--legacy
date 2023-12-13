package testConnectionToDb;

import com.walletbyhei.dbConnection.ConnectionToDb;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ConnectionToDbIT {
  private static Connection connection;

  @BeforeAll
  public static void setUp() {
    connection = ConnectionToDb.getConnection();
  }

  @Test
  public void testConnectionNotNull() {
    Assertions.assertNotNull(connection);
  }

  @Test
  public void testConnectionIsOpen() {
    try {
      Assertions.assertFalse(connection.isClosed());
    } catch (SQLException e) {
      throw new RuntimeException("Failed to open the database connection : " + e.getMessage());
    }
  }

  @AfterAll
  public static void tearDown() {
    ConnectionToDb.closeConnection();

    try {
      Assertions.assertTrue(connection.isClosed());
    } catch (SQLException e) {
      throw new RuntimeException("Failed to close the database connection : " + e.getMessage());
    }
  }
}
