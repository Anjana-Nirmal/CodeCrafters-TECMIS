import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Admin_Dash extends JFrame {
    private JPanel Main;
    private JButton btnuserprofiles; // Opens User Profiles Management GUI
    private JButton btncourses;      // Opens Courses Management GUI
    private JButton btnnotices;      // Opens Notices Management GUI
    private JButton btntimetables;   // Opens Time Tables Management GUI
    private JButton btnback;         // Goes back or logs out
    private JButton btnexit;         // Exits application

    public Admin_Dash() {
        setTitle("Admin Dashboard");
        setContentPane(Main);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);

        // 🚀 When this is clicked, open the User Profiles GUI
        btnuserprofiles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Replace this with:
                // new UserProfilesGUI().setVisible(true);
                JOptionPane.showMessageDialog(null, "User Profiles Clicked");
            }
        });

        // 🚀 When this is clicked, open the Courses Management GUI
        btncourses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Replace this with:
                // new CoursesGUI().setVisible(true);
                JOptionPane.showMessageDialog(null, "Courses Clicked");
            }
        });

        // 🚀 When this is clicked, open the Notices Management GUI
        btnnotices.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Replace this with:
                // new NoticesGUI().setVisible(true);
                JOptionPane.showMessageDialog(null, "Notices Clicked");
            }
        });

        // 🚀 When this is clicked, open the Time Tables GUI
        btntimetables.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Replace this with:
                // new TimeTablesGUI().setVisible(true);
                JOptionPane.showMessageDialog(null, "Time Tables Clicked");
            }
        });

        // 🔙 This can log out the user or go back to a previous screen
        btnback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // You could go back to login screen:
                // new LoginScreen().setVisible(true);
                // dispose(); // to close this window
                JOptionPane.showMessageDialog(null, "Back Clicked");
            }
        });

        // ❌ Exit confirmation dialog
        btnexit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to exit?", "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    public static void main(String[] args) {
        new Admin_Dash();
    }
}
