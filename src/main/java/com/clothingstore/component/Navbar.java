package com.clothingstore.component;

import com.clothingstore.util.Navigator;

import javax.swing.*;
import java.awt.*;

public class Navbar extends JPanel {

    public Navbar(JFrame currentFrame) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(Color.WHITE);
        int width = (currentFrame != null) ? currentFrame.getWidth() : 900;
        setPreferredSize(new Dimension(width, 40));
        setVisible(true);

        add(new DivComponent(NavItem.SUPPLIER.getTitle(), () -> Navigator.navigate(NavItem.SUPPLIER, currentFrame)));
        add(new DivComponent(NavItem.CREATE_SUPPLIER.getTitle(), () -> Navigator.navigate(NavItem.CREATE_SUPPLIER, currentFrame)));
//        add(new DivComponent(NavItem.ORDER.getTitle(), () -> Navigator.navigate(NavItem.ORDER, currentFrame)));
//        add(new DivComponent(NavItem.VOUCHER.getTitle(), () -> Navigator.navigate(NavItem.VOUCHER, currentFrame)));
//        add(new DivComponent(NavItem.POINTS.getTitle(), () -> Navigator.navigate(NavItem.POINTS, currentFrame)));
//        add(new DivComponent(NavItem.PRODUCT.getTitle(), () -> Navigator.navigate(NavItem.PRODUCT, currentFrame)));
//        add(new DivComponent(NavItem.DISCOUNT.getTitle(), () -> Navigator.navigate(NavItem.DISCOUNT, currentFrame)));
        add(new DivComponent(NavItem.EXIT.getTitle(), () -> Navigator.navigate(NavItem.EXIT, currentFrame)));
//        add(new DivComponent(NavItem.EVENT.getTitle(), () -> Navigator.navigate(NavItem.EVENT, currentFrame)));
//        JButton invoiceButton = new JButton("Hóa Đơn");
//
//        JPopupMenu invoiceMenu = new JPopupMenu();
//        JMenuItem createInvoice = new JMenuItem("Hoá Đơn Nhập Hàng");
//        JMenuItem listInvoice = new JMenuItem("Hoá Đơn Bán Hàng");

//        createInvoice.addActionListener(e -> Navigator.navigate( NavItem.INVOICEPURCHASE,currentFrame));
//        listInvoice.addActionListener(e -> Navigator.navigate(NavItem.INVOICESALE,currentFrame));
//
//        invoiceMenu.add(createInvoice);
//        invoiceMenu.add(listInvoice);
//
//        invoiceButton.addActionListener(e -> invoiceMenu.show(invoiceButton, 0, invoiceButton.getHeight()));
//
//        add(invoiceButton);
    }

    public enum NavItem{
        LOGIN("Đăng nhập"),
        REGISTER("Đăng ký"),
        ORDER("Đặt hàng"),
        VOUCHER("Quản lý voucher"),
        POINTS("Tích điểm"),
        EXIT("Thoát"),
        SUPPLIER("Nhà cung cấp"),
        INVOICESALE(""),
        INVOICEPURCHASE(""),
        EVENT("Sự kện"),
        CREATE_SUPPLIER("Tạo nhà cung cấp"),
        PRODUCT("Sản phẩm"),
        DISCOUNT("Khuyến mãi"),
        ;
        private final String title;

        NavItem(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}