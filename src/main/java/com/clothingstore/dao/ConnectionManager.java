package com.clothingstore.dao;

import com.clothingstore.util.DBConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DBConfig.getUrl(),
                DBConfig.getUsername(),
                DBConfig.getPassword()
        );
    }

    public static void closeQuietly(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception ignored) {}
        }
    }
}