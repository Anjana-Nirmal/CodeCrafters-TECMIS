import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
 static Connection conn;

    DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tecmisfot", "root", "#CaK8011sg*");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }
    public static Connection getConnection()
    {
        return conn;
    }

    public static void main(String[] args) {
        DatabaseConnection databaseConnection=new DatabaseConnection();

    }
    }

