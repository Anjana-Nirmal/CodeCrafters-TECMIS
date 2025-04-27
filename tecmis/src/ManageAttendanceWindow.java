package tecmis.src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ManageAttendanceWindow extends JFrame {

    private JTable attendanceTable;
    private DefaultTableModel model;
    private JButton addButton, updateButton, deleteButton;
    private String username;

    public ManageAttendanceWindow(String username) {
        this.username = username;

        setTitle("Manage Attendance");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Attendance Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] columns = {"Attendance ID", "Student ID", "Course Code", "Course Type", "Date", "Attendance", "Medical Status", "Session No"};
        model = new DefaultTableModel(columns, 0);
        attendanceTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(attendanceTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Add Attendance");
        updateButton = new JButton("Update Attendance");
        deleteButton = new JButton("Delete Attendance");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);

        loadAttendanceData(); // Load data

        // Button actions
        addButton.addActionListener(e -> openAddAttendanceWindow());
        updateButton.addActionListener(e -> openUpdateAttendanceWindow());
        deleteButton.addActionListener(e -> deleteSelectedAttendance());
    }

    public void loadAttendanceData() {
        try {
            Connection con = DBConnection.getConnection();

            Statement stmt = con.createStatement();
            String query = "SELECT * FROM Attendance";
            ResultSet rs = stmt.executeQuery(query);

            model.setRowCount(0); // Clear existing rows first

            while (rs.next()) {
                String attendance_id = rs.getString("attendance_id");
                String student_id = rs.getString("at_undergraduate_id");
                String course_code = rs.getString("at_course_code");
                String course_type = rs.getString("at_course_type");
                String date = rs.getString("date");
                String attendance = rs.getString("attendence");
                String medical_status = rs.getString("medical_status");
                int session_no = rs.getInt("session_no");

                model.addRow(new Object[]{attendance_id, student_id, course_code, course_type, date, attendance, medical_status, session_no});
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading attendance data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void openAddAttendanceWindow() {
        new AddAttendanceWindow(username, this); // Pass ManageAttendanceWindow instance
    }

    private void openUpdateAttendanceWindow() {
        int selectedRow = attendanceTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record first!");
            return;
        }
        String attendanceId = model.getValueAt(selectedRow, 0).toString();
        new UpdateAttendanceWindow(username, attendanceId, this); // Pass selected attendance ID
    }

    private void deleteSelectedAttendance() {
        int selectedRow = attendanceTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record first!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this attendance?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String attendanceId = model.getValueAt(selectedRow, 0).toString();
        try {
            Connection con = DBConnection.getConnection();
            String query = "DELETE FROM Attendance WHERE attendance_id = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, attendanceId);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Attendance deleted successfully!");
            model.removeRow(selectedRow); // Remove from table

            pst.close();
            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting attendance: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
