import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Notices {
    public JPanel Main;
    private JButton addNoticeButton;
    private JButton editNoticeButton;
    private JButton deleteNoticeButton;
    private JButton searchNoticeButton;
    private JTable table1;

    public Notices() {
        // Table headers based on your Notice table
        String[] columns = {"Notice ID", "Title", "Date"};
        Object[][] data = {
                {1, "Holiday", "2024-04-20"},
                {2, "Meeting Scheduled", "2024-04-25"}
        };

        // Table model
        DefaultTableModel model = new DefaultTableModel(data, columns);
        table1.setModel(model);

        // Button logic (placeholder)
        addNoticeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Main, "Add Notice Clicked");
            }
        });

        editNoticeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Main, "Edit Notice Clicked");
            }
        });

        deleteNoticeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Main, "Delete Notice Clicked");
            }
        });

        searchNoticeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Main, "Search Notice Clicked");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Notices Management");
        frame.setContentPane(new Notices().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}