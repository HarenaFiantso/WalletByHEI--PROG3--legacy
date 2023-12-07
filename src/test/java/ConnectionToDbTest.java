import com.walletbyhei.dbConnection.ConnectionToDb;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionToDbTest {
  @Test
  public void testDatabaseConnection() {
    Connection connection = ConnectionToDb.getConnection();
    try {
      Assert.assertFalse("Connection should be open", connection.isClosed());
      connection.close();
      Assert.assertTrue(
          "Connection should be closed after closeConnection() call", connection.isClosed());
    } catch (SQLException e) {
      Assert.fail("Exception thrown while testing database connection: " + e.getMessage());
    }
  }
}
