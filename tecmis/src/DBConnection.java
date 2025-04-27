package tecmis.src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    public class DBConnection {
        private static final String URL = "jdbc:mysql://localhost:3306/tecmis";
        private static final String USERNAME = "root"; //db username
        private static final String PASSWORD = "";     //db password

        public static Connection getConnection() {
            try {
                return DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                System.out.println("Database Connection Failed!");
                e.printStackTrace();
                return null;
            }
        }
    }
