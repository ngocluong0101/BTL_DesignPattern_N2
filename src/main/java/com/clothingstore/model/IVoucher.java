package com.clothingstore.model;


public interface IVoucher {
    int getId();
    String getCode();
    String getName();
    double getStartValue();
    double getEndValue();
    boolean isActive();
    double calculateDiscount(double originalPrice);
    String getDiscountDescription();
}