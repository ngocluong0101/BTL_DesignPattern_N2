package com.clothingstore.patterns.facade;

import com.clothingstore.dao.UserDAO;
import com.clothingstore.dao.user.CustomerDAO;
import com.clothingstore.dto.LoginResult;
import com.clothingstore.model.Admin;
import com.clothingstore.model.Customer;
import com.clothingstore.patterns.sessionsingleton.Session;
import com.clothingstore.service.SecurityService;

public class LoginFacade {
    private UserDAO userDAO;
    private CustomerDAO customerDAO;
    private SecurityService securityService;

    public LoginFacade() {
        this.userDAO = new UserDAO();
        this.customerDAO = new CustomerDAO();
        this.securityService = new SecurityService();
    }

    public LoginResult authenticate(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            return new LoginResult(false, "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu");
        }

        try {
            Customer customer = customerDAO.getCustomerByUsernameAndPassword(username, password);
            if (customer != null) {
                Session.getInstance().login(customer.getId(), customer.getUsername(), "customer");
                return new LoginResult(true, "Đăng nhập thành công");
            }
        } catch (Exception e) {
            return new LoginResult(false, e.getMessage());
        }

        Admin user;
        try {
            user = userDAO.findByUsername(username);
        } catch (IllegalStateException e) {
            return new LoginResult(false, e.getMessage());
        }

        if (user == null) {
            return new LoginResult(false, "Sai tên đăng nhập hoặc mật khẩu");
        }

        if (!securityService.verifyPassword(password, user.getPassword())) {
            return new LoginResult(false, "Sai tên đăng nhập hoặc mật khẩu");
        }

        Session.getInstance().login(user.getId(), user.getUsername(), user.getRole());
        return new LoginResult(true, "Đăng nhập thành công");
    }
}
