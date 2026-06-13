package com.clothingstore.view.order;


import com.clothingstore.component.Navbar;
import com.clothingstore.controller.ProductController;
import com.clothingstore.model.product.BasicProductFactory;
import com.clothingstore.model.product.PremiumProductFactory;
import com.clothingstore.model.product.Product1;
import com.clothingstore.model.product.ProductFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductView extends JFrame {
    public JTable tblProducts;
    public JButton btnAdd, btnEdit, btnDelete;
    public JTextField txtName, txtDesc, txtPrice;
    public JComboBox<String> cboSupplier, cboAdmin;
    public JRadioButton rdoBasic, rdoPremium;
    public ButtonGroup stateGroup;
    public DefaultTableModel model;
    public ProductController controller = new ProductController();

    public ProductView() {
        setTitle("Quản Lý Sản Phẩm");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- THÊM NAVBAR VÀO TRÊN CÙNG ---
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        // Navbar nằm phía trên
        Navbar navbar = new Navbar(this);
        topPanel.add(navbar, BorderLayout.NORTH);

        // Tiêu đề nằm dưới navbar
        JLabel lblTitle = new JLabel("QUẢN LÝ SẢN PHẨM", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        topPanel.add(lblTitle, BorderLayout.SOUTH);

        // Thêm cả navbar và tiêu đề vào phần NORTH
        add(topPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Tên sản phẩm:"), gbc);
        gbc.gridx = 1;
        txtName = new JTextField(20);
        formPanel.add(txtName, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Mô tả:"), gbc);
        gbc.gridx = 1;
        txtDesc = new JTextField(20);
        formPanel.add(txtDesc, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Giá:"), gbc);
        gbc.gridx = 1;
        txtPrice = new JTextField(20);
        formPanel.add(txtPrice, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Nhà cung cấp:"), gbc);
        gbc.gridx = 1;
        cboSupplier = new JComboBox<>(new String[]{
                "1 - Công ty A", "2 - Nhà cung cấp B", "3 - Supplier C"
        });
        formPanel.add(cboSupplier, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Admin:"), gbc);
        gbc.gridx = 1;
        cboAdmin = new JComboBox<>(new String[]{
                "2 - Admin Nguyễn", "3 - Admin Trần", "4 - Admin Lê"
        });
        formPanel.add(cboAdmin, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Trạng thái:"), gbc);
        gbc.gridx = 1;
        JPanel statePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        rdoBasic = new JRadioButton("Basic", true);
        rdoPremium = new JRadioButton("Premium");
        stateGroup = new ButtonGroup();
        stateGroup.add(rdoBasic);
        stateGroup.add(rdoPremium);
        statePanel.add(rdoBasic);
        statePanel.add(rdoPremium);
        formPanel.add(statePanel, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID_SP", "ID_NCC", "Tên SP", "Mô tả", "Giá", "Trạng thái", "ID_Admin"}
        );
        tblProducts = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblProducts);

        mainPanel.add(formPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(scrollPane);
        add(mainPanel, BorderLayout.CENTER);

        loadData();
        addListeners();
    }

    private void loadData() {
        model.setRowCount(0);
        List<Product1> list = controller.getAll();
        for (Product1 p : list) {
            model.addRow(new Object[]{
                    p.getId_products(),
                    p.getSupplierId(),
                    p.getName(),
                    p.getDescription(),
                    p.getPrice(),
                    p.getState(),
                    p.getAdminId()
            });
        }
    }

    private void addListeners() {
        btnAdd.addActionListener(e -> {
            Product1 p = buildProductFromForm();
            if (p != null) {
                controller.add(p);
                loadData();
            }
            clearForm();
        });

        btnEdit.addActionListener(e -> {
            int row = tblProducts.getSelectedRow();
            if (row >= 0) {
                Product1 p = buildProductFromForm();
                if (p != null) {
                    int id = (int) model.getValueAt(row, 0);
                    p.setId_products(id);
                    controller.update(p);
                    loadData();
                }
            }
            clearForm();
        });

        btnDelete.addActionListener(e -> {
            int row = tblProducts.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                controller.delete(id);
                loadData();
            }
            clearForm();
        });

        tblProducts.getSelectionModel().addListSelectionListener(e -> {
            int row = tblProducts.getSelectedRow();
            if (row >= 0) {
                cboSupplier.setSelectedItem(tblProducts.getValueAt(row, 1).toString() + " - ...");
                txtName.setText(tblProducts.getValueAt(row, 2).toString());
                txtDesc.setText(tblProducts.getValueAt(row, 3).toString());
                txtPrice.setText(tblProducts.getValueAt(row, 4).toString());
                String state = tblProducts.getValueAt(row, 5).toString();
                if (state.equalsIgnoreCase("Premium")) {
                    rdoPremium.setSelected(true);
                } else {
                    rdoBasic.setSelected(true);
                }
                cboAdmin.setSelectedItem(tblProducts.getValueAt(row, 6).toString() + " - ...");
            }
        });
    }

    private Product1 buildProductFromForm() {
        try {
            String name = txtName.getText().trim();
            String desc = txtDesc.getText().trim();
            String priceStr = txtPrice.getText().trim();

            // Kiểm tra dữ liệu nhập vào
            if (name.isEmpty() || desc.isEmpty() || priceStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập đầy đủ các trường: Tên, Mô tả và Giá!",
                        "Thiếu thông tin",
                        JOptionPane.WARNING_MESSAGE);
                return null;
            }

            int supplierId = Integer.parseInt(cboSupplier.getSelectedItem().toString().split(" - ")[0]);
            double price = Double.parseDouble(priceStr);
            String state = rdoPremium.isSelected() ? "Premium" : "Basic";
            int adminId = Integer.parseInt(cboAdmin.getSelectedItem().toString().split(" - ")[0]);

            ProductFactory factory = state.equals("Premium")
                    ? new PremiumProductFactory()
                    : new BasicProductFactory();

            return factory.createProduct(supplierId, name, desc, price, state, adminId);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Giá sản phẩm phải là một số hợp lệ!",
                    "Lỗi định dạng",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Dữ liệu nhập không hợp lệ!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }


    private void clearForm() {
        txtName.setText("");
        txtDesc.setText("");
        txtPrice.setText("");
        cboSupplier.setSelectedIndex(0);
        cboAdmin.setSelectedIndex(0);
        rdoBasic.setSelected(true);
        tblProducts.clearSelection(); // bỏ chọn dòng trong bảng
    }

}