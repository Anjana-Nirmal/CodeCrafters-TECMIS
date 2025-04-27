package tecmis.src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UndergraduateDashboard extends JFrame implements ActionListener {

    private JButton profileButton, attendanceButton, medicalButton, coursesButton, marksButton, timetableButton, noticesButton, logoutButton;
    private String username;

    public UndergraduateDashboard(String username) {
        this.username = username;

        setTitle("Undergraduate Dashboard");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 1, 10, 10));

        profileButton = new JButton("Update Profile");
        attendanceButton = new JButton("View Attendance");
        medicalButton = new JButton("View Medical Records");
        coursesButton = new JButton("View Courses");
        marksButton = new JButton("View Marks and GPA");
        timetableButton = new JButton("View Timetables");
        noticesButton = new JButton("View Notices");
        logoutButton = new JButton("Logout");


        profileButton.addActionListener(this);
        attendanceButton.addActionListener(this);
        medicalButton.addActionListener(this);
        coursesButton.addActionListener(this);
        marksButton.addActionListener(this);
        timetableButton.addActionListener(this);
        noticesButton.addActionListener(this);
        logoutButton.addActionListener(this);


        add(profileButton);
        add(attendanceButton);
        add(medicalButton);
        add(coursesButton);
        add(marksButton);
        add(timetableButton);
        add(noticesButton);
        add(logoutButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == profileButton) {
            new ProfileUpdateWindow(username);
        }
    else if (e.getSource() == attendanceButton) {
            new ViewAttendanceWindow(username);
        }
    else if (e.getSource() == medicalButton) {
            new ViewMedicalsWindow(username);
        }
    else if (e.getSource() == coursesButton) {
            new ViewCoursesWindow(username);
        }
    else if (e.getSource() == marksButton) {
            new ViewMarksGPAWindow(username);
        }
    else if (e.getSource() == timetableButton) {
            new ViewTimetablesWindow(username);
        }
    else if (e.getSource() == noticesButton) {
            new ViewNoticesWindow(username);
        }
    else if (e.getSource() == logoutButton) {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose(); // Close the dashboard
            new LoginWindow(); // Open login window again
            }
        }


    }
}

