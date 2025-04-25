import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Admin_Dash extends JFrame {
    private JPanel Main;
    private JButton btnuserprofiles;
    private JButton btncourses;
    private JButton btnnotices;
    private JButton btntimetables;
    private JButton btnback;
    private JButton btnexit;

    public Admin_Dash() {
        setTitle("Admin Dashboard");
        setContentPane(Main);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        // 🚀 Open User Profiles
        btnuserprofiles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("User Profiles");
                frame.setContentPane(new UserProfile().Main);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(900, 600);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        // 🚀 Open Courses Management
        btncourses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Courses Management");
                frame.setContentPane(new Course().Main);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(850, 450);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        // 🚀 Open Notices Management
        btnnotices.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Notices Management");
                frame.setContentPane(new Notices().Main);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(800, 400);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        // 🚀 Open Time Tables Management
        btntimetables.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Time Tables Management");
                frame.setContentPane(new Time_tables().Main);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(800, 400);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        // 🔙 Back Button
        btnback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Main, "Back button clicked. You can add logout logic here.");
                // Example: new LoginScreen().setVisible(true);
                // dispose(); // Close this window
            }
        });

        // ❌ Exit Button
        btnexit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(Main,
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