import Database.DatabaseConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Add_Attendance extends JFrame {
    private JPanel mainPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JButton addButton;

    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    public  Add_Attendance() {
        setContentPane(mainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(300,300);
        setVisible(true);

    }

    public void addAttendance() {
//        Connection con = null;
//        PreparedStatement pst = null;
//        ResultSet rs = null;

        Connection con = DatabaseConnection.connect();

        try {
            String sql = "SELECT * FROM attendance";

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Add_Attendance();
    }
}
