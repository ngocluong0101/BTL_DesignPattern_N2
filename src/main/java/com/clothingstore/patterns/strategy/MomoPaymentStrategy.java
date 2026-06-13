package com.clothingstore.patterns.strategy;

import com.clothingstore.dto.PaymentRequest;
import com.clothingstore.dto.PaymentResult;

public class MomoPaymentStrategy implements IPaymentStrategy {
    @Override
    public PaymentResult processPayment(PaymentRequest request) {
        // Trong thực tế sẽ tạo mã QR Momo, gửi API đến Momo Gateway và chờ IPN/Webhook phản hồi
        String msg = String.format("Thanh toán qua Ví MoMo thành công. Đơn hàng ID: %d, Số tiền: %,.2f VNĐ", 
                                   request.getOrderId(), request.getAmount());
        System.out.println("[Log - Momo Gateway] " + msg);
        return new PaymentResult(true, msg);
    }
}
