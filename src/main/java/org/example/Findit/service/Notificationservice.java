package org.example.Findit.service;

import org.example.Findit.DB_Connection;
import org.example.Findit.Model.Notification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Notificationservice {

    public void sendNotification(int userId, String message) throws SQLException {
        String sql = "INSERT INTO notifications (user_id, message, is_read) VALUES (?, ?, false)";
        Connection conn = DB_Connection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, message);
            stmt.executeUpdate();
        }
    }

    public List<Notification> getNotificationsByUser(int userId) throws SQLException {
        String sql = "SELECT * FROM notifications WHERE user_id = ? ORDER BY created_at DESC";
        List<Notification> notifications = new ArrayList<>();
        Connection conn = DB_Connection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    notifications.add(mapRowToNotification(rs));
                }
            }
        }
        return notifications;
    }

    public void markAsRead(int notificationId) throws SQLException {
        String sql = "UPDATE notifications SET is_read = true WHERE notification_id = ?";
        Connection conn = DB_Connection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, notificationId);
            stmt.executeUpdate();
        }
    }

    private Notification mapRowToNotification(ResultSet rs) throws SQLException {
        return new Notification(
                rs.getInt("notification_id"),
                rs.getInt("user_id"),
                rs.getString("message"),
                rs.getBoolean("is_read")
        );
    }
}