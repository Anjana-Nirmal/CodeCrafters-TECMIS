import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateProfile extends JFrame{
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JTextField textField6;
    private JButton clearButton;
    private JButton updateButton;
    private JLabel label6;
    private JPanel main;
    private JLabel user;
    public static String Username;
    UpdateProfile(String Username)
    {
        DatabaseConnection databaseConnection=new DatabaseConnection();

        this.Username=Username;


        setContentPane(main);
        setTitle("Profile Update");
        setSize(400,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        user.setBorder(new LineBorder(Color.BLACK, 2));
        setVisible(true);

        textField6.setText(Username);
        textField6.setEditable(false);
        Connection conn=DatabaseConnection.getConnection();
        String sql="SELECT Lecturer.Lecturer_Id,User.Name,User.Email,User.Contact,Lecturer.Department,User.Username\n" +
                "FROM Lecturer\n" +
                "JOIN User\n" +
                "ON Lecturer.Lecturer_Id=User.User_Id\n" +
                "WHERE User.Role='lecturer' AND User.Username=?;\n";

        try {
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,textField6.getText() );

            ResultSet rs=pstmt.executeQuery();
            while (rs.next())
            {
                String lecID=rs.getString(1);
                String lecName=rs.getString(2);
                String Email=rs.getString(3);
                int Tp=rs.getInt(4);
                String Department=rs.getString(5);

                textField1.setText(lecID);
                textField2.setText(lecName);
                textField3.setText(Email);
                textField4.setText(String.valueOf(Tp));
                textField5.setText(Department);
            }
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        //setVisible(true);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Update();
            }
        });
    }

    public void clear()
    {
        textField1.setText(" ");
        textField2.setText(" ");
        textField3.setText(" ");
        textField4.setText(" ");
        textField5.setText(" ");

    }
    public void Update()
    {
        String lecid=textField1.getText();
        String lecname=textField2.getText();
        String email=textField3.getText();
        String conNo=textField4.getText();
        String dep=textField5.getText();

        Connection conn=DatabaseConnection.getConnection();
        String sql="UPDATE lecturerdetails SET lecturer_id=?,lecturer_name=?,email=?,contact_no=?,department=? WHERE username=?";
        try{
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,lecid);
            pstmt.setString(2,lecname);
            pstmt.setString(3,email);
            pstmt.setString(4,conNo);
            pstmt.setString(5,dep);
            pstmt.setString(6,Username);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"Details Update Successfully..");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,"Error in Update Details.. ");
        }
    }
    public static void main(String[] args) {
        UpdateProfile updateProfile = new UpdateProfile(Username);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
