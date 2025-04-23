import java.sql.*;
import java.util.ArrayList;

public class DatabaseHelper {
    // 🔧 Replace with your actual database name, user, and password
    private static final String URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    // Load JDBC driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL 8.x+
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Get a database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Fetch all users from the database
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

    // Add a new user to the database
    public static boolean addUser(String name, String email, String username, String contact, String role) {
        String query = "INSERT INTO users (name, email, username, contact, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, username);
            ps.setString(4, contact);
            ps.setString(5, role);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Update a user in the database
    public static boolean updateUser(int userId, String name, String email, String username, String contact, String role) {
        String query = "UPDATE users SET name=?, email=?, username=?, contact=?, role=? WHERE user_id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, username);
            ps.setString(4, contact);
            ps.setString(5, role);
            ps.setInt(6, userId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Delete a user by ID
    public static boolean deleteUser(int userId) {
        String query = "DELETE FROM users WHERE user_id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}