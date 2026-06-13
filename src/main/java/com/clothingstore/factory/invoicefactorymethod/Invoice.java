package com.bach.factory.invoicefactorymethod;

import java.sql.SQLException;

public interface Invoice {
    String displayInvoice();
    boolean saveToDatabase() throws SQLException;
    String exportInvoice(int id) throws SQLException;
}