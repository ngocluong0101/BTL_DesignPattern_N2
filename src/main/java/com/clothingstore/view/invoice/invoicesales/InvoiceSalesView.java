package com.clothingstore.view.invoice.invoicesales;


import com.clothingstore.component.Navbar;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InvoiceSalesView extends JFrame {
    private final JComboBox<Integer> comboOrderId;
    private final JTextField txtQuantity;
    private final JTextField txtBookingDate;
    private final JComboBox<String> comboStatus;
    private final JButton btnCreate;
    private final JButton btnExport;
    private Navbar navbar;

    public InvoiceSalesView(List<Integer> orderIds) {
        setTitle("Quản lý Hóa Đơn Bán");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        navbar = new Navbar(this);
        navbar.setVisible(true);
        navbar.setLocation(0, 0);
        add(navbar, BorderLayout.NORTH);

        // Panel chứa form
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("Hóa Đơn Bán Hàng");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblOrderId = new JLabel("Mã đặt hàng:");
        comboOrderId = new JComboBox<>(orderIds.toArray(new Integer[0]));

        JLabel lblQuantity = new JLabel("Số lượng:");
        txtQuantity = new JTextField();

        JLabel lblBookingDate = new JLabel("Ngày bán: (yyyy-MM-dd):");
        txtBookingDate = new JTextField();

        JLabel lblStatus = new JLabel("Trạng thái:");
        comboStatus = new JComboBox<>(new String[]{"Chưa Thanh Toán", "Đã Thanh Toán"});

        btnCreate = new JButton("Tạo Hóa Đơn");
        btnExport = new JButton("Xuất Hóa Đơn");

        int y = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // 👈 chiếm 2 cột
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0); // cách trên dưới

        panel.add(lblTitle, gbc);
        gbc.gridwidth = 1; // reset lại

        gbc.gridx = 0; gbc.gridy = y++;
        panel.add(lblOrderId, gbc);
        gbc.gridx = 1;
        panel.add(comboOrderId, gbc);

        gbc.gridx = 0; gbc.gridy = ++y;
        panel.add(lblQuantity, gbc);
        gbc.gridx = 1;
        panel.add(txtQuantity, gbc);

        gbc.gridx = 0; gbc.gridy = ++y;
        panel.add(lblBookingDate, gbc);
        gbc.gridx = 1;
        panel.add(txtBookingDate, gbc);

        gbc.gridx = 0; gbc.gridy = ++y;
        panel.add(lblStatus, gbc);
        gbc.gridx = 1;
        panel.add(comboStatus, gbc);

//        gbc.gridx = 1; gbc.gridy = ++y;
//        gbc.anchor = GridBagConstraints.EAST;
//        panel.add(btnCreate, gbc);
//
//        gbc.gridx = 0; gbc.gridy = ++y;
//        gbc.anchor = GridBagConstraints.WEST;
//        panel.add(btnExport, gbc);

        gbc.gridy = ++y;

        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 10, 10); // thêm khoảng cách bên trái
        panel.add(btnExport, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 10, 10, 20); // thêm khoảng cách bên phải
        panel.add(btnCreate, gbc);


        add(panel);
    }

    // ✅ Getters nếu muốn xử lý bên ngoài
    public JComboBox<Integer> getComboOrderId() {
        return comboOrderId;
    }

    public JTextField getTxtQuantity() {
        return txtQuantity;
    }

    public JTextField getTxtBookingDate() {
        return txtBookingDate;
    }

    public JComboBox<String> getComboStatus() {
        return comboStatus;
    }

    public JButton getBtnCreate() {
        return btnCreate;
    }

    public JButton getBtnExport() {
        return btnExport;
    }

    // ✅ Hàm chạy thử
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            List<Integer> demoOrderIds = List.of(1001, 1002, 1003);
//            new InvoiceSalesView(demoOrderIds).setVisible(true);
//        });
//    }
}