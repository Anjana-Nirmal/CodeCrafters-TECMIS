import java.sql.*;
import java.util.ArrayList;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    // Load MySQL JDBC driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL 8.x driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Get DB connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Fetch user data
    public static ArrayList<Object[]> fetchUsers() {
        ArrayList<Object[]> users = new ArrayList<>();
        String query = "SELECT user_id, name, email, username, contact, role, date_created FROM users";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("contact"),
                        rs.getString("role"),
                        rs.getDate("date_created")
                };
                users.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}