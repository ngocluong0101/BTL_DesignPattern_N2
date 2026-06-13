package com.clothingstore.dao.notification;

import com.clothingstore.model.Notification;
import com.clothingstore.database.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationDAO {

    public List<Notification> getNotificationsByCustomerId(int customerId) {
        List<Notification> notifications = new ArrayList<>();

        String sql = "SELECT id_notification, id_customer, id_event, message, sent_at, status " +
                "FROM notifications WHERE id_customer = ? ORDER BY sent_at DESC";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerId);

            rs = stmt.executeQuery();
            while (rs.next()) {
                int idNotification = rs.getInt("id_notification");
                int idEvent = rs.getInt("id_event");
                String message = rs.getString("message");
                Timestamp sentAt = rs.getTimestamp("sent_at");
                String status = rs.getString("status");

                Notification notification = new Notification(
                        customerId,
                        idEvent,
                        message,
                        new Date(sentAt.getTime()),
                        status
                );

                notifications.add(notification);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeQuietly(rs);
            ConnectionManager.closeQuietly(stmt);
            ConnectionManager.closeQuietly(conn);
        }

        return notifications;
    }
}