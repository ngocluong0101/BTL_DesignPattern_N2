package com.clothingstore.dao.discount;


import com.clothingstore.dao.ConnectionManager;
import com.clothingstore.model.Discount;

import java.sql.*;

public class DiscountDAO {

    public void addDiscount(Discount d) {
        String sql = "INSERT INTO discount (product_id, discount_type, value, start_date, end_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, d.getProductId());
            ps.setString(2, d.getDiscountType());
            ps.setFloat(3, d.getValue());
            ps.setDate(4, new java.sql.Date(d.getStartDate().getTime()));
            ps.setDate(5, new java.sql.Date(d.getEndDate().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Discount getActiveDiscount(int productId) {
        String sql = "SELECT * FROM discount WHERE product_id = ? AND CURDATE() BETWEEN start_date AND end_date";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Discount d = new Discount();
                d.setId(rs.getInt("id"));
                d.setProductId(productId);
                d.setDiscountType(rs.getString("discount_type"));
                d.setValue(rs.getFloat("value"));
                d.setStartDate(rs.getDate("start_date"));
                d.setEndDate(rs.getDate("end_date"));
                return d;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateDiscount(Discount d) {
        String sql = "UPDATE discount SET discount_type = ?, value = ?, start_date = ?, end_date = ? WHERE id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, d.getDiscountType());
            stmt.setFloat(2, d.getValue());
            stmt.setDate(3, new java.sql.Date(d.getStartDate().getTime()));
            stmt.setDate(4, new java.sql.Date(d.getEndDate().getTime()));
            stmt.setInt(5, d.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDiscountByProductId(int productId) {
        String sql = "DELETE FROM discount WHERE product_id = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}