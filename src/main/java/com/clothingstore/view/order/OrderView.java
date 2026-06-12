package com.clothingstore.view.order;


import com.clothingstore.model.Product;
import com.clothingstore.model.IVoucher;
import com.clothingstore.patterns.sessionsingleton.Session;
import com.clothingstore.service.CartService;
import com.clothingstore.service.OrderService;
import com.clothingstore.service.CustomerService;
import com.clothingstore.component.Navbar;
import com.clothingstore.model.Order;
import com.clothingstore.patterns.strategy.DiscountContext;
import com.clothingstore.patterns.strategy.FixedAmountDiscountStrategy;
import com.clothingstore.patterns.strategy.PercentageDiscountStrategy;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class OrderView extends JFrame {
    private final CartService cartService;
    private final OrderService orderService;
    private final CustomerService customerService;
    private final DiscountContext discountContext;
    private final JTable cartTable;
    private final JComboBox<String> voucherComboBox;
    private final JTextField totalAmountField;
    private final JTextField finalAmountField;
    private final JComboBox<String> paymentMethodCombo;
    private final JTextArea noteArea;
    private final JLabel memberLevelLabel;
    private final JLabel discountLabel;
    private final JLabel memberLevelDiscountLabel;
    private int customerId;
    private double totalAmount;
    private double discountAmount;
    private String currentLevel = "BRONZE";
    private JLabel orderStatusLabel;
    private JButton payButton, shipButton, completeButton, cancelButton;
    private JButton orderButton;
    private Order currentOrder;

    //Thêm interface lồng bên trong View
    public interface OrderListener {
        void onOrder(String paymentMethod, String note, int cartId, int voucherIndex, double finalAmount);
    }
    private OrderListener orderListener;
    public void setOrderListener(OrderListener listener) {
        this.orderListener = listener;
    }

    public OrderView() {
        cartService = CartService.getInstance();
        orderService = OrderService.getInstance();
        customerService = CustomerService.getInstance();
        discountContext = new DiscountContext();

        setTitle("Đặt hàng");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 250));
        setResizable(false);

        // Add Navbar
        add(new Navbar(this), BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPanel.setBackground(new Color(245, 247, 250));

        // Cart table
        String[] columnNames = {"ID", "Tên sản phẩm", "Mô tả", "Giá", "Trạng thái"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        cartTable = new JTable(model);
        cartTable.setRowHeight(28);
        cartTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        cartTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách sản phẩm trong giỏ hàng"));
        contentPanel.add(scrollPane, BorderLayout.NORTH);

        // Main info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(255, 255, 255));
        infoPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Member and voucher row
        JPanel memberVoucherPanel = new JPanel(new GridLayout(1, 6, 20, 0));
        memberVoucherPanel.setOpaque(false);
        memberVoucherPanel.add(new JLabel("Cấp bậc thành viên:"));
        memberLevelLabel = new JLabel();
        memberLevelLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        memberVoucherPanel.add(memberLevelLabel);
        memberVoucherPanel.add(new JLabel("Giảm giá thành viên:"));
        memberLevelDiscountLabel = new JLabel("0 VND");
        memberLevelDiscountLabel.setForeground(new Color(0, 128, 128));
        memberVoucherPanel.add(memberLevelDiscountLabel);
        memberVoucherPanel.add(new JLabel("Voucher:"));
        voucherComboBox = new JComboBox<>();
        voucherComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        voucherComboBox.addActionListener(e -> updateFinalAmount());
        memberVoucherPanel.add(voucherComboBox);
        infoPanel.add(memberVoucherPanel);
        infoPanel.add(Box.createVerticalStrut(10));

        // Discount and total row
        JPanel discountTotalPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        discountTotalPanel.setOpaque(false);
        discountTotalPanel.add(new JLabel("Giảm giá:"));
        discountLabel = new JLabel("0 VND");
        discountLabel.setForeground(new Color(0, 128, 0));
        discountTotalPanel.add(discountLabel);
        discountTotalPanel.add(new JLabel("Tổng tiền:"));
        totalAmountField = new JTextField();
        totalAmountField.setEditable(false);
        totalAmountField.setHorizontalAlignment(JTextField.RIGHT);
        discountTotalPanel.add(totalAmountField);
        infoPanel.add(discountTotalPanel);
        infoPanel.add(Box.createVerticalStrut(10));

        // Final amount row
        JPanel finalPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        finalPanel.setOpaque(false);
        finalPanel.add(new JLabel("Thành tiền:"));
        finalAmountField = new JTextField();
        finalAmountField.setEditable(false);
        finalAmountField.setFont(new Font("Segoe UI", Font.BOLD, 14));
        finalAmountField.setForeground(new Color(0, 0, 128));
        finalAmountField.setHorizontalAlignment(JTextField.RIGHT);
        finalPanel.add(finalAmountField);
        finalPanel.add(new JLabel("Phương thức thanh toán:"));
        String[] paymentMethods = {"Tiền mặt", "Chuyển khoản", "Thẻ tín dụng"};
        paymentMethodCombo = new JComboBox<>(paymentMethods);
        paymentMethodCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        finalPanel.add(paymentMethodCombo);
        infoPanel.add(finalPanel);
        infoPanel.add(Box.createVerticalStrut(10));

        // Note row
        JPanel notePanel = new JPanel(new BorderLayout());
        notePanel.setOpaque(false);
        notePanel.add(new JLabel("Ghi chú:"), BorderLayout.WEST);
        noteArea = new JTextArea(2, 30);
        noteArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        JScrollPane noteScrollPane = new JScrollPane(noteArea);
        notePanel.add(noteScrollPane, BorderLayout.CENTER);
        infoPanel.add(notePanel);
        infoPanel.add(Box.createVerticalStrut(10));

        // Button row
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        orderButton = new JButton("Đặt hàng");
        orderButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        orderButton.setBackground(new Color(0, 120, 215));
        orderButton.setForeground(Color.WHITE);
        orderButton.setFocusPainted(false);
        orderButton.setPreferredSize(new Dimension(160, 36));
        orderButton.addActionListener(e -> {
            if (orderListener != null && cartTable.getRowCount() > 0) {
                int cartId = (int) cartTable.getValueAt(0, 0);
                String paymentMethod = (String) paymentMethodCombo.getSelectedItem();
                String note = noteArea.getText();
                int voucherIndex = voucherComboBox.getSelectedIndex();
                double finalAmount = Double.parseDouble(finalAmountField.getText().replace(" VND", "").replace(",", "").trim());
                orderListener.onOrder(paymentMethod, note, cartId, voucherIndex, finalAmount);
                // Lấy order mới nhất của customer
                currentOrder = orderService.getLatestOrderForCustomer(customerId);
                updateOrderStatusUI();
                // Disable các mục chọn voucher, phương thức thanh toán và nút đặt hàng
                voucherComboBox.setEnabled(false);
                paymentMethodCombo.setEnabled(false);
                orderButton.setEnabled(false);
            }
        });
        buttonPanel.add(orderButton);

        // Thêm các nút thao tác trạng thái
        payButton = new JButton("Thanh toán");
        payButton.addActionListener(e -> handlePayOrder());
        buttonPanel.add(payButton);
        // Ẩn nút Giao hàng
        // shipButton = new JButton("Giao hàng");
        // shipButton.addActionListener(e -> handleShipOrder());
        // buttonPanel.add(shipButton);
        completeButton = new JButton("Đã nhận được hàng");
        completeButton.addActionListener(e -> handleCompleteOrder());
        buttonPanel.add(completeButton);
        cancelButton = new JButton("Huỷ đơn");
        cancelButton.addActionListener(e -> handleCancelOrder());
        buttonPanel.add(cancelButton);
        // Ban đầu disable các nút trạng thái
        payButton.setEnabled(false);
        // shipButton.setEnabled(false);
        completeButton.setEnabled(false);
        cancelButton.setEnabled(false);

        infoPanel.add(buttonPanel);

        // Thêm label hiển thị trạng thái đơn hàng
        orderStatusLabel = new JLabel("Trạng thái: PENDING");
        orderStatusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        infoPanel.add(orderStatusLabel);

        contentPanel.add(infoPanel, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);

        //setid customer
//        int customerId = Session.getInstance().getId();
        setCustomerId(1);
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
        loadCartData();
        loadVouchers();
        loadMemberInfo();
        updateFinalAmount();
    }

    private void loadCartData() {
        DefaultTableModel model = (DefaultTableModel) cartTable.getModel();
        model.setRowCount(0);

        List<Product> products = cartService.getCartProducts(customerId);
        totalAmount = 0;

        for (Product product : products) {
            model.addRow(new Object[]{
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getState()
            });
            totalAmount += product.getPrice();
        }

        totalAmountField.setText(String.format("%,.2f VND", totalAmount));
    }

    private void loadVouchers() {
        voucherComboBox.removeAllItems();
        voucherComboBox.addItem("Không sử dụng voucher");
        List<IVoucher> vouchers = orderService.getAvailableVouchers();
        for (IVoucher voucher : vouchers) {
            voucherComboBox.addItem(voucher.getName() + " (" + voucher.getCode() + ")");
        }
        updateFinalAmount();
    }

    private void loadMemberInfo() {
        currentLevel = customerService.getMemberLevel(customerId);
        memberLevelLabel.setText(currentLevel);
    }

    private void updateFinalAmount() {
        // Member level discount
        double levelDiscountPercent = customerService.getLevelDiscountPercent(currentLevel);
        double levelDiscountAmount = totalAmount * levelDiscountPercent;
        memberLevelDiscountLabel.setText(String.format("%,.2f VND", levelDiscountAmount));

        // Voucher discount (must be calculated on the amount after member discount)
        int selectedIndex = voucherComboBox.getSelectedIndex();
        double amountAfterLevelDiscount = totalAmount - levelDiscountAmount;
        double voucherDiscount = 0;

        if (selectedIndex > 0) {
            List<IVoucher> vouchers = orderService.getAvailableVouchers();
            if (selectedIndex - 1 < vouchers.size()) {
                IVoucher selectedVoucher = vouchers.get(selectedIndex - 1);
                // Sử dụng DiscountContext để set strategy dựa trên loại voucher
                if (selectedVoucher instanceof com.clothingstore.model.FixedAmountVoucher) {
                    discountContext.setStrategy(new FixedAmountDiscountStrategy(
                            ((com.clothingstore.model.FixedAmountVoucher) selectedVoucher).getAmount()));
                } else if (selectedVoucher instanceof com.clothingstore.model.PercentageVoucher) {
                    discountContext.setStrategy(new PercentageDiscountStrategy(
                            ((com.clothingstore.model.PercentageVoucher) selectedVoucher).getPercentage()));
                }
                voucherDiscount = discountContext.calculateDiscount(amountAfterLevelDiscount);
            }
        } else {
            // Không có voucher được chọn
            discountContext.setStrategy(null);
        }

        discountAmount = voucherDiscount;
        discountLabel.setText(String.format("%,.2f VND", discountAmount));

        double finalAmount = amountAfterLevelDiscount - discountAmount;
        finalAmountField.setText(String.format("%,.2f VND", finalAmount));
    }

    // Thêm hàm reloadData để Controller gọi lại khi cần cập nhật giao diện
    public void reloadData() {
        loadCartData();
        loadVouchers();
        loadMemberInfo();
        updateFinalAmount();
    }

    public int getCustomerId() {
        return customerId;
    }

    // Thêm các hàm thao tác trạng thái
    private void handlePayOrder() {
        if (currentOrder != null) {
            orderService.payOrder(currentOrder);
            currentOrder = orderService.getLatestOrderForCustomer(customerId);
            updateOrderStatusUI();
            loadCartData();
            JOptionPane.showMessageDialog(this, "Đã thanh toán đơn hàng!");
        }
    }
    // private void handleShipOrder() {
    //     if (currentOrder != null) {
    //         orderService.shipOrder(currentOrder);
    //         currentOrder = orderService.getLatestOrderForCustomer(customerId);
    //         updateOrderStatusUI();
    //         JOptionPane.showMessageDialog(this, "Đã giao hàng!");
    //     }
    // }
    private void handleCompleteOrder() {
        if (currentOrder != null) {
            orderService.completeOrder(currentOrder);
            currentOrder = orderService.getLatestOrderForCustomer(customerId);
            updateOrderStatusUI();
            setCustomerId(customerId);
            JOptionPane.showMessageDialog(this, "Bạn đã nhận được hàng!");
        }
    }
    private void handleCancelOrder() {
        if (currentOrder != null) {
            orderService.cancelOrder(currentOrder);
            currentOrder = orderService.getLatestOrderForCustomer(customerId);
            updateOrderStatusUI();
            // Enable lại các mục chọn voucher, phương thức thanh toán và nút đặt hàng
            voucherComboBox.setEnabled(true);
            paymentMethodCombo.setEnabled(true);
            orderButton.setEnabled(true);
            JOptionPane.showMessageDialog(this, "Đơn hàng đã huỷ!");
        }
    }
    private void updateOrderStatusUI() {
        if (currentOrder != null) {
            String status = currentOrder.getStatus();
            if ("COMPLETED".equalsIgnoreCase(status)) {
                orderStatusLabel.setText("Trạng thái: Đã nhận được hàng");
            } else {
                orderStatusLabel.setText("Trạng thái: " + status);
            }
            payButton.setEnabled(currentOrder.getState() instanceof com.clothingstore.patterns.state.PendingOrderState);
            completeButton.setEnabled(currentOrder.getState() instanceof com.clothingstore.patterns.state.PaidOrderState);
            cancelButton.setEnabled(
                    currentOrder.getState() instanceof com.clothingstore.patterns.state.PendingOrderState ||
                            currentOrder.getState() instanceof com.clothingstore.patterns.state.PaidOrderState
            );
        } else {
            orderStatusLabel.setText("Trạng thái: (chưa có đơn hàng)");
            payButton.setEnabled(false);
            completeButton.setEnabled(false);
            cancelButton.setEnabled(false);
        }
    }
}