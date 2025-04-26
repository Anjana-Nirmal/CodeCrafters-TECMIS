import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Coursemanage extends JFrame {
    private JButton addMaterialsButton;
    private JPanel main;
    private JButton viewMaterialsButton;
    private JButton editMaterialsButton;
    private JLabel view;
    private JPanel hiddenPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JLabel coursecode;
    private JLabel description;
    private JLabel filepath;
    private JLabel date;
    private JButton updateButton;
    private JLabel label1;
    private JButton clearButton;
    private JPanel hiddenPanel2;
    private JTable table1;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JLabel editid;
    private JLabel editTitle;
    private JLabel editDesc;
    private JLabel editpath;
    private JTextField textField9;
    private JLabel editDate;
    private JButton editupdate;
    private JButton editclear;
    private JPanel hiddenpanel3;
    private JTextField textfield11;
    private JTextField textField10;
    private JLabel editcourse;
    private JLabel Add;
    private JLabel Edit;
    private JPanel CourseTitle;
    private JLabel CourseName;

    public Coursemanage()
    {
        DatabaseConnection databaseConnection=new DatabaseConnection();
        setTitle("Course Manage");
        setSize(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(main);
        view.setBorder(new LineBorder(Color.BLACK, 3));
        Add.setBorder(new LineBorder(Color.BLACK, 3));
        Edit.setBorder(new LineBorder(Color.BLACK, 3));
        CourseTitle.setPreferredSize(new Dimension(600,100));
        setVisible(true);
        hiddenPanel.setVisible(false);
        hiddenPanel2.setVisible(false);
        hiddenpanel3.setVisible(false);
        addMaterialsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               hiddenPanel.setVisible(!hiddenPanel.isVisible());
               hiddenPanel2.setVisible(false);
               hiddenpanel3.setVisible(false);

            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddMaterial();
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText(" ");
                textField2.setText(" ");
                textField3.setText(" ");
                textField4.setText(" ");
                textField5.setText(" ");
            }
        });
        viewMaterialsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hiddenPanel.setVisible(false);
                hiddenpanel3.setVisible(false);
                Connection conn=DatabaseConnection.getConnection();
                String sql="SELECT * FROM addMaterials";
                try {
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    ResultSet rs=pstmt.executeQuery();
                    table1.setModel(DbUtils.resultSetToTableModel(rs));
                }catch (SQLException ex)
                {
                    System.out.println(ex.getMessage());
                }

                hiddenPanel2.setVisible(!hiddenPanel2.isVisible());
            }
        });
        editMaterialsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hiddenPanel.setVisible(false);
                hiddenPanel2.setVisible(false);
                hiddenpanel3.setVisible(!hiddenpanel3.isVisible());
            }
        });
        editclear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField10.setText(" ");
                textfield11.setText(" ");
                textField6.setText(" ");
                textField7.setText(" ");
                textField8.setText(" ");
                textField9.setText(" ");
            }
        });
        editupdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMaterils();
            }
        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                    int row = table1.rowAtPoint(e.getPoint());
                    int col = table1.columnAtPoint(e.getPoint());

                    Object cellValue = table1.getValueAt(row, col);
                    if (cellValue != null) {
                        String url = cellValue.toString();


                        if (url.startsWith("http://") || url.startsWith("https://")) {
                            try {
                                Desktop.getDesktop().browse(new URI(url));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
        });
    }
public void AddMaterial()
{
    String courseCode=textField1.getText();
    String Title= textField2.getText();
    String Description=textField3.getText();
    String Path=textField4.getText();
    String  dat=textField5.getText();

    Connection conn=DatabaseConnection.getConnection();
    String sql="INSERT INTO addMaterials(course_code,title,description,file_path,upload_date)VALUES (?,?,?,?,?)";
    if (courseCode.isEmpty() && Title.isEmpty() && Description.isEmpty() && Path.isEmpty() && dat.isEmpty())
    {
        JOptionPane.showMessageDialog(null,"You Have To Fill All The Fields...");
    }
    else {
        try {

            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, courseCode);
            pstm.setString(2, Title);
            pstm.setString(3, Description);
            pstm.setString(4, Path);
            pstm.setString(5, dat);
            int rows = pstm.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Data Insert Successfully....");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error!,Cann't add data properly..");
        }
    }
    textField1.setText(" ");
    textField2.setText(" ");
    textField3.setText(" ");
    textField4.setText(" ");
    textField5.setText(" ");
}
public void updateMaterils() {
    String id = textField10.getText();
    String code = textfield11.getText();
    String title = textField6.getText();
    String desc = textField7.getText();
    String path = textField8.getText();
    String date = textField9.getText();

    if (id.isEmpty()) {
        JOptionPane.showMessageDialog(null, "You must have to fill the Material id...");
        return;
    }

    Connection conn = DatabaseConnection.getConnection();
    String sql = "UPDATE addMaterials SET course_code=?,title=?,description=?,file_path=?,upload_date=? WHERE Material_id=?";



        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, code);
            pstmt.setString(2, title);
            pstmt.setString(3, desc);
            pstmt.setString(4, path);
            pstmt.setString(5, date);
            pstmt.setString(6,id);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

}
    public static void main(String[] args) {
        new Coursemanage();
    }
}
