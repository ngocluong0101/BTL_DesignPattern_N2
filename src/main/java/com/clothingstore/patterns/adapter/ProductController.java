package com.clothingstore.patterns.adapter;

import com.clothingstore.dao.product.ProductDAO;
import com.clothingstore.model.product.Product1;
import com.clothingstore.service.ProductService;

import java.util.List;

public class ProductController implements ProductService {
    private ProductDAO dao = new ProductDAO();

    @Override
    public void add(Product1 p) {dao.insert(p);}

    @Override
    public void update(Product1 p) {dao.update(p);}

    @Override
    public void delete(int id) {dao.delete(id);}

    @Override
    public List<Product1> getAll() {return dao.findAll(); }
}
