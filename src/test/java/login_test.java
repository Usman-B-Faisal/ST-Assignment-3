package test.java;

import org.junit.jupiter.api.*;
import java.sql.*;
import main.java.LoginApp;

import static org.junit.jupiter.api.Assertions.*;

public class login_test {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/login_data"; // MySQL database
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345678";

    private Connection connection;

    @Test
    void testSuccessfulLogin() throws Exception {
        LoginApp loginApp = new LoginApp();
        String userName = loginApp.authenticateUser("johndoe@example.com", "password123");

        assertNotNull(userName, "User should be authenticated successfully.");
        assertEquals("John Doe", userName, "Authenticated user name should match.");
        assertDoesNotThrow(() -> DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD), "Database connection should not throw exceptions.");
    }

    @Test
    void testInvalidEmail() throws Exception {
        LoginApp loginApp = new LoginApp();
        String userName = loginApp.authenticateUser("invalid@example.com", "password123");

        assertNull(userName, "User with invalid email should not be authenticated.");
        assertDoesNotThrow(() -> DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD), "Database connection should not throw exceptions.");
    }

    @Test
    void testInvalidPassword() throws Exception {
        LoginApp loginApp = new LoginApp();
        String userName = loginApp.authenticateUser("johndoe@example.com", "wrongpassword");

        assertNull(userName, "User with incorrect password should not be authenticated.");
        assertDoesNotThrow(() -> DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD), "Database connection should not throw exceptions.");
    }

    @Test
    void testEmptyEmailAndPassword() throws Exception {
        LoginApp loginApp = new LoginApp();
        String userName = loginApp.authenticateUser("", "");

        assertNull(userName, "Empty email and password should not authenticate the user.");
        assertDoesNotThrow(() -> DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD), "Database connection should not throw exceptions.");
    }

    @Test
    void testSQLInjection() throws Exception {
        LoginApp loginApp = new LoginApp();
        String userName = loginApp.authenticateUser("johndoe@example.com' OR '1'='1", "password123");

        assertNull(userName, "SQL Injection attempt should not authenticate the user.");
        assertDoesNotThrow(() -> DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD), "Database connection should not throw exceptions.");
    }
}
