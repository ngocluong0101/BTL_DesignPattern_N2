
package com.clothingstore.patterns.abstractfactory;

public class BasicProductFactory implements ProductFactory{
    @Override
    public Product1 createProduct(int supplierId, String name, String description, double price, String state, int adminId){
        return new BasicProduct(supplierId, name, description, price, state, adminId);
    }
    @Override
    public Product1 createProduct(int id ,int supplierId, String name, String description, double price, String state, int adminId){
        return new BasicProduct(supplierId, name, description, price, state, adminId);
    }
}
