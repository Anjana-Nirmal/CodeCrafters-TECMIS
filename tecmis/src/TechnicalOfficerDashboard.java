package tecmis.src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TechnicalOfficerDashboard extends JFrame {

    private JButton updateProfileButton;
    private JButton manageAttendanceButton;
    private JButton manageMedicalButton;
    private JButton viewNoticesButton;
    private JButton viewTimetablesButton;

    private String technicalOfficerUsername; // Store logged-in officer's username

    public TechnicalOfficerDashboard(String username) {
        this.technicalOfficerUsername = username; // Assign the username

        setTitle("Technical Officer Dashboard");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        updateProfileButton = new JButton("Update Profile");
        updateProfileButton.setBounds(100, 30, 200, 30);
        add(updateProfileButton);

        manageAttendanceButton = new JButton("Manage Attendance");
        manageAttendanceButton.setBounds(100, 80, 200, 30);
        add(manageAttendanceButton);

        manageMedicalButton = new JButton("Manage Medicals");
        manageMedicalButton.setBounds(100, 130, 200, 30);
        add(manageMedicalButton);

        viewNoticesButton = new JButton("View Notices");
        viewNoticesButton.setBounds(100, 180, 200, 30);
        add(viewNoticesButton);

        viewTimetablesButton = new JButton("View Timetables");
        viewTimetablesButton.setBounds(100, 230, 200, 30);
        add(viewTimetablesButton);

        // Button Actions
        updateProfileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ProfileUpdateWindow(technicalOfficerUsername);
            }
        });

        manageAttendanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ManageAttendanceWindow(technicalOfficerUsername);
            }
        });

        manageMedicalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ManageMedicalsWindow(technicalOfficerUsername);
            }
        });

        viewNoticesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ViewNoticesWindow(technicalOfficerUsername);
            }
        });

        viewTimetablesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ViewTimetablesWindow(technicalOfficerUsername);
            }
        });

        setVisible(true);
    }
}
