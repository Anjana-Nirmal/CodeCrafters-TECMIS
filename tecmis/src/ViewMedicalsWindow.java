package tecmis.src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewMedicalsWindow extends JFrame {
    private JTable table;
    private String username;

    public ViewMedicalsWindow(String username) {
        this.username = username;

        setTitle("View Medicals");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadMedicalData();

        setVisible(true);
    }

    private void loadMedicalData() {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return;

        try {
            // Get user_id of the undergraduate
            String getUserIdSql = "SELECT user_id FROM User WHERE username = ?";
            PreparedStatement pstUser = conn.prepareStatement(getUserIdSql);
            pstUser.setString(1, username);
            ResultSet rsUser = pstUser.executeQuery();
            String userId = null;
            if (rsUser.next()) {
                userId = rsUser.getString("user_id");
            }

            if (userId == null) {
                JOptionPane.showMessageDialog(this, "User ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Now fetch medicals
            String sql = "SELECT medical_id, date, description FROM Medical WHERE med_undergraduate_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, userId);
            ResultSet rs = pst.executeQuery();

            // Table model
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Medical ID");
            model.addColumn("Date");
            model.addColumn("Description");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("medical_id"),
                        rs.getDate("date"),
                        rs.getString("description")
                });
            }

            table.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

