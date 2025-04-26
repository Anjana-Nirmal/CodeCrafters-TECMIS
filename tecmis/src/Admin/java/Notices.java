import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Notices {
    public JPanel Main;
    private JButton addNoticeButton;
    private JButton editNoticeButton;
    private JButton deleteNoticeButton;
    private JButton searchNoticeButton;
    private JButton refreshButton;
    private JButton backButton;
    private JTable table1;

    public Notices() {
        loadNotices();

        addNoticeButton.addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog("Enter Notice ID:");
                if (idStr == null || idStr.isEmpty()) return;
                int id = Integer.parseInt(idStr);

                String title = JOptionPane.showInputDialog("Enter Title:");
                if (title == null || title.isEmpty()) return;

                String content = JOptionPane.showInputDialog("Enter Content:");
                if (content == null || content.isEmpty()) return;

                Connection conn = Connector.getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO notice VALUES (?, ?, ?)");
                ps.setInt(1, id);
                ps.setString(2, title);
                ps.setString(3, content);

                ps.executeUpdate();
                ps.close();
                conn.close();

                JOptionPane.showMessageDialog(Main, "Notice added.");
                loadNotices();

            } catch (SQLIntegrityConstraintViolationException dup) {
                JOptionPane.showMessageDialog(Main, "Notice ID already exists.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(Main, "Add Error: " + ex.getMessage());
            }
        });

        editNoticeButton.addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog("Enter Notice ID to Edit:");
                if (idStr == null || idStr.isEmpty()) return;
                int id = Integer.parseInt(idStr);

                String newTitle = JOptionPane.showInputDialog("Enter New Title:");
                if (newTitle == null || newTitle.isEmpty()) return;

                String newContent = JOptionPane.showInputDialog("Enter New Content:");
                if (newContent == null || newContent.isEmpty()) return;

                Connection conn = Connector.getConnection();
                PreparedStatement ps = conn.prepareStatement("UPDATE notice SET title=?, content=? WHERE notice_id=?");
                ps.setString(1, newTitle);
                ps.setString(2, newContent);
                ps.setInt(3, id);

                int updated = ps.executeUpdate();
                ps.close();
                conn.close();

                if (updated > 0) {
                    JOptionPane.showMessageDialog(Main, "Notice updated.");
                } else {
                    JOptionPane.showMessageDialog(Main, "Notice ID not found.");
                }

                loadNotices();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(Main, "Edit Error: " + ex.getMessage());
            }
        });

        deleteNoticeButton.addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog("Enter Notice ID to Delete:");
                if (idStr == null || idStr.isEmpty()) return;
                int id = Integer.parseInt(idStr);

                int confirm = JOptionPane.showConfirmDialog(Main, "Are you sure you want to delete notice " + id + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) return;

                Connection conn = Connector.getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM notice WHERE notice_id=?");
                ps.setInt(1, id);

                int deleted = ps.executeUpdate();
                ps.close();
                conn.close();

                if (deleted > 0) {
                    JOptionPane.showMessageDialog(Main, "Notice deleted.");
                } else {
                    JOptionPane.showMessageDialog(Main, "Notice ID not found.");
                }

                loadNotices();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(Main, "Delete Error: " + ex.getMessage());
            }
        });

        searchNoticeButton.addActionListener(e -> {
            try {
                String keyword = JOptionPane.showInputDialog("Enter keyword to search (title or content):");
                if (keyword == null || keyword.trim().isEmpty()) {
                    loadNotices();
                    return;
                }

                Connection conn = Connector.getConnection();
                PreparedStatement ps = conn.prepareStatement(
                        "SELECT notice_id, title, content FROM notice WHERE title LIKE ? OR content LIKE ?"
                );
                ps.setString(1, "%" + keyword + "%");
                ps.setString(2, "%" + keyword + "%");

                ResultSet rs = ps.executeQuery();

                DefaultTableModel model = (DefaultTableModel) table1.getModel();
                model.setRowCount(0);

                while (rs.next()) {
                    int id = rs.getInt("notice_id");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    model.addRow(new Object[]{id, title, content});
                }

                rs.close();
                ps.close();
                conn.close();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(Main, "Search Error: " + ex.getMessage());
            }
        });

        refreshButton.addActionListener(e -> loadNotices());

        backButton.addActionListener(e -> {
            new Admin_Dash().setVisible(true);
            SwingUtilities.getWindowAncestor(Main).dispose();
        });
    }

    private void loadNotices() {
        try {
            Connection conn = Connector.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT notice_id, title, content FROM notice");

            DefaultTableModel model = new DefaultTableModel(new String[]{"Notice ID", "Title", "Content"}, 0);

            while (rs.next()) {
                int id = rs.getInt("notice_id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                model.addRow(new Object[]{id, title, content});
            }

            table1.setModel(model);

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(Main, "Load Error: " + ex.getMessage());
        }
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