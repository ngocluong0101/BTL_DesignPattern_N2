package com.clothingstore.patterns.state;

public abstract class MemberLevel {
    public abstract String getLevelName();
    public abstract double getDiscountRate();
    public abstract int getRequiredPoints();
    public String getDescription() {
        return getLevelName() + " (" + (int)(getDiscountRate()*100) + "% giảm giá, từ " + getRequiredPoints() + " điểm)";
    }
}