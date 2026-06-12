package com.clothingstore.patterns.state;


import com.clothingstore.model.Order;

public class PaidOrderState extends OrderState {

    @Override
    public void cancel(Order order) {
        order.setState(new CanceledOrderState());
        order.setStatus("CANCELED");
    }

    @Override
    public void complete(Order order) {
        order.setState(new CompletedOrderState());
        order.setStatus("COMPLETED");
    }

    @Override
    public String getStateName() {
        return "PAID";
    }
}