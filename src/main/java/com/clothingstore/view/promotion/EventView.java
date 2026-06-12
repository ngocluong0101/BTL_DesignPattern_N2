package com.clothingstore.view.promotion;


import com.clothingstore.component.Navbar;

import javax.swing.*;
import java.awt.*;

public class EventView extends JFrame {
    private final JTextField txtName;
    private final JTextField txtStartDate;
    private final JTextField txtEndDate;
    private final JTextArea txtContent;
    private final JTextField txtAdminId;
    private final JButton btnAddEvent;
    private Navbar navbar;

    public EventView() {
        setTitle("Thêm Sự Kiện Mới");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        navbar = new Navbar(this);
        navbar.setVisible(true);
        navbar.setLocation(0, 0);
        add(navbar, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblName = new JLabel("Tên sự kiện:");
        JLabel lblStartDate = new JLabel("Ngày bắt đầu (yyyy-MM-dd):");
        JLabel lblEndDate = new JLabel("Ngày kết thúc (yyyy-MM-dd):");
        JLabel lblContent = new JLabel("Nội dung:");
//        JLabel lblAdminId = new JLabel("ID Admin:");

        txtName = new JTextField();
        txtStartDate = new JTextField();
        txtEndDate = new JTextField();
        txtContent = new JTextArea(5, 20);
        txtContent.setLineWrap(true);
        txtAdminId = new JTextField();
        btnAddEvent = new JButton("Thêm sự kiện");

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y; panel.add(lblName, gbc);
        gbc.gridx = 1; panel.add(txtName, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; panel.add(lblStartDate, gbc);
        gbc.gridx = 1; panel.add(txtStartDate, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; panel.add(lblEndDate, gbc);
        gbc.gridx = 1; panel.add(txtEndDate, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; panel.add(lblContent, gbc);
        gbc.gridx = 1; panel.add(new JScrollPane(txtContent), gbc);

//        gbc.gridx = 0; gbc.gridy = ++y; panel.add(lblAdminId, gbc);
//        gbc.gridx = 1; panel.add(txtAdminId, gbc);

        gbc.gridx = 1; gbc.gridy = ++y;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(btnAddEvent, gbc);

        add(panel, BorderLayout.CENTER);
    }

    // Getters
    public JTextField getTxtName() { return txtName; }
    public JTextField getTxtStartDate() { return txtStartDate; }
    public JTextField getTxtEndDate() { return txtEndDate; }
    public JTextArea getTxtContent() { return txtContent; }
    public JTextField getTxtAdminId() { return txtAdminId; }
    public JButton getBtnAddEvent() { return btnAddEvent; }
}