import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Time_tables {
    public JPanel Main;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton viewButton;
    private JTable table1;

    public Time_tables() {
        // Define columns and initial sample data
        String[] columns = {"Day", "Time", "Subject", "Venue"};
        Object[][] data = {
                {"Monday", "9:00 AM", "Mathematics", "Room 101"},
                {"Tuesday", "11:00 AM", "Physics", "Room 202"}
        };

        // Create table model and set it
        DefaultTableModel model = new DefaultTableModel(data, columns);
        table1.setModel(model);

        // Button actions (placeholders)
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Main, "Add Timetable Clicked");
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Main, "Edit Timetable Clicked");
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Main, "Delete Timetable Clicked");
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Main, "View Timetable Clicked");
            }
        });
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