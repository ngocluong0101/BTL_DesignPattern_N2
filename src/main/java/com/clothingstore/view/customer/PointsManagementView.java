package com.clothingstore.view.customer;

import com.clothingstore.model.Customer;
import com.clothingstore.service.CustomerService;
import com.clothingstore.component.Navbar;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PointsManagementView extends JFrame {
    private JTable customerTable;
    private List<Customer> customers;
    private Navbar navbar;

    public PointsManagementView() {
        customers = CustomerService.getInstance().getAllCustomers();
        setTitle("Quản lý tích điểm khách hàng");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        navbar = new Navbar(this);
        add(navbar, BorderLayout.NORTH);
        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createTitledBorder("Danh sách khách hàng"));

        String[] customerColumns = {"Username", "Full Name", "Points", "Level", "Discount"};
        Object[][] customerData = customers.stream()
                .map(c -> new Object[]{
                        c.getUsername(),
                        c.getFullName(),
                        c.getPoints(),
                        c.getLevel().getLevelName(),
                        c.getLevel().getDiscountRate() * 100 + "%"
                })
                .toArray(Object[][]::new);
        customerTable = new JTable(customerData, customerColumns);
        JScrollPane customerScrollPane = new JScrollPane(customerTable);
        mainPanel.add(customerScrollPane, BorderLayout.CENTER);

        JButton resetPointsButton = new JButton("Đặt lại điểm về 0");
        resetPointsButton.setFont(new Font("Arial", Font.BOLD, 15));
        resetPointsButton.setBackground(new Color(244, 67, 54));
        resetPointsButton.setForeground(Color.WHITE);
        resetPointsButton.setFocusPainted(false);
        resetPointsButton.addActionListener(e -> resetSelectedCustomerPoints());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(resetPointsButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void resetSelectedCustomerPoints() {
        int row = customerTable.getSelectedRow();
        if (row < 0 || row >= customers.size()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để đặt lại điểm.");
            return;
        }
        Customer customer = customers.get(row);
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn đặt lại điểm cho khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            CustomerService.getInstance().resetPoints(customer.getId());
            customers = CustomerService.getInstance().getAllCustomers();
            refreshCustomerTable();
            JOptionPane.showMessageDialog(this, "Đã đặt lại điểm về 0 cho khách hàng!");
        }
    }

    private void refreshCustomerTable() {
        String[] customerColumns = {"Username", "Full Name", "Points", "Level", "Discount"};
        Object[][] customerData = customers.stream()
                .map(c -> new Object[]{
                        c.getUsername(),
                        c.getFullName(),
                        c.getPoints(),
                        c.getLevel().getLevelName(),
                        c.getLevel().getDiscountRate() * 100 + "%"
                })
                .toArray(Object[][]::new);
        customerTable.setModel(new javax.swing.table.DefaultTableModel(customerData, customerColumns));
    }
}