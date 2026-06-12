package com.clothingstore.patterns.userbuilder;


import java.time.LocalDate;
import java.util.Date;

public interface UserBuilder {

    void reset();
    void id(int id);
    void username(String username);
    void password(String password);
    void fullName(String fullName);
    void phone(String phone);
    void address(String address);
    void dateOfBirth(Date dateOfBirth);
    void points(int points);
    void level(String levelName);

}