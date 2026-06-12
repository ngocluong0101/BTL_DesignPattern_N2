package com.clothingstore.patterns.factory;

import com.clothingstore.model.IVoucher;

public interface VoucherFactory {
    IVoucher createVoucher(int id, String code, String name, double startValue, double endValue, double value);
}