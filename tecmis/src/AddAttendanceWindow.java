package tecmis.src;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddAttendanceWindow extends JFrame {

    private JTextField idField, studentIdField, courseCodeField, courseTypeField, dateField, attendanceField, medicalStatusField, sessionNoField;
    private JButton saveButton;
    private ManageAttendanceWindow parentWindow;
    private String username;

    public AddAttendanceWindow(String username, ManageAttendanceWindow parentWindow) {
        this.username = username;
        this.parentWindow = parentWindow;

        setTitle("Add Attendance");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(9, 2, 10, 10));

        idField = new JTextField();
        studentIdField = new JTextField();
        courseCodeField = new JTextField();
        courseTypeField = new JTextField();
        dateField = new JTextField();
        attendanceField = new JTextField();
        medicalStatusField = new JTextField();
        sessionNoField = new JTextField();
        saveButton = new JButton("Save");

        add(new JLabel("Attendance ID:"));
        add(idField);
        add(new JLabel("Student ID:"));
        add(studentIdField);
        add(new JLabel("Course Code:"));
        add(courseCodeField);
        add(new JLabel("Course Type:"));
        add(courseTypeField);
        add(new JLabel("Date (YYYY-MM-DD):"));
        add(dateField);
        add(new JLabel("Attendance:"));
        add(attendanceField);
        add(new JLabel("Medical Status:"));
        add(medicalStatusField);
        add(new JLabel("Session No:"));
        add(sessionNoField);
        add(new JLabel(""));
        add(saveButton);

        saveButton.addActionListener(e -> saveAttendance());

        setVisible(true);
    }

    private void saveAttendance() {
        try {
            Connection con = DBConnection.getConnection();
            String query = "INSERT INTO Attendance (attendance_id, at_undergraduate_id, at_course_code, at_course_type, date, attendence, medical_status, session_no, at_to_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, idField.getText());
            pst.setString(2, studentIdField.getText());
            pst.setString(3, courseCodeField.getText());
            pst.setString(4, courseTypeField.getText());
            pst.setString(5, dateField.getText());
            pst.setString(6, attendanceField.getText());
            pst.setString(7, medicalStatusField.getText());
            pst.setInt(8, Integer.parseInt(sessionNoField.getText()));
            pst.setString(9, username); // TO's user ID

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Attendance Added Successfully!");

            pst.close();
            con.close();

            parentWindow.loadAttendanceData(); // Refresh table
            dispose(); // Close window

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding attendance: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
