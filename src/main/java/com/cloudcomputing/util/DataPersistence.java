package com.cloudcomputing.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataPersistence {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    private static final String DB_URL = "jdbc:mysql://ccproject.cumbn0blorkz.us-east-1.rds.amazonaws.com";
    
    public static boolean loginCheck(String username, String password) {
        if (username == null || password == null) {
            throw new IllegalArgumentException("Username and Password can not be null!");
        }
        Connection conn = null;
        
        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, "ccproject", "12345678");
            
            System.out.println("Creating statement...");  
            PreparedStatement selectPassword = conn.prepareStatement("Select password from finalproject.user where name = ?");
            selectPassword.setString(1, username);
            ResultSet rs = selectPassword.executeQuery(); 
            while (rs.next()) {
                String passwordInDatabase = rs.getString("password");
                if (password.equals(passwordInDatabase)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }   
}
