package com.clothingstore.patterns.state;


import com.clothingstore.model.Order;

public class CompletedOrderState extends OrderState {
    @Override
    public void pay(Order order) {
        throw new IllegalStateException("Order is already completed.");
    }



    @Override
    public void complete(Order order) {
        throw new IllegalStateException("Order is already completed.");
    }

    @Override
    public void cancel(Order order) {
        throw new IllegalStateException("Order is already completed.");
    }

    @Override
    public String getStateName() {
        return "COMPLETED";
    }
}