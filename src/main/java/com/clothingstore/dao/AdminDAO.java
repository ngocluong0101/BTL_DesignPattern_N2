package com.clothingstore.dao;


import com.clothingstore.database.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AdminDAO {
    public List<Integer> getAllAdminIds() {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT id_admin FROM admin ORDER BY id_admin ASC";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ids.add(rs.getInt("id_admin"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ids;
    }

    public Map<String, Integer> getAllAdminMap() {
        Map<String, Integer> adminMap = new LinkedHashMap<>();
        String sql = "SELECT id_admin, full_name FROM admin ORDER BY full_name ASC";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_admin");
                String fullName = rs.getString("full_name");
                adminMap.put(fullName, id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return adminMap;
    }

    public String getAdminNameById(int id) {
        String sql = "SELECT full_name FROM admin WHERE id_admin = ?";
        String fullName = null;

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    fullName = rs.getString("full_name");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fullName;
    }

}