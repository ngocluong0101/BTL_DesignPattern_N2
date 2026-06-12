package com.clothingstore.service;


import com.clothingstore.dao.ConnectionManager;
import com.clothingstore.dao.user.CustomerDAO;
import com.clothingstore.model.Customer;
import java.sql.*;
import java.util.List;

public class CustomerService {
    private static CustomerService instance;
    private final CustomerDAO customerDAO = new CustomerDAO();

    private CustomerService() {}

    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    public String getMemberLevel(int customerId) {
        String sql = "SELECT level FROM customers WHERE id_customers = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("level");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeQuietly(rs);
            ConnectionManager.closeQuietly(stmt);
            ConnectionManager.closeQuietly(conn);
        }
        return "BRONZE"; // Default level
    }

    public int getPoints(int customerId) {
        String sql = "SELECT points FROM customers WHERE id_customers = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("points");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeQuietly(rs);
            ConnectionManager.closeQuietly(stmt);
            ConnectionManager.closeQuietly(conn);
        }
        return 0;
    }

    public void updatePoints(int customerId, int points) {
        String updatePointsSql = "UPDATE customers SET points = points + ? WHERE id_customers = ?";
        String getPointsSql = "SELECT points FROM customers WHERE id_customers = ?";
        String updateLevelSql = "UPDATE customers SET level = ? WHERE id_customers = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();

            // Cập nhật điểm
            stmt = conn.prepareStatement(updatePointsSql);
            stmt.setInt(1, points);
            stmt.setInt(2, customerId);
            stmt.executeUpdate();
            stmt.close();

            // Lấy tổng điểm mới
            stmt = conn.prepareStatement(getPointsSql);
            stmt.setInt(1, customerId);
            rs = stmt.executeQuery();
            int totalPoints = 0;
            if (rs.next()) {
                totalPoints = rs.getInt("points");
            }
            rs.close();
            stmt.close();

            // Sử dụng State Pattern để xác định level mới
            com.bach.model.Customer tempCustomer = new com.bach.model.Customer();
            tempCustomer.setPoints(totalPoints);
            String newLevel = tempCustomer.getLevel().getLevelName();

            // Cập nhật level nếu cần
            stmt = conn.prepareStatement(updateLevelSql);
            stmt.setString(1, newLevel);
            stmt.setInt(2, customerId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeQuietly(rs);
            ConnectionManager.closeQuietly(stmt);
            ConnectionManager.closeQuietly(conn);
        }
    }

    public double getLevelDiscountPercent(String level) {
        if (level == null) return 0.0;
        switch (level.toUpperCase()) {
            case "GOLD": return 0.10;
            case "SILVER": return 0.05;
            default: return 0.0;
        }
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    public void resetPoints(int customerId) {
        String updatePointsSql = "UPDATE customers SET points = 0 WHERE id_customers = ?";
        String updateLevelSql = "UPDATE customers SET level = ? WHERE id_customers = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConnectionManager.getConnection();
            // Đặt lại điểm
            stmt = conn.prepareStatement(updatePointsSql);
            stmt.setInt(1, customerId);
            stmt.executeUpdate();
            stmt.close();
            // Cập nhật level về BRONZE
            stmt = conn.prepareStatement(updateLevelSql);
            stmt.setString(1, "BRONZE");
            stmt.setInt(2, customerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeQuietly(stmt);
            ConnectionManager.closeQuietly(conn);
        }
    }
}