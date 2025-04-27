package tecmis.src;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewAttendanceWindow extends JFrame {
    private JTable table;
    private String username;

    public ViewAttendanceWindow(String username) {
        this.username = username;

        setTitle("View Attendance");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadAttendanceData();

        setVisible(true);
    }

    private void loadAttendanceData() {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return;

        try {
            // Get user_id of the undergraduate
            String getUserIdSql = "SELECT user_id FROM User WHERE username = ?";
            PreparedStatement pstUser = conn.prepareStatement(getUserIdSql);
            pstUser.setString(1, username);
            ResultSet rsUser = pstUser.executeQuery();
            String userId = null;
            if (rsUser.next()) {
                userId = rsUser.getString("user_id");
            }

            if (userId == null) {
                JOptionPane.showMessageDialog(this, "User ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Now fetch attendance
            String sql = "SELECT Attendance.date, Attendance.attendence, Attendance.medical_status, Attendance.session_no, Attendance.at_course_code " +
                    "FROM Attendance " +
                    "WHERE Attendance.at_undergraduate_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, userId);
            ResultSet rs = pst.executeQuery();

            // Table model
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Course Code");
            model.addColumn("Date");
            model.addColumn("Attendance");
            model.addColumn("Medical Status");
            model.addColumn("Session No");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("at_course_code"),
                        rs.getDate("date"),
                        rs.getString("attendence"),
                        rs.getString("medical_status"),
                        rs.getInt("session_no")
                });
            }

            table.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
