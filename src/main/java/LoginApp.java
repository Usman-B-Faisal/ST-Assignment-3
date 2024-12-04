package main.java;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginApp {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/login_data";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345678";

    public LoginApp() {

    }
    public String authenticateUser(String email, String password) {
        String userName = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Query to check both email and password
            String query = "SELECT Name FROM User WHERE Email = ? AND Password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                userName = rs.getString("Name");
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userName;
    }

    public static void main(String[] args) {

    }
}

