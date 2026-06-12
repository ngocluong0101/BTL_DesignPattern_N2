/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clothingstore.patterns.abstractfactory;

/**
 *
 * @author ADMIN
 */
public class PremiumProduct extends Product1{

    public PremiumProduct(int supplierId, String name, String description, double price, String state, int adminId) {
        super(supplierId, name, description, price, state, adminId);
    }

    public PremiumProduct(int id, int supplierId, String name, String description, double price, String state, int adminId) {
        super(id, supplierId, name, description, price, state, adminId);
    }
    
}
