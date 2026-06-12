package com.clothingstore.patterns.supplierstate;

import com.clothingstore.model.Supplier;

public class ActiveSupplierState extends SupplierState {

    public ActiveSupplierState(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public boolean canEdit() {
        return true;
    }
}