package com.gopichand.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:mysql://localhost:3306/gopidb";
    private static final String USER = "root";
    private static final String PASSWORD = "gopi";
    
    static {
        try {
            // Load the driver only once
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load MySQL driver", e);
        }
    }
    
    /**
     * Get a fresh database connection.
     * Each call creates a new connection that must be closed by the caller.
     */
    public static Connection getConnection() {
        try {
            // Create a new connection each time instead of reusing a static one
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            
            if (conn == null || conn.isClosed()) {
                throw new SQLException("Failed to establish database connection");
            }
            
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection error: " + e.getMessage(), e);
        }
    }
}