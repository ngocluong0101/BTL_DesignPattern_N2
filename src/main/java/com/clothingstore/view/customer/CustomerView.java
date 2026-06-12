package com.clothingstore.view.customer;

import javax.swing.*;

public class CustomerView extends JFrame {
    private final JButton btnViewProfile;
    private final JButton btnEditProfile;
    private final JButton btnViewProducts;
    private final JButton btnOrderHistory;
    private final JButton btnLogout;

    public CustomerView() {
        setTitle("Customer Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(null); // Use null layout

        JLabel titleLabel = new JLabel("Customer Dashboard", SwingConstants.CENTER);
        titleLabel.setBounds(100, 20, 200, 30);
        add(titleLabel);

        btnViewProfile = new JButton("View Profile");
        btnViewProfile.setBounds(120, 70, 160, 30);
        add(btnViewProfile);

        btnEditProfile = new JButton("Edit Profile");
        btnEditProfile.setBounds(120, 110, 160, 30);
        add(btnEditProfile);

        btnViewProducts = new JButton("View Products");
        btnViewProducts.setBounds(120, 150, 160, 30);
        add(btnViewProducts);

        btnOrderHistory = new JButton("Order History");
        btnOrderHistory.setBounds(120, 190, 160, 30);
        add(btnOrderHistory);

        btnLogout = new JButton("Logout");
        btnLogout.setBounds(120, 230, 160, 30);
        add(btnLogout);
    }
}