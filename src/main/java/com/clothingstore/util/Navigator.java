package com.clothingstore.util;

import com.clothingstore.component.Navbar;
import com.clothingstore.controller.*;

import com.clothingstore.controller.OrderController;

import com.clothingstore.dao.AdminDAO;
import com.clothingstore.dao.OrderDAO;
import com.clothingstore.patterns.sessionsingleton.Session;
import com.clothingstore.view.customer.PointsManagementView;
import com.clothingstore.view.order.ProductView;
import com.clothingstore.view.promotion.AdminDiscountView;


import javax.swing.*;
import java.util.List;
import java.util.Map;

public class Navigator {

    public static void navigate(Navbar.NavItem navItem, JFrame currentFrame) {
        switch (navItem) {
            case LOGIN:
                currentFrame.dispose();
                new LoginController();
                break;
            case REGISTER:
                currentFrame.dispose();
                new RegisterController();
                break;
            case ORDER:
                currentFrame.dispose();
                new OrderController();
                break;
            case VOUCHER:
                currentFrame.dispose();
                new VoucherManagementController();
                break;
            case POINTS:
                currentFrame.dispose();
                new PointsManagementView();
                break;
            case INVOICESALE:
                currentFrame.dispose();
                OrderDAO orderDAO = new OrderDAO();
                List<Integer> orderIds = orderDAO.getAllOrderIds();
                new InvoiceSalesController(orderIds);
                break;
            case INVOICEPURCHASE:
                currentFrame.dispose();
                AdminDAO adminDAO = new AdminDAO();
                String adminFullname = adminDAO.getAdminNameById(Session.getInstance().getId());
                new InvoicePurchaseController(adminFullname);
                break;
            case EVENT:
                currentFrame.dispose();
                new EventController();
                break;
            case EXIT:
                currentFrame.dispose();
                System.exit(0);
                break;
            case SUPPLIER:
                currentFrame.dispose();
                new SupplierController();
                break;

            case CREATE_SUPPLIER:
                currentFrame.dispose();
                new CreateSupplierController();
                break;

            case PRODUCT:
                currentFrame.dispose();
                new ProductView().setVisible(true);
                break;

            case DISCOUNT:
                currentFrame.dispose();
                new AdminDiscountView().setVisible(true);
                break;
            default:
                throw new IllegalArgumentException("Unknown navigation item: " + navItem);
        }
    }

}