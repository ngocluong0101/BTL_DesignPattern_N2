package com.clothingstore.view.customer;

import javax.swing.*;
import java.awt.*;

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
        setTitle("Customer Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 247, 250));

        JLabel titleLabel = new JLabel("CUSTOMER DASHBOARD", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setBounds(100, 25, 350, 40);
        panel.add(titleLabel);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 14);

        int btnX = 125;
        int btnY = 90;
        int btnWidth = 280;
        int btnHeight = 40;
        int gap = 15;

        btnViewProfile = new JButton("View Profile");
        btnViewProfile.setBounds(btnX, btnY, btnWidth, btnHeight);
        btnViewProfile.setFont(buttonFont);
        panel.add(btnViewProfile);

        btnEditProfile = new JButton("Edit Profile");
        btnEditProfile.setBounds(btnX, btnY += btnHeight + gap, btnWidth, btnHeight);
        btnEditProfile.setFont(buttonFont);
        panel.add(btnEditProfile);

        btnViewProducts = new JButton("View Products");
        btnViewProducts.setBounds(btnX, btnY += btnHeight + gap, btnWidth, btnHeight);
        btnViewProducts.setFont(buttonFont);
        panel.add(btnViewProducts);

        btnOrderHistory = new JButton("Order History");
        btnOrderHistory.setBounds(btnX, btnY += btnHeight + gap, btnWidth, btnHeight);
        btnOrderHistory.setFont(buttonFont);
        panel.add(btnOrderHistory);

        btnSubscribeEvent = new JButton("Subscribe Event Notification");
        btnSubscribeEvent.setBounds(btnX, btnY += btnHeight + gap, btnWidth, btnHeight);
        btnSubscribeEvent.setFont(buttonFont);
        panel.add(btnSubscribeEvent);

        btnUnsubscribeEvent = new JButton("Unsubscribe Event Notification");
        btnUnsubscribeEvent.setBounds(btnX, btnY += btnHeight + gap, btnWidth, btnHeight);
        btnUnsubscribeEvent.setFont(buttonFont);
        panel.add(btnUnsubscribeEvent);

        btnViewNotificaion = new JButton("View Notification");
        btnViewNotificaion.setBounds(btnX, btnY += btnHeight + gap, btnWidth, btnHeight);
        btnViewNotificaion.setFont(buttonFont);
        panel.add(btnViewNotificaion);

        btnLogout = new JButton("Logout");
        btnLogout.setBounds(btnX, btnY += btnHeight + gap + 10, btnWidth, btnHeight);

        btnLogout.setBackground(new Color(231, 76, 60));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setFont(buttonFont);

        panel.add(btnLogout);

        add(panel);
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