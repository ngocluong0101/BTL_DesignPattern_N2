package com.clothingstore.patterns.facade;

import com.clothingstore.dao.UserDAO;
import com.clothingstore.dto.LoginResult;
import com.clothingstore.model.Admin;
import com.clothingstore.patterns.sessionsingleton.Session;
import com.clothingstore.service.SecurityService;

public class LoginFacade {
    private UserDAO userDAO;
    private SecurityService securityService;

    public LoginFacade() {
        this.userDAO = new UserDAO();
        this.securityService = new SecurityService();
    }

    public LoginResult authenticate(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            return new LoginResult(false, "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu");
        }

        Admin user = userDAO.findByUsername(username);
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
