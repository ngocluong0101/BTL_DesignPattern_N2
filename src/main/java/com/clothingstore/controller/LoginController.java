package com.clothingstore.controller;

import com.clothingstore.dto.LoginResult;
import com.clothingstore.patterns.facade.LoginFacade;
import com.clothingstore.view.auth.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private LoginView view;
    private LoginFacade loginFacade;

    public LoginController() {
        this.view = new LoginView();
        this.loginFacade = new LoginFacade();

        this.view.addLoginListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        this.view.addRegisterListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegister();
            }
        });
        
        this.view.setVisible(true);
    }

    private void handleLogin() {
        String username = view.getUsername();
        String password = view.getPassword();

        LoginResult result = loginFacade.authenticate(username, password);

        if (result.isSuccess()) {
            view.dispose(); // Đóng màn hình đăng nhập
            // Mở màn hình chính bằng Navigator
//            Navigator.getInstance().showCustomerMainView();
            new CustomerMainController();
        } else {
            view.showError(result.getMessage());
        }
    }

    private void openRegister() {
        view.dispose();
        new RegisterController();
    }
}
