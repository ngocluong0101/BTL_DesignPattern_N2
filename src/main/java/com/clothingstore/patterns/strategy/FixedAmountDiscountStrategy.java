package com.clothingstore.patterns.strategy;


public class FixedAmountDiscountStrategy implements DiscountStrategy {
    private final double amount;

    public FixedAmountDiscountStrategy(double amount) {
        this.amount = amount;
    }

    @Override
    public double calculateDiscount(double originalPrice) {
        return Math.min(amount, originalPrice);
    }

    @Override
    public String getDescription() {
        return "Fixed discount of " + amount;
    }

    @Override
    public String getType() {
        return "Fixed Amount";
    }

    @Override
    public double getValue() {
        return amount;
    }
}