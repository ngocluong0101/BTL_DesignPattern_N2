package com.clothingstore.patterns.strategy;

import com.clothingstore.dto.PaymentRequest;
import com.clothingstore.dto.PaymentResult;

public interface IPaymentStrategy {
    PaymentResult processPayment(PaymentRequest request);
}
