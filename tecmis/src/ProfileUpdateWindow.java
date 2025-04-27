package tecmis.src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProfileUpdateWindow extends JFrame implements ActionListener {
    private JTextField contactField;
    private JLabel profilePicLabel;
    private JButton updateButton, uploadButton;
    private String username;
    private String selectedImagePath = null;

    public ProfileUpdateWindow(String username) {
        this.username = username;

        setTitle("Update Profile");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        contactField = new JTextField();
        profilePicLabel = new JLabel("No Image", JLabel.CENTER);
        uploadButton = new JButton("Upload New Picture");
        updateButton = new JButton("Update Contact");

        uploadButton.addActionListener(this);
        updateButton.addActionListener(this);

        add(new JLabel("New Contact Number:", JLabel.CENTER));
        add(contactField);
        add(profilePicLabel);
        add(uploadButton);
        add(updateButton);

        loadCurrentProfile();

        setVisible(true);
    }

    private void loadCurrentProfile() {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return;

        try {
            String sql = "SELECT contact FROM User WHERE username = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                contactField.setText(rs.getString("contact"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadProfilePicture() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            selectedImagePath = selectedFile.getAbsolutePath();
            profilePicLabel.setText(selectedFile.getName());
        }
    }

    private void updateProfile() {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return;

        try {
            String newContact = contactField.getText();
            String sql = "UPDATE User SET contact = ? WHERE username = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, newContact);
            pst.setString(2, username);
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Profile Updated Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Update Failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // If an image is selected, copy/save it (optional)
            if (selectedImagePath != null) {
                // Save image path or actual image to database if needed
                // For now, we just display the filename
                JOptionPane.showMessageDialog(this, "Profile picture uploaded: " + selectedImagePath);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == uploadButton) {
            uploadProfilePicture();
        } else if (e.getSource() == updateButton) {
            updateProfile();
        }
    }
}

