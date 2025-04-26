import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CAEligibilty extends JFrame{
    private JPanel main;
    private JButton CAMarksButton;
    private JLabel CAimage;
    private JButton checkCAMarksButton;
    private JPanel main2;
    private JButton finalEligibilityButton;
    private JButton batchButton1;
    private JButton individualButton1;
    private JPanel main3;
    private JButton batchButton;
    private JButton individualButton;
    private JPanel main4;
    private JTextField textField1;
    private JPanel CAbatch;
    private JTable table1;
    private JButton viewButton;
    private JPanel CAindividual;
    private JTextField textField2;
    private JTextField textField3;
    private JButton viewButton1;
    private JTable table2;
    private JPanel CAindi;
    private JLabel finalcourse;
    private JPanel finalbatchmain;
    private JTable table3;
    private JPanel finalbatchtable;
    private JButton viewButton3;
    private JLabel course;
    private JLabel student;
    private JPanel finalindividualmain;
    private JTable table4;
    private JPanel finalindividualtable;
    private JTextField textField4;
    private JButton viewButton2;
    private JTextField textField5;
    private JTextField textField6;
    private JPanel theme;
    private JButton viewButton4;
    private JButton viewButton5;
    private JButton finalExamEligibilityButton;
    private JButton checkButton;
    private JPanel FinalResults;
    private JTable table5;
    private JPanel FinalResultsTable;
    private JTextField textField7;
    public static String courseCode;
    public CAEligibilty()
    {   DatabaseConnection databaseConnection=new DatabaseConnection();
        setTitle("Check CA");
        setSize(500,480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        CAimage.setBorder(new LineBorder(Color.BLACK, 3));
        setContentPane(main);
        theme.setPreferredSize(new Dimension(500,100));
        ImageIcon icon=new ImageIcon("C:\\Users\\pc\\OneDrive\\Desktop\\untitled\\src\\assessment.png");
        Image img=icon.getImage();
        Image scaledImg=img.getScaledInstance(90,90,Image.SCALE_SMOOTH);
        CAimage.setIcon(new ImageIcon(scaledImg));
        finalbatchtable.setPreferredSize(new Dimension(500,215));
        finalindividualtable.setPreferredSize(new Dimension(500,50));
        FinalResultsTable.setPreferredSize(new Dimension(500,215));
        setVisible(true);

        main2.setVisible(false);
        main3.setVisible(false);
        main4.setVisible(false);
        CAbatch.setVisible(false);
        CAindividual.setVisible(false);
        CAindi.setVisible(false);
        finalbatchmain.setVisible(false);
        finalbatchtable.setVisible(false);
        finalindividualmain.setVisible(false);
        finalindividualtable.setVisible(false);
        FinalResults.setVisible(false);
        FinalResultsTable.setVisible(false);

        CAMarksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main2.setVisible(!main2.isVisible());
            }
        });
        finalEligibilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main3.setVisible(!main3.isVisible());
            }
        });
        batchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CAindividual.setVisible(false);
                finalbatchmain.setVisible(false);
                finalindividualmain.setVisible(false);
                CAindi.setVisible(false);
                FinalResults.setVisible(false);
                FinalResultsTable.setVisible(false);
                main4.setVisible(!main4.isVisible());
            }
        });
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main3.setVisible(!main3.isVisible());
                courseCode = textField1.getText();
                Connection conn = DatabaseConnection.getConnection();

                if (courseCode.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "You must have to fill the field...");
                    return;
                }
                CAeligibilityClass cAeligibilityClass = new CAeligibilityClass(courseCode);


            }
        });
                individualButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        main4.setVisible(false);
                        CAbatch.setVisible(false);
                        CAindi.setVisible(false);
                        finalbatchmain.setVisible(false);
                        finalindividualmain.setVisible(false);
                        finalbatchtable.setVisible(false);
                        finalindividualtable.setVisible(false);
                        FinalResults.setVisible(false);
                        FinalResultsTable.setVisible(false);
                        CAindividual.setVisible(!CAindividual.isVisible());

                    }
                });
                viewButton1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        main2.setVisible(!main2.isVisible());
                        String courseCode = textField2.getText();
                        String undergraduate_id = textField3.getText();
                        Connection conn = DatabaseConnection.getConnection();
                        if (courseCode.isEmpty() || undergraduate_id.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Fill all the fields...");
                            return;
                        }
                        CAEligibilityIndi caEligibilityIndi=new CAEligibilityIndi(courseCode,undergraduate_id);
                    }
                });
                batchButton1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        main4.setVisible(false);
                        CAbatch.setVisible(false);
                        CAindi.setVisible(false);
                        CAindividual.setVisible(false);
                        finalindividualmain.setVisible(false);
                        finalindividualtable.setVisible(false);
                        FinalResults.setVisible(false);
                        FinalResultsTable.setVisible(false);
                        finalbatchmain.setVisible(!finalbatchmain.isVisible());
                    }
                });
                individualButton1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        main4.setVisible(false);
                        CAbatch.setVisible(false);
                        CAindi.setVisible(false);
                        CAindividual.setVisible(false);
                        finalbatchmain.setVisible(false);
                        finalbatchtable.setVisible(false);
                        FinalResults.setVisible(false);
                        FinalResultsTable.setVisible(false);
                        finalindividualmain.setVisible(!finalindividualmain.isVisible());
                    }
                });
                viewButton2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String Ccode = textField4.getText();
                        Connection conn = DatabaseConnection.getConnection();
                        String sql = "WITH AttendanceData AS (\n" +
                                "    SELECT \n" +
                                "        undergraduate_id,\n" +
                                "        course_code,\n" +
                                "        COUNT(*) AS total_sessions,\n" +
                                "        SUM(CASE \n" +
                                "                WHEN attendance_status = 'Present' THEN 1\n" +
                                "                WHEN attendance_status IN ('Absent', 'Medical') AND medical_id IS NOT NULL THEN 1\n" +
                                "                ELSE 0 \n" +
                                "            END) AS attended_sessions\n" +
                                "    FROM attendance\n" +
                                "    WHERE course_code = ?\n" +
                                "    GROUP BY undergraduate_id, course_code\n" +
                                ")\n" +
                                "SELECT \n" +
                                "    undergraduate_id,\n" +
                                "    course_code,\n" +
                                "    ROUND((attended_sessions / total_sessions) * 100, 2) AS attendance_percentage,\n" +
                                "    CASE \n" +
                                "        WHEN (attended_sessions / total_sessions) * 100 >= 80 THEN 'Eligible'\n" +
                                "        ELSE 'Not Eligible'\n" +
                                "    END AS eligibility_status\n" +
                                "FROM AttendanceData;";
                        if (Ccode.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "You must have to fill the field...");
                            return;
                        }
                        try {
                            PreparedStatement pstmt = conn.prepareStatement(sql);
                            pstmt.setString(1, Ccode);
                            ResultSet rs = pstmt.executeQuery();
                            table3.setModel(DbUtils.resultSetToTableModel(rs));
                            finalbatchtable.setVisible(!finalbatchtable.isVisible());
                        } catch (SQLException ex) {
                            System.out.println("Error in retrive data..");
                        }
                    }
                });
                viewButton3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        String Ccode1 = textField5.getText();
                        String Sid = textField6.getText();
                        Connection conn = DatabaseConnection.getConnection();
                        String sql = "WITH AttendanceData AS (\n" +
                                "    SELECT \n" +
                                "        undergraduate_id,\n" +
                                "        course_code,\n" +
                                "        COUNT(*) AS total_sessions,\n" +
                                "        SUM(CASE \n" +
                                "                WHEN attendance_status = 'Present' THEN 1\n" +
                                "                WHEN attendance_status IN ('Absent', 'Medical') AND medical_id IS NOT NULL THEN 1\n" +
                                "                ELSE 0 \n" +
                                "            END) AS attended_sessions\n" +
                                "    FROM attendance\n" +
                                "    WHERE course_code = ? AND undergraduate_id=?\n" +
                                "    GROUP BY undergraduate_id, course_code\n" +
                                ")\n" +
                                "SELECT \n" +
                                "    undergraduate_id,\n" +
                                "    course_code,\n" +
                                "    ROUND((attended_sessions / total_sessions) * 100, 2) AS attendance_percentage,\n" +
                                "    CASE \n" +
                                "        WHEN (attended_sessions / total_sessions) * 100 >= 80 THEN 'Eligible'\n" +
                                "        ELSE 'Not Eligible'\n" +
                                "    END AS eligibility_status\n" +
                                "FROM AttendanceData;";
                        if (Ccode1.isEmpty() || Sid.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Fill all the fields...");
                            return;
                        }
                        try {
                            PreparedStatement pstmt = conn.prepareStatement(sql);
                            pstmt.setString(1, Ccode1);
                            pstmt.setString(2, Sid);
                            ResultSet rs = pstmt.executeQuery();
                            table4.setModel(DbUtils.resultSetToTableModel(rs));
                            finalindividualtable.setVisible(!finalindividualtable.isVisible());
                        } catch (SQLException ex) {
                            System.out.println("Error in retrive data..");
                        }
                    }
                });
                CAimage.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        finalbatchmain.setVisible(false);
                        finalbatchtable.setVisible(false);
                        finalindividualmain.setVisible(false);
                        finalindividualtable.setVisible(false);
                        CAbatch.setVisible(false);
                        main4.setVisible(false);
                        CAindividual.setVisible(false);
                        CAindi.setVisible(false);
                        FinalResults.setVisible(false);
                        FinalResultsTable.setVisible(false);
                    }
                });
        viewButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code=textField1.getText();
                Connection conn=DatabaseConnection.getConnection();
                String sql="SELECT undergraduate_id,course_code,ca_marks,eligibility FROM ca_eligibility_temp WHERE course_code=? ";
                try {
                    PreparedStatement pstmt=conn.prepareStatement(sql);
                    pstmt.setString(1,code);
                    ResultSet rs=pstmt.executeQuery();
                    table1.setModel(DbUtils.resultSetToTableModel(rs));
                }catch (SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Error in retriveing data");
                }
                CAbatch.setVisible(!CAbatch.isVisible());
            }
        });
        viewButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code=textField2.getText();
                String id=textField3.getText();
                Connection conn=DatabaseConnection.getConnection();
                String sql="SELECT undergraduate_id,course_code,ca_marks,eligibility FROM ca_eligibility_temp WHERE course_code=? AND undergraduate_id=? ";
                try {
                    PreparedStatement pstmt=conn.prepareStatement(sql);
                    pstmt.setString(1,code);
                    pstmt.setString(2,id);
                    ResultSet rs=pstmt.executeQuery();
                    table2.setModel(DbUtils.resultSetToTableModel(rs));
                }catch (SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Error in retriveing data");
                }
                CAindi.setVisible(!CAindi.isVisible());
            }
        });
        finalExamEligibilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalbatchmain.setVisible(false);
                finalbatchtable.setVisible(false);
                finalindividualmain.setVisible(false);
                finalindividualtable.setVisible(false);
                CAbatch.setVisible(false);
                main4.setVisible(false);
                CAindividual.setVisible(false);
                CAindi.setVisible(false);
                FinalResultsTable.setVisible(false);
                FinalResults.setVisible(!FinalResults.isVisible());
            }
        });
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code=textField7.getText();
                Connection conn=DatabaseConnection.getConnection();
                String sql="WITH AttendanceEligibility AS (\n" +
                        "    SELECT \n" +
                        "        undergraduate_id,\n" +
                        "        course_code,\n" +
                        "        COUNT(*) AS total_sessions,\n" +
                        "        SUM(CASE \n" +
                        "                WHEN attendance_status = 'Present' THEN 1\n" +
                        "                WHEN attendance_status IN ('Absent', 'Medical') AND medical_id IS NOT NULL THEN 1\n" +
                        "                ELSE 0 \n" +
                        "            END) AS attended_sessions,\n" +
                        "        ROUND((SUM(CASE \n" +
                        "                    WHEN attendance_status = 'Present' THEN 1\n" +
                        "                    WHEN attendance_status IN ('Absent', 'Medical') AND medical_id IS NOT NULL THEN 1\n" +
                        "                    ELSE 0 \n" +
                        "                END) / COUNT(*)) * 100, 2) AS attendance_percentage,\n" +
                        "        CASE \n" +
                        "            WHEN (SUM(CASE \n" +
                        "                        WHEN attendance_status = 'Present' THEN 1\n" +
                        "                        WHEN attendance_status IN ('Absent', 'Medical') AND medical_id IS NOT NULL THEN 1\n" +
                        "                        ELSE 0 \n" +
                        "                    END) / COUNT(*)) * 100 >= 80 THEN 'Eligible'\n" +
                        "            ELSE 'Not Eligible'\n" +
                        "        END AS attendance_eligibility\n" +
                        "    FROM attendance\n" +
                        "    WHERE course_code = ?\n" +
                        "    GROUP BY undergraduate_id, course_code\n" +
                        ")\n" +
                        "SELECT \n" +
                        "    ca.undergraduate_id,\n" +
                        "    ca.course_code,\n" +
                        "    ca.ca_marks,\n" +
                        "    ca.eligibility AS ca_eligibility,\n" +
                        "    ae.attendance_percentage,\n" +
                        "    ae.attendance_eligibility,\n" +
                        "    CASE \n" +
                        "        WHEN ca.eligibility = 'Eligible' AND ae.attendance_eligibility = 'Eligible' THEN 'Eligible'\n" +
                        "        ELSE 'Not Eligible'\n" +
                        "    END AS final_exam_eligibility\n" +
                        "FROM ca_eligibility_temp ca\n" +
                        "JOIN AttendanceEligibility ae\n" +
                        "    ON ca.undergraduate_id = ae.undergraduate_id\n" +
                        "    AND ca.course_code = ae.course_code;";
                try{

                    PreparedStatement pstmt=conn.prepareStatement(sql);
                    pstmt.setString(1,code);
                    ResultSet rs=pstmt.executeQuery();
                    table5.setModel(DbUtils.resultSetToTableModel(rs));
                }catch (SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Error in Retreving Data");
                }
                FinalResultsTable.setVisible(!FinalResultsTable.isVisible());
            }
        });
    }

    public static void main(String[] args) {
        new CAEligibilty();
    }
}