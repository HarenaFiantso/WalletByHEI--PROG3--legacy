package com.walletbyhei.connectionToDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDb {
    private static Connection connection;
    public ConnectionToDb(){
        try {
            connection = DriverManager.getConnection(
                    Credentials.URL,
                    Credentials.USER,
                    Credentials.PASSWORD
            );
            System.out.println("Connection Successful");
        } catch (SQLException e){
            System.out.println("Error while connecting to the database : " + e.getMessage());
        }
    }

    public static Connection getConnection(){
        if (connection == null){
            new ConnectionToDb();
        }
        return connection;
    }

}
