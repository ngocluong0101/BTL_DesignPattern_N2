package com.clothingstore.model;

import com.clothingstore.patterns.strategy.FixedAmountDiscountStrategy;
import com.clothingstore.patterns.strategy.DiscountContext;

public class FixedAmountVoucher implements IVoucher {
    private int id;
    private String code;
    private String name;
    private double startValue;
    private double endValue;
    private boolean isActive;
    private double amount;
    private DiscountContext discountContext;

    public FixedAmountVoucher(int id, String code, String name, double startValue, double endValue, boolean isActive, double amount) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.startValue = startValue;
        this.endValue = endValue;
        this.isActive = isActive;
        this.amount = amount;
        this.discountContext = new DiscountContext();
        this.discountContext.setStrategy(new FixedAmountDiscountStrategy(amount));
    }

    @Override
    public double calculateDiscount(double originalPrice) {
        if (!isActive || originalPrice < startValue || originalPrice > endValue) {
            return 0;
        }
        return discountContext.calculateDiscount(originalPrice);
    }

    public double getAmount() { return amount; }

    @Override
    public String getDiscountDescription() {
        return String.format("-%,.0f VND", amount);
    }

    @Override
    public int getId() { return id; }
    @Override
    public String getCode() { return code; }
    @Override
    public String getName() { return name; }
    @Override
    public double getStartValue() { return startValue; }
    @Override
    public double getEndValue() { return endValue; }
    @Override
    public boolean isActive() { return isActive; }
}