package com.clothingstore.factory.invoicefactorymethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.clothingstore.database.ConnectionManager;


public class SalesInvoice implements Invoice {
    private int invoiceId;
    private int orderId;
    private int quantity;
    private String bookingDate;
    private String status;
    public SalesInvoice() {}
    public SalesInvoice( int orderId, int quantity, String bookingDate, String status) {
        this.orderId = orderId;
        this.quantity = quantity;
        this.bookingDate = bookingDate;
        this.status = status;
    }
    @Override
    public boolean saveToDatabase() throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO sales_bills (id_orders, amount, booking_date, state) VALUES (?, ?, ?, ?)")) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, quantity);
            stmt.setString(3, bookingDate);
            stmt.setString(4, status);
            stmt.executeUpdate();
            System.out.println("Sales invoice saved to database.");
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String exportInvoice(int id) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM sales_bills WHERE id_sales_bills = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "ID: " + rs.getInt("id_sales_bills") +
                        "\nMã đặt hàng: " + rs.getInt("id_orders") +
                        "\nSố lượng: " + rs.getInt("amount") +
                        "\nNgày bán: " + rs.getString("booking_date") +
                        "\nTrạng thái: " + rs.getString("state");
            } else {
                return "Không tìm thấy hóa đơn với ID này.";
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi khi tìm hóa đơn: " + e.getMessage();
        }
    }

    @Override
    public String displayInvoice() {
        return String.format(
                "Sales Invoice - ID: %d\nOrder ID: %d\nQuantity: %d\nBooking Date: %s\nStatus: %s",
                invoiceId, orderId, quantity, bookingDate, status
        );
    }
}