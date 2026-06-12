package com.clothingstore.patterns.strategy;


public class DiscountContext {
    private DiscountStrategy strategy;

    public void setStrategy(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculateDiscount(double amount) {
        return strategy != null ? strategy.calculateDiscount(amount) : 0;
    }

    public String getStrategyDescription() {
        return strategy != null ? strategy.getDescription() : "No discount";
    }

    public String getStrategyType() {
        return strategy != null ? strategy.getType() : "None";
    }

    public double getStrategyValue() {
        return strategy != null ? strategy.getValue() : 0;
    }

    public boolean hasStrategy() {
        return strategy != null;
    }
}