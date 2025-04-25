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
    private JTable table1;

    public Time_tables() {
        // Set default empty table model including Timetable ID
        String[] columns = {"Timetable ID", "Day", "Time", "Subject", "Type"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table1.setModel(model);

        // 🔥 Load timetable immediately when app starts
        loadTimetable();

        // ADD Button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = JOptionPane.showInputDialog("Enter Timetable ID:");
                    String day = JOptionPane.showInputDialog("Enter Day:");
                    String time = JOptionPane.showInputDialog("Enter Time Range:");
                    String course = JOptionPane.showInputDialog("Enter Course Code:");
                    String type = JOptionPane.showInputDialog("Enter Course Type:");
                    String lecturer = JOptionPane.showInputDialog("Enter Lecturer ID:");

                    Connection conn = Connector.getConnection();
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO Timetable VALUES (?, ?, ?, ?, ?, ?)");
                    ps.setString(1, id);
                    ps.setString(2, day);
                    ps.setString(3, time);
                    ps.setString(4, course);
                    ps.setString(5, type);
                    ps.setString(6, lecturer);

                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(Main, "Entry added.");
                    ps.close();
                    conn.close();

                    loadTimetable(); // Refresh timetable

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Main, "Add Error: " + ex.getMessage());
                }
            }
        });

        // EDIT Button
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = JOptionPane.showInputDialog("Enter Timetable ID to Edit:");
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
                    JOptionPane.showMessageDialog(Main, updated + " row(s) updated.");
                    ps.close();
                    conn.close();

                    loadTimetable(); // Refresh timetable

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Main, "Edit Error: " + ex.getMessage());
                }
            }
        });

        // DELETE Button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = JOptionPane.showInputDialog("Enter Timetable ID to Delete:");

                    Connection conn = Connector.getConnection();
                    PreparedStatement ps = conn.prepareStatement("DELETE FROM Timetable WHERE Timetable_id=?");
                    ps.setString(1, id);

                    int deleted = ps.executeUpdate();
                    JOptionPane.showMessageDialog(Main, deleted + " row(s) deleted.");
                    ps.close();
                    conn.close();

                    loadTimetable(); // Refresh timetable

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Main, "Delete Error: " + ex.getMessage());
                }
            }
        });
    }

    // 🔥 Method to load timetable from database into JTable
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
            JOptionPane.showMessageDialog(Main, "Load Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Time Tables Management");
        frame.setContentPane(new Time_tables().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}