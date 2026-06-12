package com.clothingstore.patterns.state;


import com.clothingstore.model.Order;

public abstract class OrderState {
    public void pay(Order order) {
        throw new IllegalStateException("Không thể thanh toán ở trạng thái hiện tại.");
    }
    public void complete(Order order) {
        throw new IllegalStateException("Không thể hoàn thành ở trạng thái hiện tại.");
    }
    public void cancel(Order order) {
        throw new IllegalStateException("Không thể huỷ ở trạng thái hiện tại.");
    }
    public abstract String getStateName();
}