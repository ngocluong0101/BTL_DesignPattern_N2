package com.clothingstore.patterns.decorator;


public class DiscountDecorator extends ProductDecorator {
    private float discountValue;         // Giá trị giảm: có thể là phần trăm hoặc số tiền
    private String discountType;         // "percent" hoặc "amount"

    public DiscountDecorator(ProductComponent product, float discountValue, String discountType) {
        super(product);
        this.discountValue = discountValue;
        this.discountType = discountType;
    }

    @Override
    public double getPrice() {
        double originalPrice = product.getPrice();
        double finalPrice;

        if ("percent".equalsIgnoreCase(discountType)) {
            finalPrice = originalPrice * (1 - discountValue / 100f);
        } else if ("amount".equalsIgnoreCase(discountType)) {
            finalPrice = originalPrice - discountValue;
        } else {
            // Nếu không hợp lệ thì không giảm
            finalPrice = originalPrice;
        }

        return Math.max(0, finalPrice); // Không cho âm
    }
}