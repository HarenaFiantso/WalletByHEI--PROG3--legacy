package com.walletbyhei.dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDb {
  /* Configure the postgresql database configuration here
   *   - Create a new environment variable with the prefix "DB"
   *   - Make all variable to final
   *   - remove the connectiondb method and just refactor it just using getConnection and closeConnection
   * */
  private static Connection connection;
  public static final String DB_URL = "jdbc:postgresql://localhost:5432/wallet_by_hei";
  public static final String DB_USERNAME = "postgres";
  public static final String DB_PASSWORD = "tsy tadidiko";

  public static Connection getConnection() {
    if (connection != null) {
      return connection;
    }

    try {
      connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    } catch (SQLException e) {
      System.out.println("FATAL ERROR (lol): There is an error while connecting to database");
      System.out.println(e.getMessage());
    }
    return connection;
  }

  public static void closeConnection() {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
