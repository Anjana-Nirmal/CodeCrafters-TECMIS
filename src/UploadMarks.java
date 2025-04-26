import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UploadMarks extends JFrame {
    private JPanel main;
    private JComboBox<String> comboBox1;
    private JTextField textField1;
    private JRadioButton quiz1RadioButton;
    private JRadioButton quiz2RadioButton;
    private JRadioButton quiz3RadioButton;
    private JRadioButton assestmentsRadioButton;
    private JRadioButton midTermRadioButton;
    private JButton submitButton;
    private JTextField textField2;
    private JButton resetButton;
    private JLabel marks;
    private JLabel type;
    private JLabel regno;
    private JLabel cousrecode;
    private JRadioButton finalTheoryRadioButton;
    private JRadioButton finalPracticalRadioButton;

    public UploadMarks()
    {
        DatabaseConnection databaseConnection=new DatabaseConnection();
        setTitle("Upload Marks");
        setSize(400,470);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(main);



        comboBox1.addItem("ICT2113");
        comboBox1.addItem("ICT2122");
        comboBox1.addItem("ICT2133");
        comboBox1.addItem("ICT2142");
        comboBox1.addItem("ICT2152");
        comboBox1.addItem("ENG2122");

        ButtonGroup buttonGroup=new ButtonGroup();
        buttonGroup.add(quiz1RadioButton);
        buttonGroup.add(quiz2RadioButton);
        buttonGroup.add(quiz3RadioButton);
        buttonGroup.add(assestmentsRadioButton);
        buttonGroup.add(midTermRadioButton);
        buttonGroup.add(finalPracticalRadioButton);
        buttonGroup.add(finalTheoryRadioButton);
        setVisible(true);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String combox=(String) comboBox1.getSelectedItem();
                String Regno=textField1.getText();
                String Type1 = "";
                if(quiz1RadioButton.isSelected())
                {
                     Type1="Quiz 1";
                }
                if(quiz2RadioButton.isSelected())
                {
                     Type1="Quiz 2";
                }
                if(quiz3RadioButton.isSelected())
                {
                     Type1="Quiz 3";
                }
                if(assestmentsRadioButton.isSelected())
                {
                    Type1="Assessment";
                }
                if(midTermRadioButton.isSelected())
                {
                    Type1="Mid Term";
                }
                if(finalTheoryRadioButton.isSelected())
                {
                     Type1="Final Exam Practical";
                }
                if(finalPracticalRadioButton.isSelected())
                {
                     Type1="Final Exam Theory";
                }

                int mark=Integer.parseInt(textField2.getText());
                if(mark>100 || mark < 0)
                {
                    JOptionPane.showMessageDialog(null,"Enter marks between 0 to 100");
                    return;
                }

                Connection conn=DatabaseConnection.getConnection();
                String sql="INSERT INTO marks(undergraduate_id,course_code,type,marks) VALUES (?,?,?,?)";
                try {
                    PreparedStatement pstmt=conn.prepareStatement(sql);
                    pstmt.setString(1,Regno);
                    pstmt.setString(2,combox);
                    pstmt.setString(3,Type1);
                    pstmt.setInt(4,mark);

                    int rows=pstmt.executeUpdate();
                    if(rows>0)
                    {
                        JOptionPane.showMessageDialog(null,"Marks Added Successfully...");
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"Error!!");
                    }

                }catch (SQLException ex)
                {
                    System.out.println(ex.getMessage());
                }

                 textField1.setText(" ");
                 textField2.setText(" ");
                 buttonGroup.clearSelection();
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText(" ");
                textField2.setText(" ");
                buttonGroup.clearSelection();
                comboBox1.setSelectedIndex(0);
            }
        });
    }

    public static void main(String[] args) {
        new UploadMarks();
    }
}
