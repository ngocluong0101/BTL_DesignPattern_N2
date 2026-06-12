package com.clothingstore.patterns.state;

import com.clothingstore.model.Order;

public class PendingOrderState extends OrderState {
    @Override
    public void pay(Order order) {
        order.setState(new PaidOrderState());
        order.setStatus("PAID");
    }

    @Override
    public void complete(Order order) {
        throw new IllegalStateException("Cannot complete a pending order.");
    }

    @Override
    public void cancel(Order order) {
        order.setState(new CanceledOrderState());
        order.setStatus("CANCELED");
    }

    @Override
    public String getStateName() {
        return "PENDING";
    }
}