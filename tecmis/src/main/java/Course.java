import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class Course {
    public JPanel Main;
    private JButton addCourseButton;
    private JButton editCourseButton;
    private JButton deleteCourseButton;
    private JButton searchCourseButton;
    private JButton refreshButton;
    private JButton backButton; // ✅ new back button
    private JTable table1;

    public Course() {
        loadCourses();

        // ➕ Add Course
        addCourseButton.addActionListener(e -> {
            JTextField codeField = new JTextField();
            JTextField nameField = new JTextField();
            JTextField typeField = new JTextField();
            JTextField creditField = new JTextField();
            JTextField lecturerIdField = new JTextField();

            Object[] fields = {
                    "Course Code:", codeField,
                    "Course Name:", nameField,
                    "Type (Core/Elective):", typeField,
                    "Credits (1-3):", creditField,
                    "Lecturer ID:", lecturerIdField
            };

            int result = JOptionPane.showConfirmDialog(null, fields, "Add Course", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    int credit = Integer.parseInt(creditField.getText());
                    boolean success = addCourse(
                            codeField.getText(),
                            nameField.getText(),
                            typeField.getText(),
                            credit,
                            lecturerIdField.getText()
                    );

                    if (success) {
                        JOptionPane.showMessageDialog(Main, "Course added successfully.");
                        loadCourses();
                    } else {
                        JOptionPane.showMessageDialog(Main, "Failed to add course.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Main, "Credit must be a number between 1 and 3.");
                }
            }
        });

        // ✏ Edit Course
        editCourseButton.addActionListener(e -> {
            int row = table1.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(Main, "Please select a course to edit.");
                return;
            }

            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            String courseCode = model.getValueAt(row, 0).toString();
            String courseName = model.getValueAt(row, 1).toString();
            String type = model.getValueAt(row, 2).toString();
            String credits = model.getValueAt(row, 3).toString();
            String lecturerId = model.getValueAt(row, 4).toString();

            JTextField codeField = new JTextField(courseCode);
            codeField.setEnabled(false);

            JTextField typeField = new JTextField(type);
            typeField.setEnabled(false);

            JTextField nameField = new JTextField(courseName);
            JTextField creditField = new JTextField(credits);
            JTextField lecturerIdField = new JTextField(lecturerId);

            Object[] fields = {
                    "Course Code (not editable):", codeField,
                    "Type (not editable):", typeField,
                    "Course Name:", nameField,
                    "Credits:", creditField,
                    "Lecturer ID:", lecturerIdField
            };

            int result = JOptionPane.showConfirmDialog(null, fields, "Edit Course", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    int credit = Integer.parseInt(creditField.getText());
                    boolean success = updateCourse(
                            courseCode,
                            type,
                            nameField.getText(),
                            credit,
                            lecturerIdField.getText()
                    );

                    if (success) {
                        JOptionPane.showMessageDialog(Main, "Course updated.");
                        loadCourses();
                    } else {
                        JOptionPane.showMessageDialog(Main, "Update failed.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Main, "Credit must be a valid number.");
                }
            }
        });

        // ❌ Delete Course
        deleteCourseButton.addActionListener(e -> {
            int row = table1.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(Main, "Please select a course to delete.");
                return;
            }

            String courseCode = table1.getValueAt(row, 0).toString();
            String type = table1.getValueAt(row, 2).toString();

            int confirm = JOptionPane.showConfirmDialog(Main, "Delete this course?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = deleteCourse(courseCode, type);
                if (success) {
                    JOptionPane.showMessageDialog(Main, "Course deleted.");
                    loadCourses();
                } else {
                    JOptionPane.showMessageDialog(Main, "Delete failed.");
                }
            }
        });

        // 🔎 Search Course
        searchCourseButton.addActionListener(e -> {
            String keyword = JOptionPane.showInputDialog(Main, "Enter course code, name, or type to search:");
            if (keyword != null && !keyword.trim().isEmpty()) {
                searchCourses(keyword.trim());
            }
        });

        // 🔄 Refresh Table
        refreshButton.addActionListener(e -> loadCourses());

        // 🔙 Back Button
        backButton.addActionListener(e -> {
            new Admin_Dash().setVisible(true); // Open Admin Dashboard
            SwingUtilities.getWindowAncestor(Main).dispose(); // Close this window
        });
    }

    // 🔁 Load all courses
    private void loadCourses() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Course Code", "Course Name", "Type", "Credits", "Lecturer ID"});

        String query = "SELECT * FROM Course_Unit";

        try (Connection conn = Connector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("course_code"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getInt("credit"),
                        rs.getString("c_lecturer_id")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(Main, "Error loading courses:\n" + e.getMessage());
        }

        table1.setModel(model);
    }

    // 🔍 Search courses
    private void searchCourses(String keyword) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Course Code", "Course Name", "Type", "Credits", "Lecturer ID"});

        String query = "SELECT * FROM Course_Unit WHERE course_code LIKE ? OR name LIKE ? OR type LIKE ?";

        try (Connection conn = Connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            String likeQuery = "%" + keyword + "%";
            ps.setString(1, likeQuery);
            ps.setString(2, likeQuery);
            ps.setString(3, likeQuery);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("course_code"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getInt("credit"),
                        rs.getString("c_lecturer_id")
                });
            }

            table1.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(Main, "Error during search:\n" + e.getMessage());
        }
    }

    // ➕ Add new course
    private boolean addCourse(String code, String name, String type, int credit, String lecturerId) {
        String query = "INSERT INTO Course_Unit (course_code, name, type, credit, c_lecturer_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, code);
            ps.setString(2, name);
            ps.setString(3, type);
            ps.setInt(4, credit);
            ps.setString(5, lecturerId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(Main, "Error adding course:\n" + e.getMessage());
            return false;
        }
    }

    // ✏ Update course
    private boolean updateCourse(String code, String type, String name, int credit, String lecturerId) {
        String query = "UPDATE Course_Unit SET name=?, credit=?, c_lecturer_id=? WHERE course_code=? AND type=?";

        try (Connection conn = Connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setInt(2, credit);
            ps.setString(3, lecturerId);
            ps.setString(4, code);
            ps.setString(5, type);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(Main, "Error updating course:\n" + e.getMessage());
            return false;
        }
    }

    // ❌ Delete course
    private boolean deleteCourse(String code, String type) {
        String query = "DELETE FROM Course_Unit WHERE course_code=? AND type=?";

        try (Connection conn = Connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, code);
            ps.setString(2, type);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(Main, "Error deleting course:\n" + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Course Management");
        frame.setContentPane(new Course().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}