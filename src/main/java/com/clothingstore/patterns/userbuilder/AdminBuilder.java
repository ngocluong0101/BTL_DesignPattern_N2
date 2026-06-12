package com.clothingstore.patterns.userbuilder;


import com.clothingstore.model.Admin;

import java.util.Date;

public class AdminBuilder implements UserBuilder{

    private Admin admin;


    @Override
    public void reset() {
        admin = new Admin();
    }

    @Override
    public void id(int id) {
        if (admin == null) {
            throw new RuntimeException("Builder not initialized. Call reset() first.");
        }
        admin.setId(id);
    }

    @Override
    public void username(String username) {
        if (admin == null) {
            throw new RuntimeException("Builder not initialized. Call reset() first.");
        }
        admin.setUsername(username);
    }

    @Override
    public void password(String password) {
        if (admin == null) {
            throw new RuntimeException("Builder not initialized. Call reset() first.");
        }
        admin.setPassword(password);
    }

    @Override
    public void fullName(String fullName) {
        if (admin == null) {
            throw new RuntimeException("Builder not initialized. Call reset() first.");
        }
        admin.setFullName(fullName);
    }

    @Override
    public void phone(String phone) {
        if (admin == null) {
            throw new RuntimeException("Builder not initialized. Call reset() first.");
        }
        admin.setPhone(phone);
    }

    @Override
    public void address(String address) {
        throw new RuntimeException("Admin does not have an address field.");
    }

    @Override
    public void dateOfBirth(Date dateOfBirth) {
        throw new RuntimeException("Admin does not have a date of birth field.");
    }

    @Override
    public void points(int points) {
        throw new RuntimeException("Admin does not have points field.");
    }

    @Override
    public void level(String levelName) {
        throw new RuntimeException("Admin does not have level field.");
    }

    public Admin getResult() {
        if (admin == null) {
            throw new RuntimeException("Builder not initialized. Call reset() first.");
        }
        return admin;
    }
}