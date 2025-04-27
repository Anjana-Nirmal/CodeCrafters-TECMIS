package tecmis.src;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateAttendanceWindow extends JFrame {

    private JTextField attendanceField, medicalStatusField;
    private JButton updateButton;
    private String attendanceId;
    private ManageAttendanceWindow parentWindow;
    private String username;

    public UpdateAttendanceWindow(String username, String attendanceId, ManageAttendanceWindow parentWindow) {
        this.username = username;
        this.attendanceId = attendanceId;
        this.parentWindow = parentWindow;

        setTitle("Update Attendance");
        setSize(300, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        attendanceField = new JTextField();
        medicalStatusField = new JTextField();
        updateButton = new JButton("Update");

        add(new JLabel("Attendance (Present/Absent):"));
        add(attendanceField);
        add(new JLabel("Medical Status (Optional):"));
        add(medicalStatusField);
        add(new JLabel(""));
        add(updateButton);

        updateButton.addActionListener(e -> updateAttendance());

        setVisible(true);
    }

    private void updateAttendance() {
        try {
            Connection con = DBConnection.getConnection();
            String query = "UPDATE Attendance SET attendence = ?, medical_status = ? WHERE attendance_id = ?";
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, attendanceField.getText());
            pst.setString(2, medicalStatusField.getText());
            pst.setString(3, attendanceId);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Attendance Updated Successfully!");

            pst.close();
            con.close();

            parentWindow.loadAttendanceData(); // Refresh
            dispose(); // Close

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating attendance: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
