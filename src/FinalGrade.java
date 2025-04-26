import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FinalGrade extends JFrame {
    private JPanel panel1;
    private JLabel finalImage;
    private JPanel main2;
    private JButton finalExamResultsButton;
    private JButton finalExamGradesButton;
    private JButton finalSGPA_CGPAButton;
    private JPanel finalTheme;
    private JPanel finalTable;
    private JButton batchButton;
    private JButton individualButton8;
    private JButton batchButton1;
    private JButton individualButton1;
    private JButton batchButton2;
    //private JButton individualButton2;
    private JPanel Hidden1;
    private JPanel Hidden2;
    private JPanel Hidden3;
    private JTextField textField1;
    private JButton viewButton;
    private JTextField textField4;
    private JButton viewButton3;
    private JTextField textField5;
    private JTextField textField6;
    private JButton viewButton5;
    private JTextField textField7;
    private JButton viewButton6;
    private JTextField textField8;
    private JTextField textField9;
    private JButton viewButton8;
    private JPanel finalTable2;
    private JPanel finalTable3;
    private JPanel finalTable4;
    //private JPanel finalTable1;
    //private JPanel finalTable5;
    private JTextField textField2;
    private JTextField textField3;
    private JButton viewButton2;
    private JPanel finalTable7;
    private JTable table1;
    private JTable table2;
    private JTable table3;
    private JTable table4;
    private JTable table5;
    private JTable table6;
    private JPanel info;
    private JPanel info1;
    private JPanel info2;
    private JPanel info3;
    private JPanel info4;
    //private JPanel info5;

    public FinalGrade()
    {
        DatabaseConnection databaseConnection=new DatabaseConnection();
        setTitle("Final Exam Results");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        ImageIcon icon=new ImageIcon("C:\\Users\\pc\\OneDrive\\Desktop\\Test\\src\\exam-results.png");
        Image img=icon.getImage();
        Image scaledImg=img.getScaledInstance(90,90,Image.SCALE_SMOOTH);
        finalImage.setIcon(new ImageIcon(scaledImg));
        Hidden1.setVisible(false);
        Hidden2.setVisible(false);
        Hidden3.setVisible(false);
        finalTable.setVisible(false);
        finalTable7.setVisible(false);
        finalTable2.setVisible(false);
        finalTable3.setVisible(false);
        finalTable4.setVisible(false);
        //finalTable5.setVisible(false);
        info.setVisible(false);
        info1.setVisible(false);
        info2.setVisible(false);
        info3.setVisible(false);
        info4.setVisible(false);
        //info5.setVisible(false);
        info1.setPreferredSize(new Dimension(500,100));
        setContentPane(panel1);
        setVisible(true);
        finalExamResultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Hidden1.setVisible(!Hidden1.isVisible());
            }
        });
        finalExamGradesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Hidden2.setVisible(!Hidden2.isVisible());
            }
        });
        finalSGPA_CGPAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Hidden3.setVisible(!Hidden3.isVisible());
            }
        });
        batchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                finalTable7.setVisible(false);
                finalTable2.setVisible(false);
                finalTable3.setVisible(false);
                finalTable4.setVisible(false);
                //finalTable5.setVisible(false);
                finalTable.setVisible(!finalTable.isVisible());
            }
        });

        batchButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalTable.setVisible(false);
                finalTable7.setVisible(false);
                finalTable3.setVisible(false);
                finalTable4.setVisible(false);
                //finalTable5.setVisible(false);
                finalTable2.setVisible(!finalTable2.isVisible());
            }
        });
        individualButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalTable.setVisible(false);
                finalTable7.setVisible(false);
                finalTable2.setVisible(false);
                finalTable4.setVisible(false);
                //finalTable5.setVisible(false);
                finalTable3.setVisible(!finalTable3.isVisible());
            }
        });
        batchButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalTable.setVisible(false);
                finalTable7.setVisible(false);
                finalTable2.setVisible(false);
                finalTable3.setVisible(false);
                //finalTable5.setVisible(false);
                finalTable4.setVisible(!finalTable4.isVisible());
            }
        });

        individualButton8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalTable.setVisible(false);
                //finalTable5.setVisible(false);
                finalTable2.setVisible(false);
                finalTable3.setVisible(false);
                finalTable4.setVisible(false);
                finalTable7.setVisible(!finalTable7.isVisible());
            }
        });

        viewButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                info.setVisible(false);

                info2.setVisible(false);
                info3.setVisible(false);
                info4.setVisible(false);
                //info5.setVisible(false);

                String courseCode=textField2.getText();
                String id=textField3.getText();
                Connection conn= DatabaseConnection.getConnection();
                String sql="SELECT undergraduate_id,course_code,final_total_out_of_100  FROM final_course_marks_view  WHERE course_code=? AND undergraduate_id=?";
                if(courseCode.isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"You must have to fill the field...");
                    return;
                }

                try {
                    PreparedStatement pstmt=conn.prepareStatement(sql);
                    pstmt.setString(1,courseCode);
                    pstmt.setString(2,id);
                    ResultSet rs=pstmt.executeQuery();
                    table5.setModel(DbUtils.resultSetToTableModel(rs));
                }catch (SQLException ex)
                {
                    System.out.println("Error in executing data...");
                }
                info1.setVisible(!info1.isVisible());
            }
        });
        viewButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                info.setVisible(false);
                info1.setVisible(false);
                //info3.setVisible(false);
                info4.setVisible(false);
                info2.setVisible(false);
                String courseCode=textField4.getText();
                Connection conn= DatabaseConnection.getConnection();
                String sql="SELECT undergraduate_id,course_code,grade FROM final_course_marks_view WHERE course_code=?";
                if(courseCode.isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"You must have to fill the field...");
                    return;
                }

                try {
                    PreparedStatement pstmt=conn.prepareStatement(sql);
                    pstmt.setString(1,courseCode);
                    ResultSet rs=pstmt.executeQuery();
                    table4.setModel(DbUtils.resultSetToTableModel(rs));
                }catch (SQLException ex)
                {
                    System.out.println("Error in executing data...");
                }
                info3.setVisible(!info3.isVisible());
            }
        });
        viewButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                info.setVisible(false);
                info1.setVisible(false);
                //info2.setVisible(false);
                info4.setVisible(false);
                info3.setVisible(false);
                String courseCode=textField5.getText();
                String id=textField6.getText();
                Connection conn= DatabaseConnection.getConnection();
                String sql="SELECT undergraduate_id,course_code,grade FROM final_course_marks_view  WHERE course_code=? AND undergraduate_id=?";
                if(courseCode.isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"You must have to fill the field...");
                    return;
                }

                try {
                    PreparedStatement pstmt=conn.prepareStatement(sql);
                    pstmt.setString(1,courseCode);
                    pstmt.setString(2,id);
                    ResultSet rs=pstmt.executeQuery();
                    table2.setModel(DbUtils.resultSetToTableModel(rs));
                }catch (SQLException ex)
                {
                    System.out.println("Error in executing data...");
                }
                info2.setVisible(!info2.isVisible());
            }
        });
        viewButton6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                info.setVisible(false);
                info1.setVisible(false);
                info2.setVisible(false);
                info3.setVisible(false);
                //info5.setVisible(false);
                Connection conn= DatabaseConnection.getConnection();
                String sql="SELECT undergraduate_id,sgpa,cgpa  FROM student_sgpa_cgpa_view ";


                try {
                    PreparedStatement pstmt=conn.prepareStatement(sql);

                    ResultSet rs=pstmt.executeQuery();
                    table3.setModel(DbUtils.resultSetToTableModel(rs));
                }catch (SQLException ex)
                {
                    System.out.println("Error in executing data...");
                }

                info4.setVisible(!info4.isVisible());
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                info1.setVisible(false);
                info2.setVisible(false);
                info3.setVisible(false);
                info4.setVisible(false);
                //info5.setVisible(false);
                String courseCode=textField1.getText();
                Connection conn= DatabaseConnection.getConnection();
                String sql="SELECT undergraduate_id,course_code,final_total_out_of_100 FROM final_course_marks_view WHERE course_code=?";
                if(courseCode.isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"You must have to fill the field...");
                    return;
                }

                try {
                    PreparedStatement pstmt=conn.prepareStatement(sql);
                    pstmt.setString(1,courseCode);
                    ResultSet rs=pstmt.executeQuery();
                    table6.setModel(DbUtils.resultSetToTableModel(rs));
                }catch (SQLException ex)
                {
                    System.out.println("Error in executing data...");
                }

                info.setVisible(!info.isVisible());
            }
        });
    }

    public static void main(String[] args) {
        new FinalGrade();
    }
}
