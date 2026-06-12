package com.clothingstore.view.auth;


import com.clothingstore.component.Navbar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private Navbar navbar;



    public LoginView() {
        setTitle("Login");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false); // Prevent resizing




        JPanel panel = new JPanel();
        panel.setLayout(null);
        // Set panel bounds below the navbar (assuming navbar height is 50)
        panel.setBounds(0, 50, 500, 450);


        JLabel usernameLabel = new JLabel("Tên đăng nhập:");

        usernameLabel.setBounds(10, 20, 80, 25);
        panel.add(usernameLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(100, 20, 165, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Mật khẩu:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 165, 25);
        panel.add(passwordField);


        loginButton = new JButton("Đăng nhập");
        loginButton.setBounds(10, 80, 125, 25); // Set same width
        panel.add(loginButton);

        registerButton = new JButton("Đăng ký");

        registerButton.setBounds(140, 80, 125, 25); // Set same width
        panel.add(registerButton);

        add(panel);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}