import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Notice extends JFrame{
    private JPanel panel1;
    private JLabel NoticeImage;
    private JTable table1;
    private JPanel NoticeTable;
    private JPanel NoticeTheme;
    private JPanel image;

    public Notice()
    {
        DatabaseConnection databaseConnection=new DatabaseConnection();
        setTitle("Notice");
        setSize(400,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        NoticeTheme.setPreferredSize(new Dimension(400,100));
        ImageIcon icon=new ImageIcon("C:\\Users\\pc\\OneDrive\\Desktop\\Test\\src\\notice.png");
        Image img=icon.getImage();
        Image scaledImg=img.getScaledInstance(90,90, Image.SCALE_SMOOTH);
        NoticeImage.setIcon(new ImageIcon(scaledImg));
        NoticeTable.setPreferredSize(new Dimension(400,100));
        setContentPane(panel1);
        setVisible(true);


        Connection conn= DatabaseConnection.getConnection();
        String sql="SELECT notice_id AS N_Id,title AS Title,content AS Content FROM notice";

        try {
            PreparedStatement pstmt=conn.prepareStatement(sql);

            ResultSet rs=pstmt.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null,"Error in executing data...");
        }

    }

    public static void main(String[] args) {
        new Notice();
    }
}
