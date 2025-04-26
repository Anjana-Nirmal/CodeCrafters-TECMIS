import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Admin_Dash extends JFrame {
    private JPanel Main;
    private JButton btnuserprofiles;
    private JButton btncourses;
    private JButton btnnotices;
    private JButton btntimetables;
    private JButton btnlogout;

    public Admin_Dash() {
        setTitle("Admin Dashboard");
        setContentPane(Main);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setVisible(true);

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

        btnlogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        Main,
                        "Are you sure you want to logout?",
                        "Logout Confirmation",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    JFrame frame = new JFrame("Login");
                    frame.setContentPane(new Login().getMainPanel());
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                    dispose();
                }
            }
        });
    }

    public static void main(String[] args) {
        new Admin_Dash();
    }
}