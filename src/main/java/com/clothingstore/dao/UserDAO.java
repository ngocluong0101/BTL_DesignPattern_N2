package com.clothingstore.dao;

import com.clothingstore.database.ConnectionManager;
import com.clothingstore.model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public Admin findByUsername(String username) {
        String sql = "SELECT id_admin, username, password, full_name, phone FROM admin WHERE username = ?";
        try (Connection conn = ConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getInt("id_admin"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                admin.setRole("admin");
                admin.setFullName(rs.getString("full_name"));
                return admin;
            }
        } catch (Exception e) {
            throw new IllegalStateException("Không thể truy vấn tài khoản admin", e);
        }
        return null;
    }
}
