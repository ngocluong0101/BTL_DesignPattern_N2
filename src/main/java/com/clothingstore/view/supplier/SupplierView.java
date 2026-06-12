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
    private JTextField stateField;
    private JButton editButton;
    private JButton deleteButton;
    private JTable supplierTable;
    private DefaultTableModel tableModel;
    private Navbar navbar;
    private JButton activateButton;
    private JButton addSupplierButton;

    public SupplierView() {
        setTitle("Quản lý nhà cung cấp");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        navbar = new Navbar(this);
        add(navbar, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin nhà cung cấp"));

        idField = new JTextField();
        nameField = new JTextField();
        phoneField = new JTextField();
        addressField = new JTextField();
        emailField = new JTextField();
        stateField = new JTextField();

        idField.setEditable(false);
        stateField.setEditable(false);

        formPanel.add(createFormRow("ID:", idField));
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(createFormRow("Tên nhà cung cấp:", nameField));
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(createFormRow("Số điện thoại:", phoneField));
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(createFormRow("Địa chỉ:", addressField));
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(createFormRow("Email:", emailField));
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(createFormRow("Trạng thái:", stateField));
        formPanel.add(Box.createVerticalStrut(12));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 2));
        editButton = new JButton("Sửa");
        deleteButton = new JButton("Xoá");
        activateButton = new JButton("Kích hoạt");
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(activateButton);
        formPanel.add(buttonPanel);
        // Thêm nút Thêm nhà cung cấp ở dưới cùng
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addSupplierButton = new JButton("Thêm nhà cung cấp");
        addButtonPanel.add(addSupplierButton);
        formPanel.add(addButtonPanel);

        JPanel formWrapper = new JPanel(new BorderLayout());
        formWrapper.add(formPanel, BorderLayout.CENTER);
        formWrapper.setBorder(BorderFactory.createEmptyBorder(16, 32, 8, 32));

        String[] columns = {"ID", "Tên", "SĐT", "Địa chỉ", "Email", "Trạng thái"};
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

        // Group form and table in a center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(0, 10));
        centerPanel.add(formWrapper, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        supplierTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = supplierTable.getSelectedRow();
                if (row != -1) {
                    idField.setText(tableModel.getValueAt(row, 0).toString());
                    nameField.setText(tableModel.getValueAt(row, 1).toString());
                    phoneField.setText(tableModel.getValueAt(row, 2).toString());
                    addressField.setText(tableModel.getValueAt(row, 3).toString());
                    emailField.setText(tableModel.getValueAt(row, 4).toString());
                    stateField.setText(tableModel.getValueAt(row, 5).toString());
                    // Update activateButton label
                    if (stateField.getText().equals("inactive")) {
                        activateButton.setText("Kích hoạt");
                    } else {
                        activateButton.setText("Hủy kích hoạt");
                    }
                }
            }
        });

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                int width = getWidth();
                int padding = Math.max(24, width / 14);
                formWrapper.setBorder(BorderFactory.createEmptyBorder(16, padding, 8, padding));
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
                    s.getId(), s.getName(), s.getPhone(), s.getAddress(), s.getEmail(), s.getState()
            });
        }
    }

    public Supplier getSelectedSupplierFromForm() {

        Supplier supplier = new Supplier();
        supplier.setId(Integer.parseInt(idField.getText()));
        supplier.setName(nameField.getText());
        supplier.setPhone(phoneField.getText());
        supplier.setAddress(addressField.getText());
        supplier.setEmail(emailField.getText());
        if(stateField.getText().equals("inactive")) {
            supplier.changeState(new InActiveSupplierState(supplier));
            activateButton.setText("Kích hoạt");
        } else {
            supplier.changeState(new ActiveSupplierState(supplier));
            activateButton.setText("Hủy kích hoạt");
        }
        return supplier;

    }

    public void setEditButtonListener(ActionListener listener) {
        editButton.addActionListener(listener);
    }

    public void setDeleteButtonListener(ActionListener listener) {

        deleteButton.addActionListener(listener);
    }
    public void setActivateButtonListener(ActionListener listener) {
        activateButton.addActionListener(listener);
    }
    // Thêm phương thức để set listener cho nút Thêm nhà cung cấp
    public void setAddSupplierButtonListener(ActionListener listener) {
        addSupplierButton.addActionListener(listener);
    }

    public JButton getActivateButton() {
        return activateButton;
    }

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