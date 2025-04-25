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
    private JButton refreshButton; // ✅ updated field name
    private JTable table1;

    public Notices() {
        // Set up table columns
        String[] columns = {"Notice ID", "Title", "Content"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table1.setModel(model);

        // 🔄 Load all notices when app starts
        loadNotices();

        // ➕ Add Notice
        addNoticeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                    loadNotices(); // Reload full table

                } catch (SQLIntegrityConstraintViolationException dup) {
                    JOptionPane.showMessageDialog(Main, "Notice ID already exists.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Main, "Add Error: " + ex.getMessage());
                }
            }
        });

        // ✏️ Edit Notice
        editNoticeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                    loadNotices(); // Reload full table

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Main, "Edit Error: " + ex.getMessage());
                }
            }
        });

        // ❌ Delete Notice
        deleteNoticeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                    loadNotices(); // Reload full table

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Main, "Delete Error: " + ex.getMessage());
                }
            }
        });

        // 🔎 Search Notice
        searchNoticeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String keyword = JOptionPane.showInputDialog("Enter keyword to search (title or content):");
                    if (keyword == null || keyword.trim().isEmpty()) {
                        loadNotices(); // Reload all if empty search
                        return;
                    }

                    Connection conn = Connector.getConnection();
                    PreparedStatement ps = conn.prepareStatement(
                            "SELECT notice_id, title, content FROM notice WHERE title LIKE ? OR content LIKE ?"
                    );
                    ps.setString(1, "%" + keyword + "%");
                    ps.setString(2, "%" + keyword + "%");

                    ResultSet rs = ps.executeQuery();

                    DefaultTableModel model = new DefaultTableModel(new String[]{"Notice ID", "Title", "Content"}, 0);
                    while (rs.next()) {
                        int id = rs.getInt("notice_id");
                        String title = rs.getString("title");
                        String content = rs.getString("content");
                        model.addRow(new Object[]{id, title, content});
                    }

                    table1.setModel(model);

                    rs.close();
                    ps.close();
                    conn.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Main, "Search Error: " + ex.getMessage());
                }
            }
        });

        // 🔄 Refresh Notice
        refreshButton.addActionListener(new ActionListener() { // ✅ updated to refreshButton
            @Override
            public void actionPerformed(ActionEvent e) {
                loadNotices(); // Reload full table
            }
        });
    }

    // 🔁 Load all notices into the table
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