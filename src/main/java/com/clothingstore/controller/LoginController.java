package com.clothingstore.controller;

import com.clothingstore.dto.LoginResult;
import com.clothingstore.component.Navbar;
import com.clothingstore.patterns.facade.LoginFacade;
import com.clothingstore.patterns.sessionsingleton.Session;
import com.clothingstore.util.Navigator;
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
            String role = Session.getInstance().getRole();
            if (role != null && (role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("manager"))) {
                Navigator.navigate(Navbar.NavItem.SUPPLIER, view);
            } else {
                view.dispose();
                new CustomerMainController();
            }
        } else {
            view.showError(result.getMessage());
        }
    }

    private void openRegister() {
        Navigator.navigate(Navbar.NavItem.REGISTER, view);
    }
}
