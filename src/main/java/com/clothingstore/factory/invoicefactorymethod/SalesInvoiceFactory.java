package com.clothingstore.factory.invoicefactorymethod;

import java.sql.SQLException;

public class SalesInvoiceFactory extends InvoiceFactory {
    @Override
    public Invoice createInvoice(Object... params) {
//        int invoiceId = (int) params[0];
        int orderId = (int) params[0];
        int quantity = (int) params[1];
        String bookingDate = (String) params[2];
        String status = (String) params[3];
        return new SalesInvoice(orderId, quantity, bookingDate, status);
    }

    @Override
    public Invoice retrieveInvoice(int id) throws SQLException {
        SalesInvoice invoice = new SalesInvoice();
        invoice.exportInvoice(id);
        return invoice;
    }
}