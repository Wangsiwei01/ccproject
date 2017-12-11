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
    
    public static boolean LoginCheck(String username, String pass) {
        if (username == null || pass == null) {
            throw new IllegalArgumentException("Username and Password can not be null!");
        }
        Connection conn = null;       
        String password = PasswordHashing.getPasswordAfterHashing(pass);
        
        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, "ccproject", "12345678");
            
            System.out.println("Creating statement...");  
            PreparedStatement selectPassword = conn.prepareStatement("Select password from finalproject.user where username = ?");
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
    
    
    public static boolean UserSignUp(String username, String pass, String email, String phone) {
        if (username == null || pass == null) {
            throw new IllegalArgumentException("Username and Password can not be null!");
        }
        Connection conn = null;
        String password = PasswordHashing.getPasswordAfterHashing(pass);
        
        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, "ccproject", "12345678");
            
            //TODO check existing and insert
            
            System.out.println("Creating statement to check if exsiting...");  
            PreparedStatement selectUser = conn.prepareStatement("Select * from finalproject.user where username = ?");
            selectUser.setString(1, username);
            ResultSet rs = selectUser.executeQuery();
            if (rs.next()) {
                System.out.println("User already exsits!");  
                return false;
            }
            System.out.println("Creating statement to insert...");  
            
            PreparedStatement insertNewUser = conn.prepareStatement("Insert into finalproject.user (username, password, email, phone) values (?, ?, ?, ?)");
            insertNewUser.setString(1, username);
            insertNewUser.setString(2, password);
            insertNewUser.setString(3, email);
            insertNewUser.setString(4, phone);
            int rs2 = insertNewUser.executeUpdate();
            System.out.println("Inserted! " + rs2 + "rows added!");  
            return true;
            
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
