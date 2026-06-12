package com.clothingstore.service;


import com.clothingstore.dao.CartDAO;
import com.clothingstore.model.Product;
import java.util.List;

public class CartService {
    private static CartService instance;
    private CartDAO cartDAO;

    private CartService() {
        this.cartDAO = new CartDAO();
    }

    public static CartService getInstance() {
        if (instance == null) {
            instance = new CartService();
        }
        return instance;
    }

    public List<Product> getCartProducts(int customerId) {
        return cartDAO.getCartProducts(customerId);
    }

    public void addToCart(int customerId, int productId) {
        // First get or create cart for customer
        int cartId = getOrCreateCart(customerId);

        // Then add product to cart
        cartDAO.addProductToCart(cartId, productId);
    }

    private int getOrCreateCart(int customerId) {
        int cartId = cartDAO.getCartId(customerId);

        if (cartId == -1) {
            // Create new cart
            cartId = cartDAO.createCart(customerId);
        }

        return cartId;
    }

    public void clearCart(int customerId) {
        int cartId = getOrCreateCart(customerId);
        cartDAO.clearCart(cartId);
    }
}