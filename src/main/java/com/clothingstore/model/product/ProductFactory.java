package com.clothingstore.model.product;

public interface ProductFactory {
    Product1 createProduct(int supplierId, String name, String description, double price, String state, int adminId);

    Product1 createProduct(int id, int supplierId, String name, String description, double price, String state, int adminId);
}