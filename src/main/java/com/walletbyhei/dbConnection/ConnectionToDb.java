package com.walletbyhei.dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDb {
  /* Configure the postgresql database configuration here
   *   - Create a new environment variable with the prefix "DB"
   *   - Make all variable to final
   *   - remove the connectiondb method and just refactor it just using getConnection and closeConnection
   *   - TODO: Put credentials to environment variable
   * */

  private static Connection connection;

  public static Connection getConnection() {
    try {
      if(connection == null || connection.isClosed()) {
        String DB_URL = "jdbc:postgresql://localhost:5432/wallet_by_hei";
        String DB_USERNAME = "postgres";
        String DB_PASSWORD = "tsy tadidiko";

        try {
          connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return connection;
  }

  public static void closeConnection() {
    try {
      if (connection != null && !connection.isClosed()) {
        connection.close();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
