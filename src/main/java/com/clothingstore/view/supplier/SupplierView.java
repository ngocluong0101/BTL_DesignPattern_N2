package com.clothingstore.view.supplier;

import com.clothingstore.component.Navbar;
import com.clothingstore.model.Supplier;
import com.clothingstore.patterns.supplierstate.ActiveSupplierState;
import com.clothingstore.patterns.supplierstate.InActiveSupplierState;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class SupplierView extends JFrame {
    private JTextField idField;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField emailField;
    private JTextField taxCodeField;
    private JComboBox<String> categoryComboBox;
    private JTextField contactPersonField;
    private JTextField stateField;
    
    private JButton editButton;
    private JButton deleteButton;
    private JButton activateButton;
    private JButton addSupplierButton;
    private JButton clearFormButton;

    private JTextField searchKeywordField;
    private JComboBox<String> searchCategoryComboBox;
    private JComboBox<String> searchStateComboBox;
    private JButton searchButton;
    private JButton refreshButton;

    private JTable supplierTable;
    private DefaultTableModel tableModel;
    private Navbar navbar;

    public SupplierView() {
        setTitle("Quản lý nhà cung cấp");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        navbar = new Navbar(this);
        add(navbar, BorderLayout.NORTH);

        // FORM PANEL
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin nhà cung cấp"));

        idField = new JTextField();
        nameField = new JTextField();
        phoneField = new JTextField();
        addressField = new JTextField();
        emailField = new JTextField();
        taxCodeField = new JTextField();
        contactPersonField = new JTextField();
        stateField = new JTextField();
        
        categoryComboBox = new JComboBox<>(new String[]{"Tất cả", "Vải", "Phụ kiện", "Dịch vụ", "Khác"});

        idField.setEditable(false);
        stateField.setEditable(false);

        formPanel.add(createFormRow("Mã NCC (ID):", idField));
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(createFormRow("Tên nhà cung cấp:", nameField));
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(createFormRow("Số điện thoại:", phoneField));
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(createFormRow("Địa chỉ:", addressField));
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(createFormRow("Email:", emailField));
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(createFormRow("Mã số thuế:", taxCodeField));
        formPanel.add(Box.createVerticalStrut(5));
        
        JPanel catRow = new JPanel(new BorderLayout(10, 0));
        JLabel catLabel = new JLabel("Ngành hàng:");
        catLabel.setPreferredSize(new Dimension(130, 24));
        catRow.add(catLabel, BorderLayout.WEST);
        catRow.add(categoryComboBox, BorderLayout.CENTER);
        catRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        formPanel.add(catRow);
        
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(createFormRow("Người đại diện:", contactPersonField));
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(createFormRow("Trạng thái:", stateField));
        formPanel.add(Box.createVerticalStrut(12));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 2));
        addSupplierButton = new JButton("Thêm mới");
        editButton = new JButton("Cập nhật");
        deleteButton = new JButton("Xóa");
        activateButton = new JButton("Kích hoạt/Ngừng");
        clearFormButton = new JButton("Xóa form");
        
        buttonPanel.add(addSupplierButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(activateButton);
        buttonPanel.add(clearFormButton);
        formPanel.add(buttonPanel);

        JPanel formWrapper = new JPanel(new BorderLayout());
        formWrapper.add(formPanel, BorderLayout.CENTER);
        formWrapper.setBorder(BorderFactory.createEmptyBorder(16, 16, 8, 16));
        formWrapper.setPreferredSize(new Dimension(400, 0));

        // SEARCH PANEL
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm & Lọc"));
        
        searchPanel.add(new JLabel("Từ khóa:"));
        searchKeywordField = new JTextField(15);
        searchPanel.add(searchKeywordField);
        
        searchPanel.add(new JLabel("Ngành hàng:"));
        searchCategoryComboBox = new JComboBox<>(new String[]{"Tất cả", "Vải", "Phụ kiện", "Dịch vụ", "Khác"});
        searchPanel.add(searchCategoryComboBox);
        
        searchPanel.add(new JLabel("Trạng thái:"));
        searchStateComboBox = new JComboBox<>(new String[]{"Tất cả", "Đang hợp tác", "Ngừng hợp tác"});
        searchPanel.add(searchStateComboBox);
        
        searchButton = new JButton("Tìm kiếm");
        refreshButton = new JButton("Làm mới");
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);

        // TABLE
        String[] columns = {"ID", "Tên NCC", "SĐT", "Email", "Mã số thuế", "Ngành hàng", "Người đại diện", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        supplierTable = new JTable(tableModel);
        supplierTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        supplierTable.setRowHeight(26);

        JScrollPane scrollPane = new JScrollPane(supplierTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách nhà cung cấp"));

        JPanel tableAreaPanel = new JPanel(new BorderLayout(0, 10));
        tableAreaPanel.add(searchPanel, BorderLayout.NORTH);
        tableAreaPanel.add(scrollPane, BorderLayout.CENTER);
        tableAreaPanel.setBorder(BorderFactory.createEmptyBorder(16, 0, 8, 16));

        // Layout center
        add(formWrapper, BorderLayout.WEST);
        add(tableAreaPanel, BorderLayout.CENTER);

        // Table Selection Listener
        supplierTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = supplierTable.getSelectedRow();
                if (row != -1) {
                    idField.setText(tableModel.getValueAt(row, 0) != null ? tableModel.getValueAt(row, 0).toString() : "");
                    nameField.setText(tableModel.getValueAt(row, 1) != null ? tableModel.getValueAt(row, 1).toString() : "");
                    phoneField.setText(tableModel.getValueAt(row, 2) != null ? tableModel.getValueAt(row, 2).toString() : "");
                    emailField.setText(tableModel.getValueAt(row, 3) != null ? tableModel.getValueAt(row, 3).toString() : "");
                    taxCodeField.setText(tableModel.getValueAt(row, 4) != null ? tableModel.getValueAt(row, 4).toString() : "");
                    
                    String cat = tableModel.getValueAt(row, 5) != null ? tableModel.getValueAt(row, 5).toString() : "Khác";
                    categoryComboBox.setSelectedItem(cat);
                    
                    contactPersonField.setText(tableModel.getValueAt(row, 6) != null ? tableModel.getValueAt(row, 6).toString() : "");
                    stateField.setText(tableModel.getValueAt(row, 7) != null ? tableModel.getValueAt(row, 7).toString() : "");
                    
                    if (stateField.getText().equalsIgnoreCase("inactive")) {
                        activateButton.setText("Kích hoạt");
                    } else {
                        activateButton.setText("Ngừng hợp tác");
                    }
                }
            }
        });
    }

    private JPanel createFormRow(String labelText, JTextField textField) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(130, 24));
        textField.setPreferredSize(new Dimension(0, 24));
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
        row.add(label, BorderLayout.WEST);
        row.add(textField, BorderLayout.CENTER);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        return row;
    }

    public void setSupplierData(List<Supplier> supplierList) {
        tableModel.setRowCount(0);
        for (Supplier s : supplierList) {
            tableModel.addRow(new Object[]{
                    s.getId(), 
                    s.getName(), 
                    s.getPhone(), 
                    s.getEmail(), 
                    s.getTaxCode(),
                    s.getCategory(),
                    s.getContactPerson(),
                    s.getState()
            });
        }
    }

    public Supplier getSelectedSupplierFromForm() {
        Supplier supplier = new Supplier();
        try {
            if (!idField.getText().trim().isEmpty()) {
                supplier.setId(Integer.parseInt(idField.getText().trim()));
            }
        } catch (Exception ignored) {}

        supplier.setName(nameField.getText());
        supplier.setPhone(phoneField.getText());
        supplier.setAddress(addressField.getText());
        supplier.setEmail(emailField.getText());
        supplier.setTaxCode(taxCodeField.getText());
        supplier.setCategory(categoryComboBox.getSelectedItem().toString());
        supplier.setContactPerson(contactPersonField.getText());
        
        if(stateField.getText().equalsIgnoreCase("inactive")) {
            supplier.changeState(new InActiveSupplierState(supplier));
        } else {
            supplier.changeState(new ActiveSupplierState(supplier));
        }
        return supplier;
    }

    public void clearForm() {
        idField.setText("");
        nameField.setText("");
        phoneField.setText("");
        addressField.setText("");
        emailField.setText("");
        taxCodeField.setText("");
        contactPersonField.setText("");
        categoryComboBox.setSelectedIndex(0);
        stateField.setText("");
        supplierTable.clearSelection();
    }

    // SEARCH FIELDS
    public String getSearchKeyword() {
        return searchKeywordField.getText().trim();
    }

    public String getSearchCategory() {
        return searchCategoryComboBox.getSelectedItem().toString();
    }

    public String getSearchState() {
        return searchStateComboBox.getSelectedItem().toString();
    }

    public void clearSearchFields() {
        searchKeywordField.setText("");
        searchCategoryComboBox.setSelectedIndex(0);
        searchStateComboBox.setSelectedIndex(0);
    }

    // LISTENERS
    public void setSearchButtonListener(ActionListener listener) { searchButton.addActionListener(listener); }
    public void setRefreshButtonListener(ActionListener listener) { refreshButton.addActionListener(listener); }
    public void setAddSupplierButtonListener(ActionListener listener) { addSupplierButton.addActionListener(listener); }
    public void setEditButtonListener(ActionListener listener) { editButton.addActionListener(listener); }
    public void setDeleteButtonListener(ActionListener listener) { deleteButton.addActionListener(listener); }
    public void setActivateButtonListener(ActionListener listener) { activateButton.addActionListener(listener); }
    public void setClearFormButtonListener(ActionListener listener) { clearFormButton.addActionListener(listener); }

    public void refreshTable(List<Supplier> suppliers) {
        tableModel.setRowCount(0);
        setSupplierData(suppliers);
        idField.setText("");
        nameField.setText("");
        phoneField.setText("");
        addressField.setText("");
        emailField.setText("");
        stateField.setText("");
    }


    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showConfirmDeleteDialog(String message, ActionListener confirmListener) {
        String[] options = {"Xác nhận", "Huỷ"};
        int response = JOptionPane.showOptionDialog(
                this,
                message,
                "Xác nhận xoá",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[0]
        );
        if (response == 0) {
            confirmListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
        }
    }
}