package com.clothingstore.model;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private String state;
    private int supplierId;
    private int adminId;

    public Product() {

    }

    public Product(int id, String name, String description, double price, String state, int supplierId, int adminId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.state = state;
        this.supplierId = supplierId;
        this.adminId = adminId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", state='" + state + '\'' +
                ", supplierId=" + supplierId +
                ", adminId=" + adminId +
                '}';
    }
}