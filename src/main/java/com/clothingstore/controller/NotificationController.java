package com.clothingstore.controller;

import com.clothingstore.dao.notification.NotificationDAO;
import com.clothingstore.model.Notification;
import com.clothingstore.view.customer.NotificationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NotificationController {
    private final NotificationView view;
    private final NotificationDAO dao;

    public NotificationController(int customerId) {
        dao = new NotificationDAO();
        view = new NotificationView();

        // Lấy danh sách thông báo từ DB
        List<Notification> notifications = dao.getNotificationsByCustomerId(customerId);

        // Format dữ liệu hiển thị
        List<String> formatted = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Notification n : notifications) {
            String msg = String.format(
                    "[%s] - %s (Status: %s)",
                    sdf.format(n.getSentAt()),
                    n.getMessage(),
                    n.getStatus()
            );
            formatted.add(msg);
        }

        view.displayNotifications(formatted);
        view.setVisible(true);
    }
}