package com.clothingstore.patterns.userbuilder;


import com.clothingstore.model.Customer;

import java.util.Date;

public class CustomerBuilder implements UserBuilder {
    private Customer customer;

    @Override
    public void reset() {
        customer = new Customer();
    }

    @Override
    public void id(int id) {
        if (customer == null) {
            throw new RuntimeException("Builder not initialized. Call reset() first.");
        }
        customer.setId(id);
    }

    @Override
    public void username(String username) {
        if (customer == null) {
            throw new RuntimeException("Builder not initialized. Call reset() first.");
        }
        customer.setUsername(username);
    }

    @Override
    public void password(String password) {
        if (customer == null) {
            throw new RuntimeException("Builder not initialized. Call reset() first.");
        }
        customer.setPassword(password);
    }

    @Override
    public void fullName(String fullName) {
        if (customer == null) {
            throw new RuntimeException("Builder not initialized. Call reset() first.");
        }
        customer.setFullName(fullName);
    }

    @Override
    public void phone(String phone) {
        if (customer == null) {
            throw new RuntimeException("Builder not initialized. Call reset() first.");
        }
        customer.setPhone(phone);
    }

    @Override
    public void address(String address) {
        if (customer == null) {
            throw new RuntimeException("Builder not initialized. Call reset() first.");
        }
        customer.setAddress(address);
    }

    @Override
    public void dateOfBirth(Date dateOfBirth) {
        if (customer == null) {
            throw new RuntimeException("Builder not initialized. Call reset() first.");
        }
        // Convert Date to String in format "yyyy-MM-dd"
        String dateStr = new java.text.SimpleDateFormat("yyyy-MM-dd").format(dateOfBirth);
        customer.setDateOfBirth(dateStr);
    }

    public void points(int points) {
        if (customer == null) {
            throw new RuntimeException("Builder not initialized. Call reset() first.");
        }
        customer.setPoints(points);
    }

    public void level(String levelName) {
        if (customer == null) {
            throw new RuntimeException("Builder not initialized. Call reset() first.");
        }
        if (levelName == null) {
            customer.setLevel(new com.bach.patterns.state.BronzeLevel());
            return;
        }
        switch (levelName.toUpperCase()) {
            case "GOLD":
                customer.setLevel(new com.bach.patterns.state.GoldLevel());
                break;
            case "SILVER":
                customer.setLevel(new com.bach.patterns.state.SilverLevel());
                break;
            default:
                customer.setLevel(new com.bach.patterns.state.BronzeLevel());
        }
    }

    public Customer getResult() {
        if (customer == null) {
            throw new RuntimeException("Builder not initialized. Call reset() first.");
        }
        return customer;
    }
}