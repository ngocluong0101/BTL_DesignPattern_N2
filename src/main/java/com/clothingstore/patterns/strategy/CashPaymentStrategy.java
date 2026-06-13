package com.clothingstore.patterns.strategy;

import com.clothingstore.dto.PaymentRequest;
import com.clothingstore.dto.PaymentResult;

public class CashPaymentStrategy implements IPaymentStrategy {
    @Override
    public PaymentResult processPayment(PaymentRequest request) {
        // Trong thực tế, ở đây sẽ giao tiếp với máy POS hoặc ngăn kéo đựng tiền
        String msg = String.format("Thanh toán bằng Tiền mặt thành công. Đơn hàng ID: %d, Số tiền: %,.2f VNĐ", 
                                   request.getOrderId(), request.getAmount());
        System.out.println("[Log] " + msg);
        return new PaymentResult(true, msg);
    }
}
