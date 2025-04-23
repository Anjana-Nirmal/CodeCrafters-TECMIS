import javax.swing.*;

public class AdminDashBord extends JFrame {
    private JButton userProfilesButton;
    private JButton coursesButton;
    private JButton noticesButton;
    private JButton timeTablesButton;
    private JButton backButton;
    private JButton exitButton;
    private JPanel main;

    AdminDashBord() {
        setTitle("Admin Dash Bord");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(main);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AdminDashBord();
    }
}
