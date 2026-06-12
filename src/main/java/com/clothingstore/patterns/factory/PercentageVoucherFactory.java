package com.clothingstore.patterns.factory;


import com.clothingstore.model.IVoucher;
import com.clothingstore.model.PercentageVoucher;

public class PercentageVoucherFactory implements VoucherFactory {
    @Override
    public IVoucher createVoucher(int id, String code, String name, double startValue, double endValue, double value) {
        return new PercentageVoucher(id, code, name, startValue, endValue, true, value);
    }
}