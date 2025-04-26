import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {
    private JPanel main;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton clearButton;
    private JButton logInButton;
    static String Username;
    String Password;
    Login()
    {
        DatabaseConnection databaseConnection=new DatabaseConnection();
        setTitle("Login");
        setSize(300,140);
        setContentPane(main);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Username=textField1.getText();
                Password=new String(passwordField1.getPassword());
                Connection conn=DatabaseConnection.getConnection();
                String sql="SELECT username,password  FROM user WHERE username=? AND password=? AND role = 'lecturer'";
                try {
                    PreparedStatement pstmt=conn.prepareStatement(sql);
                    pstmt.setString(1,Username);
                    pstmt.setString(2,Password);
                    ResultSet rs=pstmt.executeQuery();
                    if (rs.next())
                    {
                        String user=rs.getString(1);
                        String pass=rs.getString(2);
                        if (user.equals(Username) && pass.equals(Password))
                        {
                            HomepageNew home=new HomepageNew(Username);
                            home.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            home.setVisible(true);
                            dispose();
                        }

                    }
                    else {
                        JOptionPane.showMessageDialog(null,"Invalid Username or Password");
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }

            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText(" ");
                passwordField1.setText(" ");
            }
        });
    }

    public static void main(String[] args) {
        new Login();
    }
}


