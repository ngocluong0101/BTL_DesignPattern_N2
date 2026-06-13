package com.clothingstore.view.auth;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Date;
import java.awt.*;

public class RegisterView extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField fullNameField;
    private JTextField phoneField;
    private JTextField addressField;
    private JSpinner dateOfBirthSpinner;
    private JButton registerButton;
    private JButton loginButton;

    public RegisterView() {
        setTitle("Register");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 247, 250));

        // Tiêu đề
        JLabel titleLabel = new JLabel("CREATE ACCOUNT");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setBounds(210, 20, 250, 40);
        panel.add(titleLabel);

        Font labelFont = new Font("SansSerif", Font.PLAIN, 14);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 14);

        int labelX = 120;
        int fieldX = 250;
        int width = 220;
        int height = 35;
        int y = 90;
        int gap = 50;

        // Username
        JLabel usernameLabel = new JLabel("Tên đăng nhập");
        usernameLabel.setFont(labelFont);
        usernameLabel.setBounds(labelX, y, 120, 35);
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setFont(fieldFont);
        usernameField.setBounds(fieldX, y, width, height);
        panel.add(usernameField);

        // Password
        y += gap;
        JLabel passwordLabel = new JLabel("Mật khẩu");
        passwordLabel.setFont(labelFont);
        passwordLabel.setBounds(labelX, y, 120, 35);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(fieldFont);
        passwordField.setBounds(fieldX, y, width, height);
        panel.add(passwordField);

        // Full Name
        y += gap;
        JLabel fullNameLabel = new JLabel("Họ và tên");
        fullNameLabel.setFont(labelFont);
        fullNameLabel.setBounds(labelX, y, 120, 35);
        panel.add(fullNameLabel);

        fullNameField = new JTextField();
        fullNameField.setFont(fieldFont);
        fullNameField.setBounds(fieldX, y, width, height);
        panel.add(fullNameField);

        // Phone
        y += gap;
        JLabel phoneLabel = new JLabel("Số điện thoại");
        phoneLabel.setFont(labelFont);
        phoneLabel.setBounds(labelX, y, 120, 35);
        panel.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setFont(fieldFont);
        phoneField.setBounds(fieldX, y, width, height);
        panel.add(phoneField);

        // Address
        y += gap;
        JLabel addressLabel = new JLabel("Địa chỉ");
        addressLabel.setFont(labelFont);
        addressLabel.setBounds(labelX, y, 120, 35);
        panel.add(addressLabel);

        addressField = new JTextField();
        addressField.setFont(fieldFont);
        addressField.setBounds(fieldX, y, width, height);
        panel.add(addressField);

        // Date of Birth
        y += gap;
        JLabel dateOfBirthLabel = new JLabel("Ngày sinh");
        dateOfBirthLabel.setFont(labelFont);
        dateOfBirthLabel.setBounds(labelX, y, 120, 35);
        panel.add(dateOfBirthLabel);

        dateOfBirthSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor =
                new JSpinner.DateEditor(dateOfBirthSpinner, "dd/MM/yyyy");
        dateOfBirthSpinner.setEditor(dateEditor);
        dateOfBirthSpinner.setFont(fieldFont);
        dateOfBirthSpinner.setBounds(fieldX, y, width, height);
        panel.add(dateOfBirthSpinner);

        // Buttons
        registerButton = new JButton("Đăng ký");
        registerButton.setBounds(190, 390, 120, 40);
        registerButton.setBackground(new Color(46, 204, 113));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(registerButton);

        loginButton = new JButton("Đăng nhập");
        loginButton.setBounds(330, 390, 120, 40);
        loginButton.setBackground(new Color(52, 152, 219));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
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