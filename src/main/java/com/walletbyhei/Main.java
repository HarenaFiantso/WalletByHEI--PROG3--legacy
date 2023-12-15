package com.walletbyhei;

import com.walletbyhei.database.ConnectionToDb;

public class Main {
  public static void main(String[] args) {
    ConnectionToDb.getConnection();
  }
}
