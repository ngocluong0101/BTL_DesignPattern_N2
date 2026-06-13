package com.clothingstore.controller;

import com.clothingstore.dao.user.CustomerDAO;
import com.clothingstore.model.Customer;
import com.clothingstore.patterns.userbuilder.CustomerBuilder;
import com.clothingstore.patterns.userbuilder.UserDirector;
import com.clothingstore.util.Navigator;
import com.clothingstore.component.Navbar;
import com.clothingstore.view.auth.RegisterView;

public class RegisterController {

    private final RegisterView view;
    private final CustomerDAO customerDAO;
    private final UserDirector userDirector;

    public RegisterController() {
        this.view = new RegisterView();
        this.customerDAO = new CustomerDAO();
        this.userDirector = new UserDirector();

        this.view.addRegisterListener(e -> handleRegister());
        this.view.addLoginListener(e -> backToLogin());

        this.view.setVisible(true);
    }

    private void handleRegister() {
        if (!view.isValidInput()) {
            return;
        }

        String username = view.getUsername().trim();
        if (customerDAO.getCustomerByUsername(username) != null) {
            view.showError("Tên đăng nhập đã tồn tại!");
            return;
        }

        CustomerBuilder builder = new CustomerBuilder();
        userDirector.createCustomerFromRegisterView(builder, view);
        Customer customer = builder.getResult();

        try {
            customerDAO.createCustomer(customer);
            view.showMessage("Đăng ký thành công!");
            Navigator.navigate(Navbar.NavItem.LOGIN, view);
        } catch (Exception e) {
            view.showError("Không thể đăng ký: " + e.getMessage());
        }
    }

    private void backToLogin() {
        Navigator.navigate(Navbar.NavItem.LOGIN, view);
    }
}
