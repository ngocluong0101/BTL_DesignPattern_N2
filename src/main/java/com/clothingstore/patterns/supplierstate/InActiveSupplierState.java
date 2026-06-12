package com.clothingstore.patterns.supplierstate;


import com.clothingstore.model.Supplier;

public class InActiveSupplierState extends SupplierState {

    public InActiveSupplierState(Supplier supplier) {
        this.supplier = supplier;
    }


    @Override
    public boolean canEdit() {
        return false;
    }
}