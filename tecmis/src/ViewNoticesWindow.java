package tecmis.src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewNoticesWindow extends JFrame {
    private JTable table;
    private String username;

    public ViewNoticesWindow(String username) {
        this.username = username;

        setTitle("View Notices");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        loadNoticesData();

        setVisible(true);
    }

    private void loadNoticesData() {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return;

        try {
            String sql = "SELECT title, content, date FROM Notice ORDER BY date DESC";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Title");
            model.addColumn("Content");
            model.addColumn("Date");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getDate("date")
                });
            }

            table.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
