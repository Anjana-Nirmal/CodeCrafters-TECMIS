import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserProfile {
    public JPanel Main;
    private JButton addUserButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTable userTable;

    public UserProfile() {
        loadUsersToTable();

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField nameField = new JTextField();
                JTextField emailField = new JTextField();
                JTextField usernameField = new JTextField();
                JTextField contactField = new JTextField();
                JTextField roleField = new JTextField();

                Object[] fields = {
                        "Name:", nameField,
                        "Email:", emailField,
                        "Username:", usernameField,
                        "Contact:", contactField,
                        "Role:", roleField
                };

                int result = JOptionPane.showConfirmDialog(null, fields, "Add User", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    boolean success = DatabaseHelper.addUser(
                            nameField.getText(),
                            emailField.getText(),
                            usernameField.getText(),
                            contactField.getText(),
                            roleField.getText()
                    );

                    if (success) {
                        JOptionPane.showMessageDialog(Main, "User added successfully.");
                        loadUsersToTable();
                    } else {
                        JOptionPane.showMessageDialog(Main, "Error adding user.");
                    }
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(Main, "Please select a row to edit.");
                    return;
                }

                DefaultTableModel model = (DefaultTableModel) userTable.getModel();
                int userId = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

                JTextField nameField = new JTextField(model.getValueAt(selectedRow, 1).toString());
                JTextField emailField = new JTextField(model.getValueAt(selectedRow, 2).toString());
                JTextField usernameField = new JTextField(model.getValueAt(selectedRow, 3).toString());
                JTextField contactField = new JTextField(model.getValueAt(selectedRow, 4).toString());
                JTextField roleField = new JTextField(model.getValueAt(selectedRow, 5).toString());

                Object[] fields = {
                        "Name:", nameField,
                        "Email:", emailField,
                        "Username:", usernameField,
                        "Contact:", contactField,
                        "Role:", roleField
                };

                int result = JOptionPane.showConfirmDialog(null, fields, "Edit User", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    boolean success = DatabaseHelper.updateUser(
                            userId,
                            nameField.getText(),
                            emailField.getText(),
                            usernameField.getText(),
                            contactField.getText(),
                            roleField.getText()
                    );

                    if (success) {
                        JOptionPane.showMessageDialog(Main, "User updated successfully.");
                        loadUsersToTable();
                    } else {
                        JOptionPane.showMessageDialog(Main, "Error updating user.");
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(Main, "Please select a row to delete.");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(Main, "Are you sure you want to delete this user?",
                        "Delete Confirmation", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    int userId = Integer.parseInt(userTable.getValueAt(selectedRow, 0).toString());
                    boolean success = DatabaseHelper.deleteUser(userId);

                    if (success) {
                        JOptionPane.showMessageDialog(Main, "User deleted.");
                        loadUsersToTable();
                    } else {
                        JOptionPane.showMessageDialog(Main, "Error deleting user.");
                    }
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Main, "Search functionality not implemented yet.");
            }
        });
    }

    private void loadUsersToTable() {
        ArrayList<Object[]> users = DatabaseHelper.fetchUsers();

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{
                "User ID", "Name", "Email", "Username", "Contact", "Role", "Date Created"
        });

        for (Object[] row : users) {
            model.addRow(row);
        }

        userTable.setModel(model);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("User Profile Management");
        frame.setContentPane(new UserProfile().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}