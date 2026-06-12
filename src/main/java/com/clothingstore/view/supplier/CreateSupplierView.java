package com.clothingstore.view.supplier;


import com.clothingstore.component.Navbar;
import com.clothingstore.model.Supplier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CreateSupplierView extends JFrame {
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField emailField;
    private JButton addButton;
    private JButton backButton;
    private Navbar navbar;

    public CreateSupplierView() {
        setTitle("Tạo nhà cung cấp");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(500, 380)); // Lower height
        setPreferredSize(new Dimension(600, 380)); // Lower height
        setLocationRelativeTo(null); // Center on screen
        setLayout(new BorderLayout(10, 10));

        // Tạo panel phía trên chứa cả navbar và tiêu đề
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        navbar = new Navbar(this);
        topPanel.add(navbar, BorderLayout.NORTH);
        JLabel titleLabel = new JLabel("Thêm nhà cung cấp", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        topPanel.add(titlePanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.PAGE_START);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        addButton = new JButton("Thêm");
        backButton = new JButton("Trở về");
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        formPanel.add(createFormRow("Tên nhà cung cấp:", nameField = new JTextField()));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(createFormRow("Số điện thoại:", phoneField = new JTextField()));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(createFormRow("Địa chỉ:", addressField = new JTextField()));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(createFormRow("Email:", emailField = new JTextField()));
        formPanel.add(Box.createVerticalStrut(10));

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH); // Place below formPanel

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                int width = getWidth();
                int padding = Math.max(20, width / 15);
                ((JPanel)getContentPane().getComponent(2)).setBorder(BorderFactory.createEmptyBorder(20, padding, 20, padding));
            }
        });
        pack();
    }

    public String getNameValue() {
        return nameField.getText().trim();
    }

    public String getPhoneValue() {
        return phoneField.getText().trim();
    }

    public Supplier getSupplier() {
        String name = getNameValue();
        String phone = getPhoneValue();
        String address = getAddressValue();
        String email = getEmailValue();

        if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            throw new RuntimeException("Tên, số điện thoại và địa chỉ không được để trống");
        }

        Supplier supplier = new Supplier();
        supplier.setName(name);
        supplier.setPhone(phone);
        supplier.setAddress(address);
        supplier.setEmail(email);

        return supplier;
    }

    public String getAddressValue() {
        return addressField.getText().trim();
    }

    public String getEmailValue() {
        return emailField.getText().trim();
    }

    public void addBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private JPanel wrapTextField(JTextField field) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(field, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(0, 22)); // Force lower height
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));
        return panel;
    }

    private JPanel createFormRow(String labelText, JTextField textField) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(120, 22));
        textField.setFont(new Font("Arial", Font.PLAIN, 11));
        textField.setMargin(new Insets(1, 4, 1, 4));
        textField.setPreferredSize(new Dimension(0, 22));
        row.add(label, BorderLayout.WEST);
        row.add(textField, BorderLayout.CENTER);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 26));
        return row;
    }
}