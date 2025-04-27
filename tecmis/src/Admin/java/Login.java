import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class Login {
    private JPanel Main;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JComboBox<String> comboBox1;
    private JButton loginButton;

    public Login() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField1.getText();
                String password = String.valueOf(passwordField1.getPassword());
                String role = (String) comboBox1.getSelectedItem();

                if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Connection con = Connector.getConnection();
                    String sql = "SELECT * FROM user WHERE user_id=? AND password=? AND role=?";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, username);
                    pst.setString(2, password);
                    pst.setString(3, role);

                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Login Successful!");
                        switch (role.toLowerCase()) {
                            case "admin":
                                new Admin_Dash().setVisible(true);
                                break;
//                            case "Technical officer":
//                                new TechnicalOfficerDashboard().setVisible(true);
//                                break;
//                            case "Lecturer":
//                                new LecturerDashboard().setVisible(true);
//                                break;
//                            case "Student":
//                                new StudentDashboard().setVisible(true);
//                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Invalid role selected");
                        }
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(Main);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Username, Password or Role", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return Main;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Login");
                frame.setContentPane(new Login().getMainPanel());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

}