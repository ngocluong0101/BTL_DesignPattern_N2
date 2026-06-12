package com.clothingstore.view.customer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class NotificationView extends JFrame {
    private final JTextArea textArea;

    public NotificationView() {
        setTitle("Thông báo của khách hàng");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lblTitle = new JLabel("Danh sách thông báo", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textArea);

        setLayout(new BorderLayout());
        add(lblTitle, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void displayNotifications(List<String> messages) {
        StringBuilder sb = new StringBuilder();
        for (String msg : messages) {
            sb.append(msg).append("\n\n");
        }
        textArea.setText(sb.toString());
    }
}