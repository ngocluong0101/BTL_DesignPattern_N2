package com.clothingstore.patterns.state;


public class SilverLevel extends MemberLevel {
    @Override
    public String getLevelName() {
        return "SILVER";
    }

    @Override
    public double getDiscountRate() {
        return 0.05;
    }

    @Override
    public int getRequiredPoints() {
        return 1000;
    }

    @Override
    public String getDescription() {
        return "Premium member level with 10% discount";
    }
}