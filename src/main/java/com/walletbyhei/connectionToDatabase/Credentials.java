package com.walletbyhei.connectionToDatabase;

public class Credentials {
    public static final String URL = "jdbc:postgresql://localhost/transport_booking";
    public static final String USER = System.getenv("PSQL_USER");
    public static final String PASSWORD = System.getenv("PSQL_PASSWORD");
}
