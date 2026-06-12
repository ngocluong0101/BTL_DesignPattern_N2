package com.clothingstore.patterns.strategy;

public class PercentageDiscountStrategy implements DiscountStrategy {
    private final double percentage;

    public PercentageDiscountStrategy(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double calculateDiscount(double originalPrice) {
        return originalPrice * (percentage / 100.0);
    }

    @Override
    public String getDescription() {
        return percentage + "% discount";
    }

    @Override
    public String getType() {
        return "Percentage";
    }

    @Override
    public double getValue() {
        return percentage;
    }
}