package com.clothingstore.patterns.facade;

import com.clothingstore.database.ConnectionManager;
import com.clothingstore.dto.PaymentRequest;
import com.clothingstore.dto.PaymentResult;
import com.clothingstore.patterns.sessionsingleton.Session;
import com.clothingstore.patterns.strategy.IPaymentStrategy;
import com.clothingstore.service.InvoiceService;
import com.clothingstore.service.OrderService;
import com.clothingstore.service.InventoryService;

import java.sql.Connection;
import java.sql.SQLException;

public class PaymentFacade {
    private OrderService orderService;
    private InvoiceService invoiceService;
    private InventoryService inventoryService;

    public PaymentFacade() {
        this.orderService = OrderService.getInstance();
        this.invoiceService = new InvoiceService();
        this.inventoryService = new InventoryService();
    }

    public PaymentResult pay(int orderId, IPaymentStrategy paymentStrategy) {
        if (!Session.getInstance().isLoggedIn()) {
            return new PaymentResult(false, "Vui lòng đăng nhập trước khi thanh toán");
        }

        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            conn.setAutoCommit(false); // Bắt đầu transaction

            // 1. Lấy tổng tiền thực tế từ đơn hàng
            double totalAmount = 0.0;
            String amountSql = "SELECT total_amount FROM orders WHERE id_orders = ?";
            try (java.sql.PreparedStatement stmt = conn.prepareStatement(amountSql)) {
                stmt.setInt(1, orderId);
                try (java.sql.ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        totalAmount = rs.getDouble("total_amount");
                    } else {
                        return new PaymentResult(false, "Không tìm thấy đơn hàng ID: " + orderId);
                    }
                }
            }

            PaymentRequest request = new PaymentRequest(orderId, totalAmount);

            // 2. Xử lý thanh toán thông qua Strategy
            PaymentResult strategyResult = paymentStrategy.processPayment(request);
            if (!strategyResult.isSuccess()) {
                conn.rollback();
                return strategyResult;
            }

            // 3. Cập nhật hóa đơn thực tế vào bảng sales_bills
            invoiceService.createInvoice(orderId);
            
            // 4. Trừ tồn kho thực tế bằng INNER JOIN
            inventoryService.decreaseStock(orderId);

            // 5. Đánh dấu đơn hàng là 'PAID'
            String updateOrderSql = "UPDATE orders SET status = 'PAID' WHERE id_orders = ?";
            try (java.sql.PreparedStatement stmtUpdate = conn.prepareStatement(updateOrderSql)) {
                stmtUpdate.setInt(1, orderId);
                stmtUpdate.executeUpdate();
            }

            conn.commit(); // Hoàn tất transaction
            return new PaymentResult(true, "Giao dịch thanh toán hoàn tất thành công. " + strategyResult.getMessage());
            
        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return new PaymentResult(false, "Lỗi hệ thống trong quá trình thanh toán: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
