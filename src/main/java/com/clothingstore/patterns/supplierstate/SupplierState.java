package com.clothingstore.patterns.supplierstate;


import com.clothingstore.model.Supplier;

public abstract class SupplierState {

    protected Supplier supplier;

    public abstract boolean canEdit();

}