package com.clothingstore.dao.user;

import com.clothingstore.dao.ConnectionManager;
import com.clothingstore.model.Customer;
import com.clothingstore.patterns.userbuilder.CustomerBuilder;
import com.clothingstore.patterns.userbuilder.UserDirector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CustomerDAO {

    public Customer getCustomerByUsernameAndPassword(String username, String password) {

        String sql = "SELECT * FROM customers WHERE username = ? AND password = ? LIMIT 1;";
        UserDirector director = new UserDirector();
        CustomerBuilder builder = new CustomerBuilder();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
            conn = ConnectionManager.getInstance().getConnection();
            statement = conn.prepareStatement(sql);
            statement.setNString(1, username);
            statement.setNString(2, password);
            rs = statement.executeQuery();
            if(rs == null || !rs.next()){
                return null;
            }
            director.createCustomerFromResultSet(builder, rs);
        } catch (SQLException e) {
            System.out.println("Error while getting admin by username and password: " + e.getMessage());
        }finally {
            ConnectionManager.closeQuietly(rs);
            ConnectionManager.closeQuietly(statement);
            ConnectionManager.closeQuietly(conn);
        }
        return builder.getResult();

    }

    public Customer getCustomerByUsername(String username) {

        String sql = "SELECT * FROM customers WHERE username = ? LIMIT 1;";
        UserDirector director = new UserDirector();
        CustomerBuilder builder = new CustomerBuilder();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
            conn = ConnectionManager.getInstance().getConnection();
            statement = conn.prepareStatement(sql);
            statement.setNString(1, username);
            rs = statement.executeQuery();
            if(rs == null || !rs.next()){
                return null;
            }
            director.createCustomerFromResultSet(builder, rs);
        } catch (SQLException e) {
            System.out.println("Error while getting customer by username: " + e.getMessage());
        }finally {
            ConnectionManager.closeQuietly(rs);
            ConnectionManager.closeQuietly(statement);
            ConnectionManager.closeQuietly(conn);
        }
        return builder.getResult();

    }

    public void createCustomer(Customer customer){

        String sql =  "INSERT INTO customers (username, password, full_name, phone, address, date_of_birth) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
            conn = ConnectionManager.getInstance().getConnection();
            statement = conn.prepareStatement(sql);
            statement.setNString(1, customer.getUsername());
            statement.setNString(2, customer.getPassword());
            statement.setNString(3, customer.getFullName());
            statement.setNString(4, customer.getPhone());
            statement.setNString(5, customer.getAddress());
            statement.setObject(6, customer.getDateOfBirth());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating customer failed, no rows affected.");
            }
        } catch (SQLException e) {
            System.out.println("Error while creating customer: " + e.getMessage());
        } finally {
            ConnectionManager.closeQuietly(rs);
            ConnectionManager.closeQuietly(statement);
            ConnectionManager.closeQuietly(conn);
        }

    }


    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getInstance().getConnection();
            statement = conn.prepareStatement(sql);
            rs = statement.executeQuery();
            UserDirector director = new UserDirector();
            while (rs.next()) {
                CustomerBuilder builder = new CustomerBuilder();
                director.createCustomerFromResultSet(builder, rs);
                customers.add(builder.getResult());
            }
        } catch (SQLException e) {
            System.out.println("Error while getting all customers: " + e.getMessage());
        } finally {
            ConnectionManager.closeQuietly(rs);
            ConnectionManager.closeQuietly(statement);
            ConnectionManager.closeQuietly(conn);
        }
        return customers;
    }

}