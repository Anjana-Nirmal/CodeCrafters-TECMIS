import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class UserProfile extends JFrame {
    private JPanel Main;
    private JTable table1;
    private JButton addUserButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton searchButton;
    private DefaultTableModel tableModel;

    public UserProfile() {
        setTitle("User Profile Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setContentPane(Main);  // Bind the UI built in .form file

        // Setup table model
        String[] columns = {"User ID", "Name", "Email", "Username", "Contact", "Role", "Date Created"};
        tableModel = new DefaultTableModel(columns, 0);
        table1.setModel(tableModel);

        // Load users into table
        refreshUserTable();

        // Button listeners
        addUserButton.addActionListener((ActionEvent e) ->
                JOptionPane.showMessageDialog(this, "Add User Clicked"));

        editButton.addActionListener((ActionEvent e) ->
                JOptionPane.showMessageDialog(this, "Edit User Clicked"));

        deleteButton.addActionListener((ActionEvent e) ->
                JOptionPane.showMessageDialog(this, "Delete User Clicked"));

        searchButton.addActionListener((ActionEvent e) ->
                JOptionPane.showMessageDialog(this, "Search User Clicked"));
    }

    public void refreshUserTable() {
        tableModel.setRowCount(0);
        ArrayList<Object[]> users = DatabaseHelper.fetchUsers(); // Make sure this returns actual user data
        for (Object[] row : users) {
            tableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserProfile().setVisible(true));
    }

    private void createUIComponents() {
        // This method is required by IntelliJ GUI Designer
    }
}
