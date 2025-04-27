package tecmis.src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewMarksGPAWindow extends JFrame {
    private JTable table;
    private JLabel gpaLabel;
    private String username;

    public ViewMarksGPAWindow(String username) {
        this.username = username;

        setTitle("View Marks and GPA");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);

        gpaLabel = new JLabel("GPA: Calculating...");
        gpaLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gpaLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(scrollPane, BorderLayout.CENTER);
        add(gpaLabel, BorderLayout.SOUTH);

        loadMarksData();

        setVisible(true);
    }

    private void loadMarksData() {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return;

        try {
            // Get user_id
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

            // Fetch marks + course details
            String sql = "SELECT m.course_code, cu.name, cu.credit, m.type, m.marks " +
                    "FROM marks m JOIN Course_Unit cu ON m.course_code = cu.course_code " +
                    "WHERE m.undergraduate_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, userId);
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Course Code");
            model.addColumn("Course Name");
            model.addColumn("Type");
            model.addColumn("Marks");
            model.addColumn("Grade");
            model.addColumn("Credits");

            double totalWeightedGPA = 0;
            int totalCredits = 0;

            while (rs.next()) {
                String courseCode = rs.getString("course_code");
                String courseName = rs.getString("name");
                String type = rs.getString("type");
                int marks = rs.getInt("marks");
                int credit = rs.getInt("credit");
                String grade = calculateGrade(marks);
                double gradePoint = calculateGradePoint(marks);

                model.addRow(new Object[]{courseCode, courseName, type, marks, grade, credit});

                totalWeightedGPA += gradePoint * credit;
                totalCredits += credit;
            }

            double gpa = totalCredits == 0 ? 0.0 : totalWeightedGPA / totalCredits;
            gpaLabel.setText(String.format("GPA: %.2f", gpa));

            table.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String calculateGrade(int marks) {
        if (marks >= 85) return "A+";
        else if (marks >= 75) return "A";
        else if (marks >= 65) return "B";
        else if (marks >= 55) return "C";
        else if (marks >= 40) return "D";
        else return "F";
    }

    private double calculateGradePoint(int marks) {
        if (marks >= 85) return 4.0;
        else if (marks >= 75) return 3.7;
        else if (marks >= 65) return 3.0;
        else if (marks >= 55) return 2.0;
        else if (marks >= 40) return 1.0;
        else return 0.0;
    }
}

