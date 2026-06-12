package com.clothingstore;


import com.clothingstore.controller.LoginController;
import com.clothingstore.view.order.OrderView;
import com.clothingstore.view.promotion.VoucherManagementView;


import javax.swing.*;

public class Main {
    private static OrderView orderView;
    private static VoucherManagementView voucherView;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginController();
        });
    }
}

