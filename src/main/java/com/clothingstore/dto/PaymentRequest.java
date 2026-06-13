package com.clothingstore.dto;

public class PaymentRequest {
    private int orderId;
    private double amount;

    public PaymentRequest(int orderId, double amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    public int getOrderId() {
        return orderId;
    }

    public double getAmount() {
        return amount;
    }
}
