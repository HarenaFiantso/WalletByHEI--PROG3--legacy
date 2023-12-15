package com.walletbyhei.dbConnection;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDb {
  /* TODO: Put credentials to environment variable */

  private static Connection connection;

  public static Connection getConnection() {
    try {
      if (connection == null || connection.isClosed()) {
        String DB_URL = System.getenv("DB_URL");
        String DB_USERNAME = System.getenv("DB_USERNAME");
        String DB_PASSWORD = System.getenv("DB_PASSWORD");

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
