package com.clothingstore.view.auth;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Date;

public class RegisterView extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField fullNameField;
    private JTextField phoneField;
    private JTextField addressField;
    private JSpinner dateOfBirthSpinner;
    private JButton registerButton;
    private JButton loginButton;

    public RegisterView(){
        setTitle("Register");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false); // Prevent resizing
        JPanel panel = new JPanel();
        panel.setLayout(null);
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
        JLabel fullNameLabel = new JLabel("Tên đầy đủ:");
        fullNameLabel.setBounds(10, 80, 80, 25);
        panel.add(fullNameLabel);
        fullNameField = new JTextField(20);
        fullNameField.setBounds(100, 80, 165, 25);
        panel.add(fullNameField);
        JLabel phoneLabel = new JLabel("Số điện thoại:");
        phoneLabel.setBounds(10, 110, 80, 25);
        panel.add(phoneLabel);
        phoneField = new JTextField(20);
        phoneField.setBounds(100, 110, 165, 25);
        panel.add(phoneField);
        JLabel addressLabel = new JLabel("Địa chỉ:");
        addressLabel.setBounds(10, 140, 80, 25);
        panel.add(addressLabel);
        addressField = new JTextField(20);
        addressField.setBounds(100, 140, 165, 25);
        panel.add(addressField);
        JLabel dateOfBirthLabel = new JLabel("Ngày sinh:");
        dateOfBirthLabel.setBounds(10, 170, 80, 25);
        panel.add(dateOfBirthLabel);
        dateOfBirthSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateOfBirthSpinner, "dd/MM/yyyy");
        dateOfBirthSpinner.setEditor(dateEditor);
        dateOfBirthSpinner.setBounds(100, 170, 165, 25);
        panel.add(dateOfBirthSpinner);
        registerButton = new JButton("Đăng kí");
        registerButton.setBounds(10, 200, 125, 25); // Set same width
        panel.add(registerButton);
        loginButton = new JButton("Đăng nhập");
        loginButton.setBounds(140, 200, 125, 25); // Set same width
        panel.add(loginButton);
        add(panel);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getFullName() {
        return fullNameField.getText();
    }

    public String getPhone() {
        return phoneField.getText();
    }

    public String getAddress() {
        return addressField.getText();
    }

    public Date getDateOfBirth() {
        return (Date) dateOfBirthSpinner.getValue();
    }

    public void addRegisterListener(java.awt.event.ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void addLoginListener(java.awt.event.ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public boolean isValidInput() {
        String username = getUsername().trim();
        String password = getPassword().trim();
        String fullName = getFullName().trim();
        String phone = getPhone().trim();
        String address = getAddress().trim();

        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty()
                || phone.isEmpty() || address.isEmpty()) {
            showError("Vui lòng điền đầy đủ thông tin!");
            return false;
        }

        if (username.length() < 4 || username.contains(" ")) {
            showError("Tên đăng nhập phải có ít nhất 4 ký tự và không chứa khoảng trắng!");
            return false;
        }

        if (!fullName.matches("^[\\p{L}\\s]+$")) {
            showError("Họ tên chỉ được chứa chữ cái và khoảng trắng!");
            return false;
        }

        if (password.length() < 6 || !password.matches(".*[A-Za-z].*") || !password.matches(".*\\d.*")) {
            showError("Mật khẩu phải có ít nhất 6 ký tự, bao gồm cả chữ và số!");
            return false;
        }

        if (!phone.matches("^0\\d{9}$")) {
            showError("Số điện thoại không hợp lệ! (Phải có 10 chữ số và bắt đầu bằng số 0)");
            return false;
        }

        if (address.length() < 5) {
            showError("Địa chỉ quá ngắn!");
            return false;
        }

        Date today = new Date();
        if (getDateOfBirth().after(today)) {
            showError("Ngày sinh không hợp lệ!");
            return false;
        }

        return true;
    }



}