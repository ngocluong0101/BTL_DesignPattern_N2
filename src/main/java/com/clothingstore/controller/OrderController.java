package com.clothingstore.controller;

import com.clothingstore.service.OrderService;
import com.clothingstore.view.order.OrderView;
import javax.swing.JOptionPane;


public class OrderController {
    private final OrderView orderView;
    private final OrderService orderService;

    public OrderController() {
        orderService = OrderService.getInstance();
        orderView = new OrderView();
        // Đăng ký listener để nhận sự kiện đặt hàng từ View
        orderView.setOrderListener(this::handleOrder);
        orderView.setVisible(true);
    }


    public void showOrderView() {
        orderView.setVisible(true);
    }

    public void hideOrderView() {
        orderView.setVisible(false);
    }

    public OrderView getOrderView() {
        return orderView;
    }

    // Thêm hàm xử lý logic đặt hàng
    private void handleOrder(String paymentMethod, String note, int cartId, int voucherIndex, double finalAmount) {
        int customerId = orderView.getCustomerId();
        orderService.createOrder(customerId, cartId, finalAmount, paymentMethod, note);
        // Áp dụng voucher nếu có
        if (voucherIndex > 0) {
            java.util.List<com.clothingstore.model.IVoucher> vouchers = orderService.getAvailableVouchers();
            if (voucherIndex - 1 < vouchers.size()) {
                com.clothingstore.model.IVoucher selectedVoucher = vouchers.get(voucherIndex - 1);
                orderService.applyVoucherToOrder(cartId, selectedVoucher.getId());
            }
        }
        JOptionPane.showMessageDialog(orderView, "Đặt hàng thành công!");
        orderView.reloadData();
    }
}
