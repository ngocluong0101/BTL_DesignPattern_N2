package com.clothingstore.controller;

import com.clothingstore.dao.notification.CustomerEventSubscriptionDAO;
import com.clothingstore.patterns.sessionsingleton.Session;
import com.clothingstore.view.customer.CustomerMainView;

import javax.swing.*;

public class CustomerMainController {
    private final CustomerMainView  view;
    private final CustomerEventSubscriptionDAO  subscriptionDAO;

    public CustomerMainController() {
        subscriptionDAO = new CustomerEventSubscriptionDAO();
        this.view = new CustomerMainView();
        this.view.setVisible(true);
        initController();
    }

    private void initController() {
        view.getBtnViewProfile().addActionListener(e -> openProfileView());
        view.getBtnEditProfile().addActionListener(e -> openEditProfile());
        view.getBtnViewProducts().addActionListener(e -> openProductListView());
        view.getBtnOrderHistory().addActionListener(e -> openOrderHistory());
        view.getBtnSubscribeEvent().addActionListener(e -> openSubscribeEvent());
        view.getBtnViewNotification().addActionListener(e -> openNotificationView());
        view.getBtnUnsubscribeEvent().addActionListener(e -> openUnsubscribeEvent());
        view.getBtnLogout().addActionListener(e -> logout());
    }

    private void openProfileView() {
        JOptionPane.showMessageDialog(view, "Đi đến trang View Profile");
        // new ProfileView().setVisible(true);
        // view.dispose();
    }

    private void openEditProfile() {
        JOptionPane.showMessageDialog(view, "Đi đến trang Edit Profile");
        // new EditProfileView().setVisible(true);
        // view.dispose();
    }

    private void openProductListView() {
        JOptionPane.showMessageDialog(view, "Đi đến danh sách sản phẩm");
        // new ProductListView().setVisible(true);
        // view.dispose();
    }

    private void openOrderHistory() {
        JOptionPane.showMessageDialog(view, "Đi đến lịch sử đơn hàng");
        // new OrderHistoryView().setVisible(true);
        // view.dispose();
    }

    private void openSubscribeEvent() {
        String message = subscriptionDAO.insertSubscription(Session.getInstance().getId());
        JOptionPane.showMessageDialog(view, message);
    }

    private void openUnsubscribeEvent() {
        String message = subscriptionDAO.deleteSubscriptionByCustomerId(Session.getInstance().getId());
        JOptionPane.showMessageDialog(view, message);
    }

    private void openNotificationView() {
        new NotificationController(Session.getInstance().getId());
    }

    private void logout() {
        JOptionPane.showMessageDialog(view, "Đăng xuất");
        view.dispose();
        new LoginController();
    }
}