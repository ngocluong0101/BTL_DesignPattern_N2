/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.clothingstore.patterns.abstractfactory;

/**
 *
 * @author ADMIN
 */
public interface ProductFactory {
    Product1 createProduct (int supplierId, String name, String description, double price, String state, int adminId);
    Product1 createProduct (int id ,int supplierId, String name, String description, double price, String state, int adminId);
    
}
