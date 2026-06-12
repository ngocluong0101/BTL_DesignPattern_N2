package com.clothingstore.patterns.adapter;

import java.util.List;

import com.clothingstore.model.product.Product1;

public interface ProductService {
    void add(Product1 p);
    void update(Product1 p);
    void delete(int id);
    List<Product1> getAll();
}
