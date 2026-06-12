package com.clothingstore.model;


import java.util.Date;

public class Discount {
    private int id;
    private int productId;
    private String discountType;
    private float value;
    private Date startDate;
    private Date endDate;

    // Constructor mặc định
    public Discount() {}

    // Constructor đầy đủ tham số
    public Discount(int id, int productId, String discountType, float value, Date startDate, Date endDate) {
        this.id = id;
        this.productId = productId;
        this.discountType = discountType;
        this.value = value;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}