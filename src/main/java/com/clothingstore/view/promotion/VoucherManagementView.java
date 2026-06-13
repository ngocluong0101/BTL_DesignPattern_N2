package com.clothingstore.view.promotion;


import com.clothingstore.model.Customer;
import com.clothingstore.model.IVoucher;
import com.clothingstore.patterns.factory.VoucherFactory;
import com.clothingstore.patterns.factory.PercentageVoucherFactory;
import com.clothingstore.patterns.factory.FixedAmountVoucherFactory;
import com.clothingstore.service.OrderService;
import com.clothingstore.service.VoucherService;
import com.clothingstore.service.CustomerService;
import com.clothingstore.component.Navbar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VoucherManagementView extends JFrame {
    private JTable voucherTable;
    private JTextField codeField;
    private JTextField nameField;
    private JTextField startValueField;
    private JTextField endValueField;
    private JComboBox<String> discountTypeCombo;
    private JTextField discountValueField;
    private JTable customerTable;
    private OrderService orderService;
    private VoucherService voucherService;
    private List<IVoucher> vouchers;
    private List<Customer> customers;
    private Navbar navbar;
    private JButton editButton, deleteButton;

    public VoucherManagementView() {
        orderService = OrderService.getInstance();
        voucherService = VoucherService.getInstance();
        vouchers = new ArrayList<>();
        customers = new ArrayList<>();
        setTitle("Quản lý voucher");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        navbar = new Navbar(this);
        add(navbar, BorderLayout.NORTH);
        initializeData();
        initializeUI();
    }

    private void initializeData() {
        // Load vouchers from database
        vouchers = voucherService.getAllVouchers();
        // Load customers from database
        customers = CustomerService.getInstance().getAllCustomers();
    }

    private void initializeUI() {
        // Tiêu đề lớn phía trên
        JLabel titleLabel = new JLabel("Quản lý voucher", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Main split pane
        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerLocation(350);
        splitPane.setResizeWeight(0.0);
        splitPane.setBorder(null);

        // Form panel (bên trái)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Thông tin voucher"),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        formPanel.setBackground(new Color(245, 250, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Code
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel codeLabel = new JLabel("Mã voucher:");
        codeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(codeLabel, gbc);
        gbc.gridx = 1;
        codeField = new JTextField(15);
        codeField.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(codeField, gbc);

        // Name
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel nameLabel = new JLabel("Tên voucher:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        nameField = new JTextField(15);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(nameField, gbc);

        // Start Value
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel startLabel = new JLabel("Giá trị bắt đầu:");
        startLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(startLabel, gbc);
        gbc.gridx = 1;
        startValueField = new JTextField(15);
        startValueField.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(startValueField, gbc);

        // End Value
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel endLabel = new JLabel("Giá trị kết thúc:");
        endLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(endLabel, gbc);
        gbc.gridx = 1;
        endValueField = new JTextField(15);
        endValueField.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(endValueField, gbc);

        // Discount Type
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel typeLabel = new JLabel("Loại giảm giá:");
        typeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(typeLabel, gbc);
        gbc.gridx = 1;
        String[] types = {"Phần trăm", "Số tiền cố định"};
        discountTypeCombo = new JComboBox<>(types);
        discountTypeCombo.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(discountTypeCombo, gbc);

        // Discount Value
        gbc.gridx = 0; gbc.gridy = 5;
        JLabel valueLabel = new JLabel("Giá trị giảm:");
        valueLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(valueLabel, gbc);
        gbc.gridx = 1;
        discountValueField = new JTextField(15);
        discountValueField.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(discountValueField, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);
        JButton createButton = new JButton("Tạo mới");
        createButton.setBackground(new Color(76, 175, 80));
        createButton.setForeground(Color.WHITE);
        createButton.setFont(new Font("Arial", Font.BOLD, 15));
        createButton.setFocusPainted(false);
        createButton.setIcon(UIManager.getIcon("FileChooser.newFolderIcon"));
        createButton.addActionListener(e -> createVoucher());
        buttonPanel.add(createButton);

        editButton = new JButton("Sửa");
        editButton.setBackground(new Color(255, 193, 7));
        editButton.setForeground(Color.BLACK);
        editButton.setFont(new Font("Arial", Font.BOLD, 15));
        editButton.setFocusPainted(false);
        editButton.setIcon(UIManager.getIcon("FileChooser.detailsViewIcon"));
        editButton.addActionListener(e -> editVoucher());
        buttonPanel.add(editButton);

        deleteButton = new JButton("Xóa");
        deleteButton.setBackground(new Color(244, 67, 54));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 15));
        deleteButton.setFocusPainted(false);
        deleteButton.setIcon(UIManager.getIcon("FileChooser.homeFolderIcon"));
        deleteButton.addActionListener(e -> deleteVoucher());
        buttonPanel.add(deleteButton);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        // Bảng voucher (bên phải)
        String[] columnNames = {"Code", "Name", "Start Value", "End Value", "Discount"};
        Object[][] data = vouchers.stream()
                .map(v -> new Object[]{
                        v.getCode(),
                        v.getName(),
                        v.getStartValue(),
                        v.getEndValue(),
                        v.getDiscountDescription()
                })
                .toArray(Object[][]::new);
        voucherTable = new JTable(data, columnNames);
        voucherTable.setFont(new Font("Arial", Font.PLAIN, 15));
        voucherTable.setRowHeight(28);
        voucherTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        voucherTable.setSelectionBackground(new Color(232, 234, 246));
        JScrollPane tableScrollPane = new JScrollPane(voucherTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách voucher"));

        // Đưa panel vào splitPane
        splitPane.setLeftComponent(formPanel);
        splitPane.setRightComponent(tableScrollPane);
        add(splitPane, BorderLayout.CENTER);

        // Khi chọn một dòng trên bảng voucher, điền thông tin lên form
        voucherTable.getSelectionModel().addListSelectionListener(e -> fillVoucherFormFromTable());
    }

    private void createVoucher() {
        try {
            String code = codeField.getText().trim();
            String name = nameField.getText().trim();
            double startValue = Double.parseDouble(startValueField.getText().trim());
            double endValue = Double.parseDouble(endValueField.getText().trim());
            String discountType = (String) discountTypeCombo.getSelectedItem();
            double discountValue = Double.parseDouble(discountValueField.getText().trim());

            VoucherFactory factory;
            if ("Phần trăm".equals(discountType)) {
                factory = new PercentageVoucherFactory();
            } else {
                factory = new FixedAmountVoucherFactory();
            }

            IVoucher voucher = factory.createVoucher(
                    vouchers.size() + 1, code, name, startValue, endValue, discountValue);

            // Save voucher to database
            voucherService.saveVoucher(voucher);

            // Refresh the voucher list
            vouchers = voucherService.getAllVouchers();
            refreshVoucherTable();
            clearVoucherForm();

            JOptionPane.showMessageDialog(this, "Voucher created successfully!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for values!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error creating voucher: " + e.getMessage());
        }
    }

    private void refreshVoucherTable() {
        String[] columnNames = {"Code", "Name", "Start Value", "End Value", "Discount"};
        Object[][] data = vouchers.stream()
                .map(v -> new Object[]{
                        v.getCode(),
                        v.getName(),
                        v.getStartValue(),
                        v.getEndValue(),
                        v.getDiscountDescription()
                })
                .toArray(Object[][]::new);
        voucherTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    private void clearVoucherForm() {
        codeField.setText("");
        nameField.setText("");
        startValueField.setText("");
        endValueField.setText("");
        discountValueField.setText("");
    }

    private void fillVoucherFormFromTable() {
        int row = voucherTable.getSelectedRow();
        if (row >= 0 && row < vouchers.size()) {
            IVoucher v = vouchers.get(row);
            codeField.setText(v.getCode());
            nameField.setText(v.getName());
            startValueField.setText(String.valueOf(v.getStartValue()));
            endValueField.setText(String.valueOf(v.getEndValue()));
            discountValueField.setText(String.valueOf(v.getDiscountDescription().contains("%") ? ((com.clothingstore.model.PercentageVoucher)v).getPercentage() : ((com.clothingstore.model.FixedAmountVoucher)v).getAmount()));
            if (v.getDiscountDescription().contains("%")) {
                discountTypeCombo.setSelectedItem("Phần trăm");
            } else {
                discountTypeCombo.setSelectedItem("Số tiền cố định");
            }
        }
    }

    private void editVoucher() {
        int row = voucherTable.getSelectedRow();
        if (row < 0 || row >= vouchers.size()) {
            JOptionPane.showMessageDialog(this, "Please select a voucher to edit.");
            return;
        }
        try {
            IVoucher oldVoucher = vouchers.get(row);
            String code = codeField.getText().trim();
            String name = nameField.getText().trim();
            double startValue = Double.parseDouble(startValueField.getText().trim());
            double endValue = Double.parseDouble(endValueField.getText().trim());
            String discountType = (String) discountTypeCombo.getSelectedItem();
            double discountValue = Double.parseDouble(discountValueField.getText().trim());

            VoucherFactory factory;
            if ("Phần trăm".equals(discountType)) {
                factory = new PercentageVoucherFactory();
            } else {
                factory = new FixedAmountVoucherFactory();
            }

            IVoucher newVoucher = factory.createVoucher(
                    oldVoucher.getId(), code, name, startValue, endValue, discountValue);

            voucherService.updateVoucher(newVoucher);
            vouchers = voucherService.getAllVouchers();
            refreshVoucherTable();
            JOptionPane.showMessageDialog(this, "Voucher updated successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating voucher: " + e.getMessage());
        }
    }

    private void deleteVoucher() {
        int row = voucherTable.getSelectedRow();
        if (row < 0 || row >= vouchers.size()) {
            JOptionPane.showMessageDialog(this, "Please select a voucher to delete.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this voucher?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            IVoucher voucher = vouchers.get(row);
            voucherService.deactivateVoucher(voucher.getId());
            vouchers = voucherService.getAllVouchers();
            refreshVoucherTable();
            clearVoucherForm();
            JOptionPane.showMessageDialog(this, "Voucher deleted successfully!");
        }
    }
}