package com.walletbyhei;

import com.walletbyhei.connectionToDatabase.ConnectionToDb;

public class Main {
  public static void main(String[] args) {
    ConnectionToDb.getConnection();
  }
}
