import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Course {
    public JPanel Main;
    private JButton addCourseButton;
    private JButton editCourseButton;
    private JButton deleteCourseButton;
    private JButton searchCourseButton;
    private JTable table1;

    public Course() {
        // Column headers for your Course_Unit table
        String[] columns = {"Course Code", "Course Name", "Type", "Credits", "Lecturer ID"};

        // Example static data (to be replaced with DB data later)
        Object[][] data = {
                {"CSC101", "Intro to CS", "Core", 3, "LEC001"},
                {"MAT201", "Discrete Math", "Elective", 2, "LEC002"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns);
        table1.setModel(model);

        // Add button listeners
        addCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Main, "Add Course Clicked");
            }
        });

        editCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Main, "Edit Course Clicked");
            }
        });

        deleteCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Main, "Delete Course Clicked");
            }
        });

        searchCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Main, "Search Course Clicked");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Course Management");
        frame.setContentPane(new Course().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
