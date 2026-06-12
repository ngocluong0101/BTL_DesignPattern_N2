package com.clothingstore.dao;

import com.clothingstore.model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    public List<Product> getCartProducts(int customerId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.* FROM products p " +
                "JOIN product_carts pc ON p.id_products = pc.id_product " +
                "JOIN carts c ON pc.id_carts = c.id_carts " +
                "WHERE c.id_customers = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id_products"));
                product.setName(rs.getString("name_products"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setState(rs.getString("state"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeQuietly(rs);
            ConnectionManager.closeQuietly(stmt);
            ConnectionManager.closeQuietly(conn);
        }
        return products;
    }

    public void addProductToCart(int cartId, int productId) {
        String sql = "INSERT INTO product_carts (id_carts, id_product) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cartId);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeQuietly(stmt);
            ConnectionManager.closeQuietly(conn);
        }
    }

    public int getCartId(int customerId) {
        String sql = "SELECT id_carts FROM carts WHERE id_customers = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_carts");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeQuietly(rs);
            ConnectionManager.closeQuietly(stmt);
            ConnectionManager.closeQuietly(conn);
        }
        return -1;
    }

    public int createCart(int customerId) {
        String insertSql = "INSERT INTO carts (id_customers) VALUES (?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, customerId);
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeQuietly(stmt);
            ConnectionManager.closeQuietly(conn);
        }
        return -1;
    }

    public void clearCart(int cartId) {
        String sql = "DELETE FROM product_carts WHERE id_carts = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cartId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeQuietly(stmt);
            ConnectionManager.closeQuietly(conn);
        }
    }
}