package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.example.config.annotation.Component;

@Component
public class DataUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/cafe";
    private static final String USER = "cafeuser";
    private static final String PASSWORD = "cafeuser_password";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("MySQL JDBC Driver not found. " + e.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
