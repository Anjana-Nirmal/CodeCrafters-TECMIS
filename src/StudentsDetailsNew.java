import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentsDetailsNew extends JFrame {
    private JButton batchButton;
    private JButton individualButton;
    private JPanel main2;
    private JTextField textField1;
    private JPanel BatchDetails;
    private JTextField textField2;
    private JTextField textField3;
    private JTable table1;
    private JButton viewButton;
    private JPanel IndividualDetails;
    private JButton viewButton1;
    private JPanel main;
    private JPanel BatchTable;
    private JTable table2;
    private JPanel IndividualTable;
    private JLabel student;
    private JLabel StudentDetailsTheame;
    private JPanel studentPanel;
    private JScrollPane IndiTable;


    public StudentsDetailsNew()
    {
        DatabaseConnection databaseConnection=new DatabaseConnection();
        setTitle("Student Details");
        setSize(400,400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(main);
        ImageIcon icon=new ImageIcon("C:\\Users\\pc\\OneDrive\\Desktop\\Test\\src\\student.png");
        Image img=icon.getImage();
        Image scaledImg=img.getScaledInstance(100,100,Image.SCALE_SMOOTH);
        student.setIcon(new ImageIcon(scaledImg));
        studentPanel.setPreferredSize(new Dimension(400,100));
        BatchTable.setPreferredSize(new Dimension(400,342));
        IndividualTable.setPreferredSize(new Dimension(400,70));



        BatchDetails.setVisible(false);
        IndividualDetails.setVisible(false);
        BatchTable.setVisible(false);
        IndividualTable.setVisible(false);
        setVisible(true);
        batchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BatchDetails.setVisible(!BatchDetails.isVisible());
            }
        });
        individualButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IndividualDetails.setVisible(!IndividualDetails.isVisible());
            }
        });
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IndividualTable.setVisible(false);
                String course=textField1.getText();
                Connection conn=DatabaseConnection.getConnection();
                String sql="SELECT Student_enrollment.undergraduate_id,user.name,user.email,user.contact \n" +
                        "FROM user\n" +
                        "JOIN Student_enrollment\n" +
                        "ON Student_enrollment.undergraduate_id=user.user_id\n" +
                        "WHERE Student_enrollment.course_code=? AND user.role='undergraduate'";
                if(course.isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"You must have to fill the field first..");
                    return;
                }
                try
                {
                    PreparedStatement pstmt=conn.prepareStatement(sql);
                    pstmt.setString(1,course);
                    ResultSet rs=pstmt.executeQuery();
                    table1.setModel(DbUtils.resultSetToTableModel(rs));
                }
                catch (SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Error retrive data...");
                }
                BatchTable.setVisible(! BatchTable.isVisible());
            }
        });
        viewButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BatchTable.setVisible(false);
                String course=textField2.getText();
                String Id=textField3.getText();
                Connection conn=DatabaseConnection.getConnection();
                String sql="SELECT Student_enrollment.undergraduate_id,user.name,user.email,user.contact\n" +
                        "FROM user\n" +
                        "JOIN Student_enrollment\n" +
                        "ON Student_enrollment.undergraduate_id=user.user_id\n" +
                        "WHERE Student_enrollment.course_code=? AND Student_enrollment.undergraduate_id=? AND user.role='undergraduate'";
                if(course.isEmpty() || Id.isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"You must have to fill the all fields ..");
                    return;
                }
                try
                {
                    PreparedStatement pstmt=conn.prepareStatement(sql);
                    pstmt.setString(1,course);
                    pstmt.setString(2,Id);
                    ResultSet rs=pstmt.executeQuery();
                    table2.setModel(DbUtils.resultSetToTableModel(rs));
                }
                catch (SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Error retrive data...");
                }


                IndividualTable.setVisible(! IndividualTable.isVisible());
            }
        });
    }

    public static void main(String[] args) {
        new StudentsDetailsNew();
    }
}
