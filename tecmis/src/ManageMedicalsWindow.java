package tecmis.src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ManageMedicalsWindow extends JFrame {

    private JTable medicalTable;
    private JButton addButton, updateButton, deleteButton;
    private DefaultTableModel model;
    private String username;

    public ManageMedicalsWindow(String username) {
        this.username = username;

        setTitle("Manage Medicals");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Medical Records Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] columns = {"Medical ID", "Student ID", "Date", "Description"};
        model = new DefaultTableModel(columns, 0);
        medicalTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(medicalTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Add Medical Record");
        updateButton = new JButton("Update Medical Record");
        deleteButton = new JButton("Delete Medical Record");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);

        loadMedicalRecords(); // load medical records into table

        // Button actions
        addButton.addActionListener(e -> addMedicalRecord());
        updateButton.addActionListener(e -> updateMedicalRecord());
        deleteButton.addActionListener(e -> deleteMedicalRecord());
    }

    private void loadMedicalRecords() {
        try {
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM Medical WHERE med_undergraduate_id='" + username + "'";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String id = rs.getString("medical_id");
                String studentId = rs.getString("med_undergraduate_id");
                String date = rs.getString("date");
                String description = rs.getString("description");

                model.addRow(new Object[]{id, studentId, date, description});
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading medicals: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void addMedicalRecord() {
        JTextField dateField = new JTextField();
        JTextField descriptionField = new JTextField();

        Object[] fields = {
                "Date (YYYY-MM-DD):", dateField,
                "Description:", descriptionField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Add Medical Record", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                Connection con = DBConnection.getConnection();

                // Step 1: Get max existing medical_id
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT MAX(medical_id) FROM Medical");

                String newMedicalId = "M001"; // default starting ID
                if (rs.next() && rs.getString(1) != null) {
                    String maxId = rs.getString(1); // example: M005
                    int idNum = Integer.parseInt(maxId.substring(1)); // get number part
                    idNum++; // increment
                    newMedicalId = String.format("M%03d", idNum); // make like M006
                }
                rs.close();
                stmt.close();

                // Step 2: Insert new medical record with generated ID
                String query = "INSERT INTO Medical (medical_id, med_undergraduate_id, date, description) VALUES (?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, newMedicalId);
                pst.setString(2, username);
                pst.setString(3, dateField.getText());
                pst.setString(4, descriptionField.getText());

                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Medical record added!");

                model.setRowCount(0); // clear table
                loadMedicalRecords(); // reload table

                pst.close();
                con.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding medical: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }


    private void updateMedicalRecord() {
        int selectedRow = medicalTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record to update.");
            return;
        }

        String medicalId = model.getValueAt(selectedRow, 0).toString();

        JTextField dateField = new JTextField(model.getValueAt(selectedRow, 2).toString());
        JTextField descriptionField = new JTextField(model.getValueAt(selectedRow, 3).toString());

        Object[] fields = {
                "Date (YYYY-MM-DD):", dateField,
                "Description:", descriptionField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Update Medical Record", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                Connection con = DBConnection.getConnection();
                String query = "UPDATE Medical SET date=?, description=? WHERE medical_id=?";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, dateField.getText());
                pst.setString(2, descriptionField.getText());
                pst.setString(3, medicalId);

                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Medical record updated!");

                model.setRowCount(0); // clear
                loadMedicalRecords(); // reload

                pst.close();
                con.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating medical: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void deleteMedicalRecord() {
        int selectedRow = medicalTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record to delete.");
            return;
        }

        String medicalId = model.getValueAt(selectedRow, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection con = DBConnection.getConnection();
                String query = "DELETE FROM Medical WHERE medical_id=?";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, medicalId);

                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Medical record deleted!");

                model.setRowCount(0); // clear
                loadMedicalRecords(); // reload

                pst.close();
                con.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting medical: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
