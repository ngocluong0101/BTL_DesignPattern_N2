package com.clothingstore.view.invoice.invoicepurchase;

import javax.swing.*;
import java.awt.*;

public class InvoicePurchaseSearchDialog extends JDialog{
    private final JTextField txtSearchId;
    private final JButton btnSearch;

    public InvoicePurchaseSearchDialog(InvoicePurchaseView parent) {
        super(parent, "Tìm kiếm hóa đơn", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Nhập ID hóa đơn:"));
        txtSearchId = new JTextField(10);
        topPanel.add(txtSearchId);
        btnSearch = new JButton("Xuất hóa đơn");
        topPanel.add(btnSearch);
        add(topPanel, BorderLayout.NORTH);
    }

    public JTextField getTxtSearchId() {
        return txtSearchId;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }
}