package com.clothingstore.service;

import com.clothingstore.dao.ConnectionManager;
import com.clothingstore.dao.OrderDAO;
import com.clothingstore.model.Order;
import com.clothingstore.model.IVoucher;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private static OrderService instance;
    private final VoucherService voucherService;
    private final OrderDAO orderDAO = new OrderDAO();

    private OrderService() {
        this.voucherService = VoucherService.getInstance();
    }

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderService();
        }
        return instance;
    }

    public List<IVoucher> getAvailableVouchers() {
        return voucherService.getAllVouchers();
    }

    public void createOrder(int customerId, int cartId, double totalAmount, String paymentMethod, String note) {
        Connection conn = null;
        try {
            conn = ConnectionManager.getConnection();
            conn.setAutoCommit(false);

            int bookingId = orderDAO.createBooking(conn, customerId, cartId, totalAmount);
            orderDAO.createOrderRecord(conn, bookingId, totalAmount, paymentMethod, note);

            conn.commit();
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ConnectionManager.closeQuietly(conn);
        }
        int pointsToAdd = (int) (totalAmount / 10000);
        CustomerService.getInstance().updatePoints(customerId, pointsToAdd);

    }

    public void applyVoucherToOrder(int orderId, int voucherId) {
        orderDAO.applyVoucherToOrder(orderId, voucherId);
    }

    public boolean processPayment(Order order) {

        order.pay();
        return true;
    }

    public void payOrder(Order order) {
        order.pay();
        orderDAO.updateOrderStatus(order.getId(), order.getStatus());

    }

    private int getCustomerIdByOrder(Order order) {
        return orderDAO.getCustomerIdByOrder(order);
    }


    public void completeOrder(Order order) {
        order.complete();
        orderDAO.updateOrderStatus(order.getId(), order.getStatus());
        // Xóa sản phẩm trong giỏ hàng khi hoàn thành đơn hàng
        CartService.getInstance().clearCart(getCustomerIdByOrder(order));
    }

    public void cancelOrder(Order order) {
        order.cancel();
        orderDAO.updateOrderStatus(order.getId(), order.getStatus());
    }

    public Order getLatestOrderForCustomer(int customerId) {
        return orderDAO.getLatestOrderForCustomer(customerId);
    }
}