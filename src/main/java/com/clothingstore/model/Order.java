package com.clothingstore.model;

import java.util.Date;
import com.clothingstore.patterns.state.*;

public class Order {
    private int id;
    private int bookingId;
    private Date orderDate;
    private double totalAmount;
    private String status;
    private String paymentMethod;
    private String note;
    private IVoucher appliedVoucher;
    private double finalAmount;
    private OrderState state;

    public Order(){};
    public Order(int id, int bookingId, Date orderDate, double totalAmount,
                 String status, String paymentMethod, String note) {
        this.id = id;
        this.bookingId = bookingId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.note = note;
        this.finalAmount = totalAmount;
        this.state = stateFromStatus(status);
    }

    private OrderState stateFromStatus(String status) {
        if (status == null) return new PendingOrderState();
        switch (status.toUpperCase()) {
            case "PAID": return new PaidOrderState();
            case "COMPLETED": return new CompletedOrderState();
            case "CANCELED": return new CanceledOrderState();
            default: return new PendingOrderState();
        }
    }

    public void applyVoucher(IVoucher voucher) {
        this.appliedVoucher = voucher;
        if (voucher != null) {
            double discount = voucher.calculateDiscount(totalAmount);
            this.finalAmount = totalAmount - discount;
        } else {
            this.finalAmount = totalAmount;
        }
    }

    public void setState(OrderState state) {
        this.state = state;
    }
    public OrderState getState() {
        return state;
    }

    public void pay() { state.pay(this); }
    public void complete() { state.complete(this); }
    public void cancel() { state.cancel(this); }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) {
        this.status = status;
        this.state = stateFromStatus(status);
    }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public IVoucher getAppliedVoucher() { return appliedVoucher; }
    public void setAppliedVoucher(IVoucher appliedVoucher) { this.appliedVoucher = appliedVoucher; }

    public double getFinalAmount() { return finalAmount; }
    public void setFinalAmount(double finalAmount) { this.finalAmount = finalAmount; }
}