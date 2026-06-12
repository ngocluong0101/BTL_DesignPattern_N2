package com.clothingstore.model.product;


public class PremiumProduct extends Product1 {

    public PremiumProduct( int supplierId, String name, String description, double price, String state, int adminId) {
        super( supplierId, name, description, price, state, adminId);
    }

    public PremiumProduct(int id ,  int supplierId, String name, String description, double price, String state, int adminId) {
        super(id ,  supplierId, name, description, price, state, adminId);
    }
}