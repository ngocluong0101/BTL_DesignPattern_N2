package com.clothingstore.factory.invoicefactorymethod;

import com.clothingstore.database.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchaseInvoice implements Invoice {
    private int invoiceId;
    private int adminId;
    private double amount;
    private String purchaseDate;
    private String status;

    public PurchaseInvoice() {}

    public PurchaseInvoice(int adminId, double amount, String purchaseDate, String status) {
        this.adminId = adminId;
        this.amount = amount;
        this.purchaseDate = purchaseDate;
        this.status = status;
    }

    @Override
    public boolean saveToDatabase() throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO buy_bills (id_admin, amount, buy_date, state) VALUES (?, ?, ?, ?)")) {
            stmt.setInt(1, adminId);
            stmt.setDouble(2, amount);
            stmt.setString(3, purchaseDate);
            stmt.setString(4, status);
            stmt.executeUpdate();
            System.out.println("Purchase invoice saved to database.");
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
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM buy_bills WHERE id_buy_bills = ?")) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                this.invoiceId = rs.getInt("id_buy_bills");
                this.adminId = rs.getInt("id_admin");
                this.amount = rs.getDouble("amount");
                this.purchaseDate = rs.getString("buy_date");
                this.status = rs.getString("state");

                return "Hóa đơn nhập hàng:\n" +
                        "Mã hóa đơn: " + invoiceId +
                        "\nMã admin: " + adminId +
                        "\nTổng tiền: " + String.format("%.2f", amount) +
                        "\nNgày nhập: " + purchaseDate +
                        "\nTrạng thái: " + status;
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
                "Purchase Invoice - ID: %d\nAdmin ID: %d\nAmount: %.2f\nPurchase Date: %s\nStatus: %s",
                invoiceId, adminId, amount, purchaseDate, status
        );
    }

}