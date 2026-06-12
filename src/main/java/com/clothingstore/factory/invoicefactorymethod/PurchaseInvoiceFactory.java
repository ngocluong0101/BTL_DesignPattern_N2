package com.clothingstore.factory.invoicefactorymethod;


import java.sql.SQLException;

public class PurchaseInvoiceFactory extends InvoiceFactory {
    @Override
    public Invoice createInvoice(Object... params) {
        int adminId = (int) params[0];
        double amount = (double) params[1];
        String purchaseDate = (String) params[2];
        String status = (String) params[3];
        return new PurchaseInvoice(adminId, amount, purchaseDate, status);
    }

    @Override
    public Invoice retrieveInvoice(int id) throws SQLException {
        PurchaseInvoice invoice = new PurchaseInvoice();
        invoice.exportInvoice(id);
        return invoice;
    }
}