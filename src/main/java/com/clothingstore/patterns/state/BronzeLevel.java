package com.clothingstore.patterns.state;


public class BronzeLevel extends MemberLevel {
    @Override
    public String getLevelName() {
        return "BRONZE";
    }

    @Override
    public double getDiscountRate() {
        return 0.0;
    }

    @Override
    public int getRequiredPoints() {
        return 0;
    }

    @Override
    public String getDescription() {
        return "Basic member level with 5% discount";
    }
}