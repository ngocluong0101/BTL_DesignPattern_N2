package com.clothingstore.controller;

import com.clothingstore.factory.invoicefactorymethod.InvoiceFactory;
import com.clothingstore.factory.invoicefactorymethod.PurchaseInvoiceFactory;
import com.clothingstore.patterns.sessionsingleton.Session;
import com.clothingstore.view.invoice.invoicepurchase.InvoicePurchaseView;
import com.clothingstore.view.invoice.invoicepurchase.InvoicePurchaseSearchDialog;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InvoicePurchaseController {
    private final InvoicePurchaseView view;
    private final InvoiceFactory purchaseFactory;

    public InvoicePurchaseController(String adminFullname) {
        this.view = new InvoicePurchaseView(adminFullname);
        this.purchaseFactory = new PurchaseInvoiceFactory();
        this.view.setVisible(true);
        initController();
    }

    private void initController() {
        view.getBtnCreate().addActionListener(e -> saveInvoice());
        view.getBtnExport().addActionListener(e -> openSearchDialog());
    }

    private void saveInvoice() {
        try {
            int adminId = Session.getInstance().getId();
//            if (adminId == -1) {
//                JOptionPane.showMessageDialog(frame, "Không tìm thấy ID của admin được chọn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
            double amount = Double.parseDouble(view.getTxtAmount().getText().trim());
            String dateStr = view.getTxtBuyDate().getText().trim();

            // Kiểm tra định dạng ngày hợp lệ (yyyy-MM-dd)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                LocalDate.parse(dateStr, formatter); // chỉ để validate, không cần giữ lại
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập ngày đúng định dạng yyyy-MM-dd.");
                return;
            }

            // bookingDate giữ nguyên là String
            String bookingDate = dateStr;

            String status = (String) view.getComboStatus().getSelectedItem();

            // Tạo đối tượng InvoiceSale
//            InvoiceSale invoice = new InvoiceSale();
//            invoice.setOrderId(orderId);
//            invoice.setQuantity(quantity);
//            invoice.setBookingDate(bookingDate);
//            invoice.setStatus(status);

            Invoice salesInvoice = purchaseFactory.createInvoice(adminId, amount, bookingDate, status);

            // lưu vào database
//            boolean success = service.insertInvoice(invoice);
            boolean success = salesInvoice.saveToDatabase();
            System.out.println(success);
            if (success) {
                JOptionPane.showMessageDialog(view, "Tạo Hóa Đơn Thành Công");

                // Reset form về mặc định
                view.getTxtAmount().setText("");
                view.getTxtBuyDate().setText("");
                view.getComboStatus().setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(view, "Tạo Hóa Đơn Thất Bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Số tiền phải là số.");
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(view, "Định dạng ngày không hợp lệ. Sử dụng yyyy-MM-dd.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Lỗi: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void openSearchDialog() {
        InvoicePurchaseSearchDialog dialog = new InvoicePurchaseSearchDialog(null);

        dialog.getBtnSearch().addActionListener(event -> {
            String idText = dialog.getTxtSearchId().getText().trim();
            if (!idText.isEmpty()) {
                try {
                    int invoiceId = Integer.parseInt(idText);
                    Invoice retrievedSales = purchaseFactory.retrieveInvoice(invoiceId);
                    String result = retrievedSales.exportInvoice(invoiceId);
                    dialog.displayResult(result);
                } catch (NumberFormatException ex) {
                    dialog.displayResult("ID không hợp lệ.");
                } catch (SQLException e) {
                    dialog.displayResult("Lỗi khi tìm kiếm hóa đơn.");
                }
            }
        });

        dialog.setVisible(true);
    }
}