package com.clothingstore.patterns.state;

public class GoldLevel extends MemberLevel {
    @Override
    public String getLevelName() {
        return "GOLD";
    }

    @Override
    public double getDiscountRate() {
        return 0.10;
    }

    @Override
    public int getRequiredPoints() {
        return 5000;
    }

    @Override
    public String getDescription() {
        return "VIP member level with 10% discount";
    }
}