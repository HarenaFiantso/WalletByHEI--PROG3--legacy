package com.walletbyhei;

import com.walletbyhei.dbConnection.ConnectionToDb;

public class Main {
  public static void main(String[] args) {
    ConnectionToDb.getConnection();
  }
}
