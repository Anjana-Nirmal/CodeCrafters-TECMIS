import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Attendance extends JFrame{
    private JPanel main;
    private JPanel AttendanceTheme;
    private JButton viewAttendanceButton;
    private JTable table1;
    private JPanel TablePanel;
    private JTextField textField1;
    private JButton viewButton;
    private JPanel TableView;
    private JLabel attendanceImage;
    private JPanel mainpanel;


    public Attendance()
    {
        DatabaseConnection databaseConnection=new DatabaseConnection();
        setTitle("Check Attandance");
        setSize(400,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        AttendanceTheme.setPreferredSize(new Dimension(400,50));
        ImageIcon icon=new ImageIcon("C:\\Users\\pc\\OneDrive\\Desktop\\untitled\\src\\attendancenew.png");
        Image img=icon.getImage();
        Image scaledImg=img.getScaledInstance(90,90,Image.SCALE_SMOOTH);
        attendanceImage.setIcon(new ImageIcon(scaledImg));
        TablePanel.setVisible(false);
        TableView.setVisible(false);
        setContentPane(mainpanel);
        setVisible(true);


        viewAttendanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TablePanel.setVisible(false);
                TableView.setVisible(!TableView.isVisible());
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code=textField1.getText();
                Connection conn=DatabaseConnection.getConnection();
                String sql="SELECT * FROM attendance WHERE course_code=?";
                try {
                    PreparedStatement pstmt=conn.prepareStatement(sql);
                    pstmt.setString(1,code);
                    ResultSet rs= pstmt.executeQuery();
                    table1.setModel(DbUtils.resultSetToTableModel(rs));
                }catch (SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Error in retrive Data");
                }
                TablePanel.setVisible(!TablePanel.isVisible());
            }
        });
    }

    public static void main(String[] args) {
        new Attendance();
    }
}
