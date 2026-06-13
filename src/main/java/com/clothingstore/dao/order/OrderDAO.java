package com.clothingstore.dao.order;

import com.clothingstore.model.Order;
import com.clothingstore.database.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id_orders"));
                order.setBookingId(rs.getInt("id_bookings"));
                order.setOrderDate(rs.getDate("order_date"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setPaymentMethod(rs.getString("payment_method"));
                order.setNote(rs.getString("note"));

                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Trong thực tế bạn nên log hoặc xử lý exception cụ thể
        }

        return orders;
    }

    public List<Integer> getAllOrderIds() {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT id_orders FROM orders ORDER BY id_orders ASC";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ids.add(rs.getInt("id_orders"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ids;
    }
}