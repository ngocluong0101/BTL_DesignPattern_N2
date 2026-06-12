package com.clothingstore.patterns.factory;


import com.clothingstore.model.IVoucher;
import com.clothingstore.model.FixedAmountVoucher;

public class FixedAmountVoucherFactory implements VoucherFactory {
    @Override
    public IVoucher createVoucher(int id, String code, String name, double startValue, double endValue, double value) {
        return new FixedAmountVoucher(id, code, name, startValue, endValue, true, value);
    }
}