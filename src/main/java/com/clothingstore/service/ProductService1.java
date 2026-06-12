package com.clothingstore.service;


import com.clothingstore.dao.discount.DiscountDAO;
import com.clothingstore.dao.product.ProductDAO;
import com.clothingstore.model.Discount;
import com.clothingstore.model.Product;
import com.clothingstore.patterns.strategy.DiscountStrategy;
import com.clothingstore.patterns.strategy.PercentageDiscountStrategy;
import com.clothingstore.patterns.strategy.FixedAmountDiscountStrategy;
import com.clothingstore.patterns.strategy.DiscountContext;

import java.util.List;

public class ProductService1 {
    private final ProductDAO productDAO = new ProductDAO();
    private final DiscountDAO discountDAO = new DiscountDAO();
    private final DiscountContext discountContext = new DiscountContext();

    public List<Product> getAll() {
        return productDAO.getAllProducts();
    }

    // Cập nhật giá sản phẩm (chỉ dùng nếu cần)
    public void updatePrice(int id, float price) {
        productDAO.updatePrice(id, price);
    }

    public double getDiscountedPrice(Product p) {
        Discount d = discountDAO.getActiveDiscount(p.getId());
        if (d != null) {
            if ("percent".equals(d.getDiscountType())) {
                discountContext.setStrategy(new PercentageDiscountStrategy(d.getValue()));
            } else {
                discountContext.setStrategy(new FixedAmountDiscountStrategy(d.getValue()));
            }
            double discount = discountContext.calculateDiscount(p.getPrice());
            return p.getPrice() - discount;
        }
        return p.getPrice();
    }

}