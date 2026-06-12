package com.clothingstore.view.customer;

import javax.swing.*;

public class CustomerMainView extends JFrame {
    private final JButton btnViewProfile;
    private final JButton btnEditProfile;
    private final JButton btnViewProducts;
    private final JButton btnOrderHistory;
    private final JButton btnLogout;
    private final JButton btnSubscribeEvent;
    private final JButton btnUnsubscribeEvent;
    private final JButton btnViewNotificaion;

    public CustomerMainView() {
        setTitle("Customer Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 450);
        setLocationRelativeTo(null);
        setLayout(null); // Use null layout

        JLabel titleLabel = new JLabel("Customer Dashboard", SwingConstants.CENTER);
        titleLabel.setBounds(120, 20, 200, 30);
        add(titleLabel);

        btnViewProfile = new JButton("View Profile");
        btnViewProfile.setBounds(120, 70, 200, 30);
        add(btnViewProfile);

        btnEditProfile = new JButton("Edit Profile");
        btnEditProfile.setBounds(120, 110, 200, 30);
        add(btnEditProfile);

        btnViewProducts = new JButton("View Products");
        btnViewProducts.setBounds(120, 150, 200, 30);
        add(btnViewProducts);

        btnOrderHistory = new JButton("Order History");
        btnOrderHistory.setBounds(120, 190, 200, 30);
        add(btnOrderHistory);

        btnSubscribeEvent = new JButton("Subscribe Event Notification");
        btnSubscribeEvent.setBounds(120, 230, 200, 30);
        add(btnSubscribeEvent);

        btnUnsubscribeEvent = new JButton("Unsubscribe Event Notification");
        btnUnsubscribeEvent.setBounds(120, 270, 200, 30);
        add(btnUnsubscribeEvent);

        btnViewNotificaion = new JButton("View Notification");
        btnViewNotificaion.setBounds(120, 310, 200, 30);
        add(btnViewNotificaion);

        btnLogout = new JButton("Logout");
        btnLogout.setBounds(120, 350, 200, 30);
        add(btnLogout);
    }

    public JButton getBtnViewProfile() {
        return btnViewProfile;
    }

    public JButton getBtnEditProfile() {
        return btnEditProfile;
    }

    public JButton getBtnViewProducts() {
        return btnViewProducts;
    }

    public JButton getBtnOrderHistory() {
        return btnOrderHistory;
    }

    public JButton getBtnLogout() {
        return btnLogout;
    }

    public JButton getBtnSubscribeEvent() {
        return btnSubscribeEvent;
    }

    public JButton getBtnUnsubscribeEvent() {
        return btnUnsubscribeEvent;
    }

    public JButton getBtnViewNotification() {
        return btnViewNotificaion;
    }
}