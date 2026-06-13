package com.clothingstore.patterns.decorator;


public class FixedAmountDiscountDecorator extends ProductDecorator{
    private double discountAmount;

    public FixedAmountDiscountDecorator(ProductComponent product, double discountAmount) {
        super(product);
        this.discountAmount = discountAmount;
    }

    @Override
    public double getPrice() {
        double originalPrice = super.getPrice();
        double discounted = originalPrice - discountAmount;
        return Math.max(0, discounted); // không cho âm giá
    }
}