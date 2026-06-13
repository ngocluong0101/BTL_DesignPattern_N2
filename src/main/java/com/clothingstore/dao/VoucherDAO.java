package com.clothingstore.dao;

import com.clothingstore.model.IVoucher;
import com.clothingstore.model.PercentageVoucher;
import com.clothingstore.model.FixedAmountVoucher;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.clothingstore.database.ConnectionManager;

public class VoucherDAO {
    public void saveVoucher(IVoucher voucher) {
        String sql = "INSERT INTO vouchers (id_admin, name_vouchers, start_value, end_value, is_active, code, discount_type, discount_value) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConnectionManager.getInstance().getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, 1); // Default admin ID
            stmt.setString(2, voucher.getName());
            stmt.setDouble(3, voucher.getStartValue());
            stmt.setDouble(4, voucher.getEndValue());
            stmt.setBoolean(5, true);
            stmt.setString(6, voucher.getCode());
            if (voucher instanceof PercentageVoucher) {
                stmt.setString(7, "Percentage");
                stmt.setDouble(8, ((PercentageVoucher) voucher).getPercentage());
            } else if (voucher instanceof FixedAmountVoucher) {
                stmt.setString(7, "Fixed Amount");
                stmt.setDouble(8, ((FixedAmountVoucher) voucher).getAmount());
            } else {
                stmt.setString(7, "Unknown");
                stmt.setDouble(8, 0);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeQuietly(stmt);
            ConnectionManager.closeQuietly(conn);
        }
    }

    public List<IVoucher> getAllVouchers() {
        List<IVoucher> vouchers = new ArrayList<>();
        String sql = "SELECT * FROM vouchers WHERE is_active = true";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getInstance().getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id_vouchers");
                String code = rs.getString("code");
                String name = rs.getString("name_vouchers");
                double startValue = rs.getDouble("start_value");
                double endValue = rs.getDouble("end_value");
                boolean isActive = rs.getBoolean("is_active");
                String discountType = rs.getString("discount_type");
                double discountValue = rs.getDouble("discount_value");
                IVoucher voucher;
                if ("Percentage".equalsIgnoreCase(discountType)) {
                    voucher = new PercentageVoucher(id, code, name, startValue, endValue, isActive, discountValue);
                } else {
                    voucher = new FixedAmountVoucher(id, code, name, startValue, endValue, isActive, discountValue);
                }
                vouchers.add(voucher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeQuietly(rs);
            ConnectionManager.closeQuietly(stmt);
            ConnectionManager.closeQuietly(conn);
        }
        return vouchers;
    }

    public void deactivateVoucher(int voucherId) {
        String sql = "UPDATE vouchers SET is_active = false WHERE id_vouchers = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConnectionManager.getInstance().getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, voucherId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeQuietly(stmt);
            ConnectionManager.closeQuietly(conn);
        }
    }

    public void updateVoucher(IVoucher voucher) {
        String sql = "UPDATE vouchers SET name_vouchers = ?, start_value = ?, end_value = ?, code = ?, discount_type = ?, discount_value = ? WHERE id_vouchers = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConnectionManager.getInstance().getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, voucher.getName());
            stmt.setDouble(2, voucher.getStartValue());
            stmt.setDouble(3, voucher.getEndValue());
            stmt.setString(4, voucher.getCode());
            if (voucher instanceof com.clothingstore.model.PercentageVoucher) {
                stmt.setString(5, "Percentage");
                stmt.setDouble(6, ((com.clothingstore.model.PercentageVoucher) voucher).getPercentage());
            } else if (voucher instanceof com.clothingstore.model.FixedAmountVoucher) {
                stmt.setString(5, "Fixed Amount");
                stmt.setDouble(6, ((com.clothingstore.model.FixedAmountVoucher) voucher).getAmount());
            } else {
                stmt.setString(5, "Unknown");
                stmt.setDouble(6, 0);
            }
            stmt.setInt(7, voucher.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeQuietly(stmt);
            ConnectionManager.closeQuietly(conn);
        }
    }
}