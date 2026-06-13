package com.clothingstore.view.invoice.invoicepurchase;

import com.clothingstore.component.Navbar;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InvoicePurchaseView extends JFrame {
    private final JTextField txtAmount;             // amount
    private final JTextField txtBuyDate;            // buy_date
    private final JComboBox<String> comboStatus;    // state
    private final JButton btnCreate;
    private final JButton btnExport;
    private Navbar navbar;

    public InvoicePurchaseView(String adminFullname) {
        setTitle("Hóa đơn nhập hàng");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        navbar = new Navbar(this);
        navbar.setVisible(true);
        navbar.setLocation(0, 0);
        add(navbar, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("Hóa Đơn Nhập Hàng");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

//        JLabel lblAdminId = new JLabel("Admin:");
//        txtAdminName = new JTextField();
//        txtAdminName.setText(adminFullname);

        JLabel lblAmount = new JLabel("Số tiền:");
        txtAmount = new JTextField();

        JLabel lblBuyDate = new JLabel("Ngày nhập: (yyyy-MM-dd):");
        txtBuyDate = new JTextField();

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

//        gbc.gridx = 0; gbc.gridy = y;
//        panel.add(lblAdminId, gbc);
//        gbc.gridx = 1;
//        panel.add(txtAdminName, gbc);

        gbc.gridx = 0; gbc.gridy = ++y;
        panel.add(lblAmount, gbc);
        gbc.gridx = 1;
        panel.add(txtAmount, gbc);

        gbc.gridx = 0; gbc.gridy = ++y;
        panel.add(lblBuyDate, gbc);
        gbc.gridx = 1;
        panel.add(txtBuyDate, gbc);

        gbc.gridx = 0; gbc.gridy = ++y;
        panel.add(lblStatus, gbc);
        gbc.gridx = 1;
        panel.add(comboStatus, gbc);

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

    // Getter cho Controller dùng


//    public JTextField getTxtAdminName() {
//        return txtAdminName;
//    }

    public JTextField getTxtAmount() {
        return txtAmount;
    }

    public JTextField getTxtBuyDate() {
        return txtBuyDate;
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

    public void displaySearchResult(String result) {
        JOptionPane.showMessageDialog(this, result, "Kết quả tìm kiếm hóa đơn", JOptionPane.INFORMATION_MESSAGE);
    }

    // Hàm main để chạy test form
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            List<String> admins = List.of("Nguyễn Văn A", "Trần Thị B", "Lê Văn C");
//            new InvoicePurchaseView(admins).setVisible(true);
//        });
//    }
}