package com.clothingstore.model;

import com.clothingstore.patterns.state.MemberLevel;
import com.clothingstore.patterns.state.BronzeLevel;
import com.clothingstore.patterns.state.SilverLevel;
import com.clothingstore.patterns.state.GoldLevel;

public class Customer extends Admin {
    private String address;
    private String dateOfBirth;
    private int points;
    private MemberLevel level;

    public Customer() {
        super();
        this.points = 0;
        this.level = new BronzeLevel();
    }

    public Customer(int id, String username, String password, String fullName, String phone, String address, String dateOfBirth) {
        super(id, username, password, fullName, phone);
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.points = 0;
        this.level = new BronzeLevel();
    }

    public Customer(int id, String fullName, String phone) {
        super(id, null, null, fullName, phone);
    }

    public void addPoints(int points) {
        this.points += points;
        updateLevel();
    }

    private void updateLevel() {
        if (points >= 5000) {
            level = new GoldLevel();
        } else if (points >= 1000) {
            level = new SilverLevel();
        } else {
            level = new BronzeLevel();
        }
    }

    // Getters and setters
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public int getPoints() { return points; }
    public void setPoints(int points) {
        this.points = points;
        updateLevel();
    }

    public MemberLevel getLevel() { return level; }
    public void setLevel(MemberLevel level) { this.level = level; }
}