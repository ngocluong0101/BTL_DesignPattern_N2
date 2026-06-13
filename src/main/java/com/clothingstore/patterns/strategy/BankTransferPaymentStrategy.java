package com.clothingstore.patterns.strategy;

import com.clothingstore.dto.PaymentRequest;
import com.clothingstore.dto.PaymentResult;

public class BankTransferPaymentStrategy implements IPaymentStrategy {
    @Override
    public PaymentResult processPayment(PaymentRequest request) {
        // Trong thực tế sẽ gọi API ngân hàng (Ví dụ: Napas, Vietcombank API) để xác nhận biến động số dư
        String msg = String.format("Thanh toán bằng Chuyển khoản thành công. Đơn hàng ID: %d, Số tiền: %,.2f VNĐ", 
                                   request.getOrderId(), request.getAmount());
        System.out.println("[Log - Bank API] " + msg);
        return new PaymentResult(true, msg);
    }
}
