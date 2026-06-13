package com.clothingstore.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InvoiceService {
    public void createInvoice(int orderId) throws Exception {
        // 1. Lấy tổng tiền (total_amount) từ bảng orders
        double amount = 0;
        String getAmountSql = "SELECT total_amount FROM orders WHERE id_orders = ?";
        try (Connection conn = ConnectionManager.getInstance().getConnection();
             PreparedStatement stmt1 = conn.prepareStatement(getAmountSql)) {
            stmt1.setInt(1, orderId);
            try (ResultSet rs = stmt1.executeQuery()) {
                if (rs.next()) {
                    amount = rs.getDouble("total_amount");
                } else {
                    throw new Exception("Không tìm thấy đơn hàng ID: " + orderId);
                }
            }
            
            // 2. Tạo hóa đơn bán hàng vào bảng sales_bills
            String insertSql = "INSERT INTO sales_bills (id_orders, amount, booking_date, state) VALUES (?, ?, CURDATE(), 'PAID')";
            try (PreparedStatement stmt2 = conn.prepareStatement(insertSql)) {
                stmt2.setInt(1, orderId);
                stmt2.setInt(2, (int) amount); // sales_bills lưu amount dưới dạng INT theo thiết kế
                stmt2.executeUpdate();
                System.out.println("Đã tạo hóa đơn bán hàng thành công cho đơn hàng ID: " + orderId);
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi tạo hóa đơn bán hàng: " + e.getMessage());
            throw e; // Ném lỗi để PaymentFacade rollback transaction
        }
    }
}
