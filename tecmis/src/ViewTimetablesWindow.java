package tecmis.src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewTimetablesWindow extends JFrame {
    private JTable table;
    private String username;

    public ViewTimetablesWindow(String username) {
        this.username = username;

        setTitle("View Timetable");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        loadTimetableData();

        setVisible(true);
    }

    private void loadTimetableData() {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return;

        try {
            // Get user's department
            String getDeptSql = "SELECT department FROM Undergraduate WHERE undergraduate_id = (SELECT user_id FROM User WHERE username = ?)";
            PreparedStatement pstDept = conn.prepareStatement(getDeptSql);
            pstDept.setString(1, username);
            ResultSet rsDept = pstDept.executeQuery();
            String department = null;
            if (rsDept.next()) {
                department = rsDept.getString("department");
            }

            if (department == null) {
                JOptionPane.showMessageDialog(this, "Department not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Fetch timetable based on department
            String sql = "SELECT day, start_time, end_time, course_name, location FROM Timetable WHERE department = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, department);
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Day");
            model.addColumn("Start Time");
            model.addColumn("End Time");
            model.addColumn("Course Name");
            model.addColumn("Location");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("day"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getString("course_name"),
                        rs.getString("location")
                });
            }

            table.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
