/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clothingstore.patterns.abstractfactory;


/**
 *
 * @author ADMIN
 */
public abstract class Product1 {

    protected int id_products;
    protected int supplierId;
    protected String name;
    protected String description;
    protected double price;
    protected String state;
    protected int adminId;

    public Product1() {
    }

    public Product1(int supplierId, String name, String description,
         double price, String state, int adminId) {
        this.supplierId = supplierId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.state = state;
        this.adminId = adminId;
    }

    public Product1(int id_products, int supplierId, String name,
                   String description, double price,
                   String state, int adminId) {
        this.id_products = id_products;
        this.supplierId = supplierId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.state = state;
        this.adminId = adminId;
    }

    public int getId_products() {
        return id_products;
    }

    public void setId_products(int id_products) {
        this.id_products = id_products;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
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

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
}