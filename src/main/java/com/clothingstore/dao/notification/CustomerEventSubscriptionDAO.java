package com.clothingstore.dao.notification;


import com.clothingstore.database.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerEventSubscriptionDAO {

    // ✅ Kiểm tra khách đã đăng ký chưa
    public boolean isSubscribed(int customerId) {
        String sql = "SELECT COUNT(*) FROM customer_event_subscriptions WHERE id_customer = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // ✅ Thêm đăng ký (chỉ khi chưa đăng ký)
    public String insertSubscription(int customerId) {
        if (isSubscribed(customerId)) {
            return "Bạn  đã đăng ký trước đó.";
        }

        String sql = "INSERT INTO customer_event_subscriptions (id_customer) VALUES (?)";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                return "Đăng ký thành công.";
            } else {
                return "Đăng ký thất bại.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi khi thực hiện đăng ký: " + e.getMessage();
        }
    }

    // ✅ Xóa đăng ký theo ID khách hàng
    public String deleteSubscriptionByCustomerId(int customerId) {
        if (!isSubscribed(customerId)) {
            return "Bạn chưa đăng ký nên không thể hủy.";
        }

        String sql = "DELETE FROM customer_event_subscriptions WHERE id_customer = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                return "Hủy đăng ký thành công.";
            } else {
                return "Không thể hủy đăng ký.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi khi hủy đăng ký: " + e.getMessage();
        }
    }
}