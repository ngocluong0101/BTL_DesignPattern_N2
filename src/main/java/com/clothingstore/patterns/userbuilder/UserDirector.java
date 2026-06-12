package com.clothingstore.patterns.userbuilder;


import com.clothingstore.model.Admin;
import com.clothingstore.view.auth.RegisterView;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDirector {

    public void createAdminFromResultSet(UserBuilder builder, ResultSet resultSet) throws SQLException {
        builder.reset();
        builder.id(resultSet.getInt("id_admin"));
        builder.username(resultSet.getNString("username"));
        builder.password(resultSet.getNString("password"));
        builder.fullName(resultSet.getNString("full_name"));
        builder.phone(resultSet.getNString("phone"));
    }

    public void createCustomerFromResultSet(UserBuilder builder, ResultSet resultSet) throws SQLException {
        builder.reset();
        builder.id(resultSet.getInt("id_customers"));
        builder.username(resultSet.getNString("username"));
        builder.password(resultSet.getNString("password"));
        builder.fullName(resultSet.getNString("full_name"));
        builder.phone(resultSet.getNString("phone"));
        builder.address(resultSet.getNString("address"));
        builder.dateOfBirth(resultSet.getObject("date_of_birth", java.util.Date.class));
        builder.points(resultSet.getInt("points"));
        builder.level(resultSet.getString("level"));
    }

    public void createCustomerFromRegisterView(UserBuilder builder, RegisterView view) {
        builder.reset();
        builder.username(view.getUsername());
        builder.password(view.getPassword());
        builder.fullName(view.getFullName());
        builder.phone(view.getPhone());
        builder.address(view.getAddress());
        builder.dateOfBirth(view.getDateOfBirth());
    }

}