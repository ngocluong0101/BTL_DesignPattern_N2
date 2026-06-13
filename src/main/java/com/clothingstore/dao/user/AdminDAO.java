package com.clothingstore.dao.user;


import com.clothingstore.database.ConnectionManager;
import com.clothingstore.model.Admin;
import com.clothingstore.patterns.userbuilder.AdminBuilder;
import com.clothingstore.patterns.userbuilder.UserDirector;

import java.sql.*;

public class AdminDAO {

    public Admin getAdminByUsernameAndPassword(String username, String password){

        String sql = "SELECT * FROM admin WHERE username = ? AND password = ? LIMIT 1;";
        UserDirector director = new UserDirector();
        AdminBuilder builder = new AdminBuilder();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
            conn = ConnectionManager.getConnection();
            statement = conn.prepareStatement(sql);
            statement.setNString(1, username);
            statement.setNString(2, password);
            rs = statement.executeQuery();
            if(rs == null || !rs.next()){
                return null;
            }
            director.createAdminFromResultSet(builder, rs);
        } catch (SQLException e) {
            System.out.println("Error while getting admin by username and password: " + e.getMessage());
        }finally {
            ConnectionManager.closeQuietly(rs);
            ConnectionManager.closeQuietly(statement);
            ConnectionManager.closeQuietly(conn);
        }
        return builder.getResult();
    }

}