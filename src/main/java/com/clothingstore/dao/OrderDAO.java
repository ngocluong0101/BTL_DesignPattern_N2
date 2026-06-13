package com.clothingstore.dao;


import java.sql.*;
import com.clothingstore.model.Order;
import java.util.Date;

public class OrderDAO {
    public int createBooking(Connection conn, int customerId, int cartId, double totalAmount) throws SQLException {
        String sql = "INSERT INTO bookings (id_customers, id_carts, start_date, end_date, status, amount) " +
                "VALUES (?, ?, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), 'PENDING', ?)";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, customerId);
            stmt.setInt(2, cartId);
            stmt.setDouble(3, totalAmount);
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } finally {
            ConnectionManager.closeQuietly(rs);
            ConnectionManager.closeQuietly(stmt);
        }
        return -1;
    }

    public void createOrderRecord(Connection conn, int bookingId, double totalAmount, String paymentMethod, String note) throws SQLException {
        String sql = "INSERT INTO orders (id_bookings, order_date, total_amount, status, payment_method, note) " +
                "VALUES (?, CURDATE(), ?, 'PENDING', ?, ?)";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookingId);
            stmt.setDouble(2, totalAmount);
            stmt.setString(3, paymentMethod);
            stmt.setString(4, note);
            stmt.executeUpdate();
        } finally {
            ConnectionManager.closeQuietly(stmt);
        }
    }

    public void applyVoucherToOrder(int orderId, int voucherId) {
        String sql = "UPDATE vouchers SET id_order = ? WHERE id_vouchers = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConnectionManager.getInstance().getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            stmt.setInt(2, voucherId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeQuietly(stmt);
            ConnectionManager.closeQuietly(conn);
        }
    }

    public void updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id_orders = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConnectionManager.getInstance().getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeQuietly(stmt);
            ConnectionManager.closeQuietly(conn);
        }
    }

    public Order getLatestOrderForCustomer(int customerId) {
        String sql = "SELECT * FROM orders WHERE id_bookings IN (SELECT id_bookings FROM bookings WHERE id_customers = ?) ORDER BY id_orders DESC LIMIT 1";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getInstance().getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id_orders");
                int bookingId = rs.getInt("id_bookings");
                Date orderDate = rs.getDate("order_date");
                double totalAmount = rs.getDouble("total_amount");
                String status = rs.getString("status");
                String paymentMethod = rs.getString("payment_method");
                String note = rs.getString("note");
                return new Order(id, bookingId, orderDate, totalAmount, status, paymentMethod, note);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeQuietly(rs);
            ConnectionManager.closeQuietly(stmt);
            ConnectionManager.closeQuietly(conn);
        }
        return null;
    }

    public int getCustomerIdByOrder(Order order) {
        String sql = "SELECT id_customers FROM bookings WHERE id_bookings = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getInstance().getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, order.getBookingId());
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_customers");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeQuietly(rs);
            ConnectionManager.closeQuietly(stmt);
            ConnectionManager.closeQuietly(conn);
        }
        return -1;
    }
}