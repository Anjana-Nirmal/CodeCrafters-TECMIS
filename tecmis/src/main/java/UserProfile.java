import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class UserProfile {
    public JPanel Main;
    private JButton addUserButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JButton refreshButton;
    private JTable userTable;

    public UserProfile() {
        loadUsersToTable();

        // ➕ Add User
        addUserButton.addActionListener(e -> {
            JTextField userIdField = new JTextField();
            JTextField nameField = new JTextField();
            JTextField emailField = new JTextField();
            JTextField usernameField = new JTextField();
            JTextField contactField = new JTextField();
            JTextField roleField = new JTextField();

            Object[] fields = {
                    "User ID:", userIdField,
                    "Name:", nameField,
                    "Email:", emailField,
                    "Username:", usernameField,
                    "Contact:", contactField,
                    "Role (undergraduate / lecturer / admin / technicalOfficer):", roleField
            };

            int result = JOptionPane.showConfirmDialog(null, fields, "Add User", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                boolean success = addUser(
                        userIdField.getText(),
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
        });

        // ✏️ Edit User
        editButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(Main, "Please select a row to edit.");
                return;
            }

            DefaultTableModel model = (DefaultTableModel) userTable.getModel();
            String userId = model.getValueAt(selectedRow, 0).toString();

            JTextField userIdField = new JTextField(userId);
            userIdField.setEnabled(false); // Not editable

            JTextField nameField = new JTextField(model.getValueAt(selectedRow, 1).toString());
            JTextField emailField = new JTextField(model.getValueAt(selectedRow, 2).toString());
            JTextField usernameField = new JTextField(model.getValueAt(selectedRow, 3).toString());
            JTextField contactField = new JTextField(model.getValueAt(selectedRow, 4).toString());
            JTextField roleField = new JTextField(model.getValueAt(selectedRow, 5).toString());

            Object[] fields = {
                    "User ID (not editable):", userIdField,
                    "Name:", nameField,
                    "Email:", emailField,
                    "Username:", usernameField,
                    "Contact:", contactField,
                    "Role:", roleField
            };

            int result = JOptionPane.showConfirmDialog(null, fields, "Edit User", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                boolean success = updateUser(
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
        });

        // ❌ Delete User
        deleteButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(Main, "Please select a row to delete.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(Main, "Are you sure you want to delete this user?",
                    "Delete Confirmation", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                String userId = userTable.getValueAt(selectedRow, 0).toString();
                boolean success = deleteUser(userId);

                if (success) {
                    JOptionPane.showMessageDialog(Main, "User deleted.");
                    loadUsersToTable();
                } else {
                    JOptionPane.showMessageDialog(Main, "Error deleting user.");
                }
            }
        });

        // 🔎 Search User
        searchButton.addActionListener(e -> {
            String keyword = JOptionPane.showInputDialog(Main, "Enter user ID, name, username, email, or role to search:");
            if (keyword != null && !keyword.trim().isEmpty()) {
                searchUsers(keyword.trim());
            }
        });

        // 🔄 Refresh Button
        refreshButton.addActionListener(e -> loadUsersToTable());

        // 🖱️ Double-click to edit user
        userTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    editButton.doClick(); // simulate Edit button click
                }
            }
        });
    }

    private void loadUsersToTable() {
        ArrayList<Object[]> users = fetchUsers();

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{
                "User ID", "Name", "Email", "Username", "Contact", "Role", "Date Created"
        });

        for (Object[] row : users) {
            model.addRow(row);
        }

        userTable.setModel(model);
    }

    private ArrayList<Object[]> fetchUsers() {
        ArrayList<Object[]> users = new ArrayList<>();
        String query = "SELECT user_id, name, email, username, contact, role, CURRENT_TIMESTAMP AS date_created FROM User";

        try (Connection conn = Connector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Object[] row = {
                        rs.getString("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("contact"),
                        rs.getString("role"),
                        rs.getTimestamp("date_created")
                };
                users.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    private void searchUsers(String keyword) {
        String query = "SELECT user_id, name, email, username, contact, role, CURRENT_TIMESTAMP AS date_created " +
                "FROM User WHERE user_id LIKE ? OR name LIKE ? OR email LIKE ? OR username LIKE ? OR role LIKE ?";

        try (Connection conn = Connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            ps.setString(4, like);
            ps.setString(5, like);

            ResultSet rs = ps.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new Object[]{
                    "User ID", "Name", "Email", "Username", "Contact", "Role", "Date Created"
            });

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("contact"),
                        rs.getString("role"),
                        rs.getTimestamp("date_created")
                });
            }

            userTable.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(Main, "Error searching users: " + e.getMessage());
        }
    }

    private boolean addUser(String userId, String name, String email, String username, String contact, String role) {
        String defaultPassword = "default123"; // You can update logic to generate random passwords if you want

        String query = "INSERT INTO User (user_id, name, email, username, contact, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, userId);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, username);
            ps.setString(5, contact);
            ps.setString(6, defaultPassword);
            ps.setString(7, role);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean updateUser(String userId, String name, String email, String username, String contact, String role) {
        String query = "UPDATE User SET name=?, email=?, username=?, contact=?, role=? WHERE user_id=?";

        try (Connection conn = Connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, username);
            ps.setString(4, contact);
            ps.setString(5, role);
            ps.setString(6, userId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean deleteUser(String userId) {
        String query = "DELETE FROM User WHERE user_id=?";

        try (Connection conn = Connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, userId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("User Profile Management");
        frame.setContentPane(new UserProfile().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}