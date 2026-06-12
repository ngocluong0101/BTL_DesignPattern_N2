package com.clothingstore.dao;

import com.clothingstore.model.Supplier;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    public List<Supplier> findAll() {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM suppliers ORDER BY id_suppliers DESC";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToSupplier(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Supplier> search(String keyword, String category, String state) {
        List<Supplier> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM suppliers WHERE 1=1");
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (name LIKE ? OR phone LIKE ? OR email LIKE ? OR tax_code LIKE ?)");
        }
        if (category != null && !category.equals("Tất cả")) {
            sql.append(" AND category = ?");
        }
        if (state != null && !state.equals("Tất cả")) {
            sql.append(" AND state = ?");
        }
        
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            
            int paramIndex = 1;
            if (keyword != null && !keyword.trim().isEmpty()) {
                String searchPattern = "%" + keyword.trim() + "%";
                stmt.setString(paramIndex++, searchPattern);
                stmt.setString(paramIndex++, searchPattern);
                stmt.setString(paramIndex++, searchPattern);
                stmt.setString(paramIndex++, searchPattern);
            }
            if (category != null && !category.equals("Tất cả")) {
                stmt.setString(paramIndex++, category);
            }
            if (state != null && !state.equals("Tất cả")) {
                String stateValue = state.equals("Đang hợp tác") ? "ACTIVE" : (state.equals("Ngừng hợp tác") ? "INACTIVE" : state);
                stmt.setString(paramIndex++, stateValue);
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToSupplier(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Supplier findById(int id) {
        String sql = "SELECT * FROM suppliers WHERE id_suppliers = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToSupplier(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(Supplier supplier) {
        String sql = "INSERT INTO suppliers (name, phone, address, email, tax_code, category, contact_person, state, id_admin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getPhone());
            stmt.setString(3, supplier.getAddress());
            stmt.setString(4, supplier.getEmail());
            stmt.setString(5, supplier.getTaxCode());
            stmt.setString(6, supplier.getCategory());
            stmt.setString(7, supplier.getContactPerson());
            String st = supplier.getState().equalsIgnoreCase("active") ? "ACTIVE" : "INACTIVE";
            stmt.setString(8, st);
            stmt.setInt(9, supplier.getAdminId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Supplier supplier) {
        String sql = "UPDATE suppliers SET name=?, phone=?, address=?, email=?, tax_code=?, category=?, contact_person=?, state=? WHERE id_suppliers=?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getPhone());
            stmt.setString(3, supplier.getAddress());
            stmt.setString(4, supplier.getEmail());
            stmt.setString(5, supplier.getTaxCode());
            stmt.setString(6, supplier.getCategory());
            stmt.setString(7, supplier.getContactPerson());
            String st = supplier.getState().equalsIgnoreCase("active") ? "ACTIVE" : "INACTIVE";
            stmt.setString(8, st);
            stmt.setInt(9, supplier.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM suppliers WHERE id_suppliers = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean existsByPhoneExceptId(String phone, int supplierId) {
        String sql = "SELECT COUNT(*) FROM suppliers WHERE phone = ? AND id_suppliers != ?";
        return checkExists(sql, phone, supplierId);
    }

    public boolean existsByEmailExceptId(String email, int supplierId) {
        String sql = "SELECT COUNT(*) FROM suppliers WHERE email = ? AND id_suppliers != ?";
        return checkExists(sql, email, supplierId);
    }

    public boolean existsByTaxCodeExceptId(String taxCode, int supplierId) {
        String sql = "SELECT COUNT(*) FROM suppliers WHERE tax_code = ? AND id_suppliers != ?";
        return checkExists(sql, taxCode, supplierId);
    }

    public boolean hasRelatedProducts(int supplierId) {
        String sql = "SELECT COUNT(*) FROM products WHERE id_suppliers = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, supplierId);
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

    private boolean checkExists(String sql, String value, int id) {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, value);
            if (id != -1) {
                stmt.setInt(2, id);
            }
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

    private Supplier mapResultSetToSupplier(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_suppliers");
        String name = rs.getString("name");
        String phone = rs.getString("phone");
        String address = rs.getString("address");
        String email = rs.getString("email");
        String taxCode = rs.getString("tax_code");
        String category = rs.getString("category");
        String contactPerson = rs.getString("contact_person");
        int adminId = rs.getInt("id_admin");
        Supplier supplier = new Supplier(id, name, phone, address, email, taxCode, category, contactPerson, adminId);
        
        String state = rs.getString("state");
        if ("ACTIVE".equalsIgnoreCase(state)) {
            supplier.changeState(new com.clothingstore.patterns.supplierstate.ActiveSupplierState(supplier));
        } else {
            supplier.changeState(new com.clothingstore.patterns.supplierstate.InActiveSupplierState(supplier));
        }
        return supplier;
    }
}
