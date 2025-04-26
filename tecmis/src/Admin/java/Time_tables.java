import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Time_tables {
    public JPanel Main;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton backButton;
    private JTable table1;

    public Time_tables() {
        loadTimetable();

        addButton.addActionListener(e -> {
            try {
                String id = JOptionPane.showInputDialog("Enter Timetable ID:");
                if (id == null || id.isEmpty()) return;

                String day = JOptionPane.showInputDialog("Enter Day:");
                if (day == null || day.isEmpty()) return;

                String time = JOptionPane.showInputDialog("Enter Time Range:");
                if (time == null || time.isEmpty()) return;

                String course = JOptionPane.showInputDialog("Enter Course Code:");
                if (course == null || course.isEmpty()) return;

                String type = JOptionPane.showInputDialog("Enter Course Type:");
                if (type == null || type.isEmpty()) return;

                String lecturer = JOptionPane.showInputDialog("Enter Lecturer ID:");
                if (lecturer == null || lecturer.isEmpty()) return;

                Connection conn = Connector.getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO Timetable VALUES (?, ?, ?, ?, ?, ?)");
                ps.setString(1, id);
                ps.setString(2, day);
                ps.setString(3, time);
                ps.setString(4, course);
                ps.setString(5, type);
                ps.setString(6, lecturer);

                ps.executeUpdate();
                ps.close();
                conn.close();

                JOptionPane.showMessageDialog(Main, "Entry added successfully!");
                loadTimetable();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(Main, "Error Adding Entry: " + ex.getMessage());
            }
        });

        editButton.addActionListener(e -> {
            try {
                String id = JOptionPane.showInputDialog("Enter Timetable ID to Edit:");
                if (id == null || id.isEmpty()) return;

                String newDay = JOptionPane.showInputDialog("Enter New Day:");
                String newTime = JOptionPane.showInputDialog("Enter New Time Range:");
                String newCourse = JOptionPane.showInputDialog("Enter New Course Code:");
                String newType = JOptionPane.showInputDialog("Enter New Course Type:");
                String newLecturer = JOptionPane.showInputDialog("Enter New Lecturer ID:");

                Connection conn = Connector.getConnection();
                PreparedStatement ps = conn.prepareStatement(
                        "UPDATE Timetable SET day=?, time_range=?, course_code=?, course_type=?, lecturer_id=? WHERE Timetable_id=?"
                );
                ps.setString(1, newDay);
                ps.setString(2, newTime);
                ps.setString(3, newCourse);
                ps.setString(4, newType);
                ps.setString(5, newLecturer);
                ps.setString(6, id);

                int updated = ps.executeUpdate();
                ps.close();
                conn.close();

                if (updated > 0) {
                    JOptionPane.showMessageDialog(Main, "Entry updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(Main, "Timetable ID not found.");
                }

                loadTimetable();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(Main, "Error Updating Entry: " + ex.getMessage());
            }
        });

        deleteButton.addActionListener(e -> {
            try {
                String id = JOptionPane.showInputDialog("Enter Timetable ID to Delete:");
                if (id == null || id.isEmpty()) return;

                Connection conn = Connector.getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM Timetable WHERE Timetable_id=?");
                ps.setString(1, id);

                int deleted = ps.executeUpdate();
                ps.close();
                conn.close();

                if (deleted > 0) {
                    JOptionPane.showMessageDialog(Main, "Entry deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(Main, "Timetable ID not found.");
                }

                loadTimetable();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(Main, "Error Deleting Entry: " + ex.getMessage());
            }
        });

        backButton.addActionListener(e -> {
            new Admin_Dash().setVisible(true);
            SwingUtilities.getWindowAncestor(Main).dispose();
        });
    }

    private void loadTimetable() {
        try {
            Connection conn = Connector.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Timetable_id, day, time_range, course_code, course_type FROM Timetable");

            DefaultTableModel model = new DefaultTableModel(new String[]{"Timetable ID", "Day", "Time", "Subject", "Type"}, 0);

            while (rs.next()) {
                String id = rs.getString("Timetable_id");
                String day = rs.getString("day");
                String time = rs.getString("time_range");
                String subject = rs.getString("course_code");
                String type = rs.getString("course_type");

                model.addRow(new Object[]{id, day, time, subject, type});
            }

            table1.setModel(model);

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(Main, "Error Loading Timetable: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Timetables Management");
        frame.setContentPane(new Time_tables().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}