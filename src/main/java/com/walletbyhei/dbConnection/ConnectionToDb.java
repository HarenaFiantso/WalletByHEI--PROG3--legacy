package com.walletbyhei.dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDb {
  /* TODO: Put credentials to environment variable */

  private static Connection connection;

  public static Connection getConnection() {
    try {
      if (connection == null || connection.isClosed()) {
        String DB_URL = "jdbc:postgresql://localhost:5432/wallet_by_hei";
        String DB_USERNAME = "postgres";
        String DB_PASSWORD = "tsy tadidiko";

        try {
          connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
          throw new RuntimeException("Cannot connect to database : " + e.getMessage());
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to get connection : " + e.getMessage());
    }
    return connection;
  }
}
