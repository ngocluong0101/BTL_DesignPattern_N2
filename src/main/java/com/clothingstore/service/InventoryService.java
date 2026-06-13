package com.clothingstore.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.clothingstore.database.ConnectionManager;

public class InventoryService {
    public void decreaseStock(int orderId) throws Exception {
        // Viết câu lệnh SQL thực tế: JOIN với order_details để lấy số lượng sản phẩm khách mua
        // Cập nhật (trừ) cột quantity trong bảng products. (Yêu cầu DB có cột quantity ở products hoặc bảng kho)
        String sql = "UPDATE products p " +
                     "INNER JOIN order_details od ON p.id_products = od.id_product " +
                     "SET p.quantity = p.quantity - od.quantity " +
                     "WHERE od.id_order = ?";
                     
        try (Connection conn = ConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Đã xuất kho thành công cho đơn hàng ID: " + orderId);
            } else {
                System.out.println("Không có sản phẩm nào được xuất kho (Đơn hàng trống hoặc không tồn tại).");
            }
        } catch (Exception e) {
            System.err.println("Lỗi trừ tồn kho (Có thể bảng products chưa có cột quantity): " + e.getMessage());
            throw e; // Ném lỗi ra để PaymentFacade rollback transaction nếu xuất kho thất bại
        }
    }
}
