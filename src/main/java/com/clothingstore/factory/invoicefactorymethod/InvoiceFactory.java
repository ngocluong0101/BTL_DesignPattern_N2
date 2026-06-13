package com.bach.factory.invoicefactorymethod;

import java.sql.SQLException;

public abstract class InvoiceFactory {
    public abstract Invoice createInvoice(Object... params);
    public abstract Invoice retrieveInvoice(int id) throws SQLException;
}