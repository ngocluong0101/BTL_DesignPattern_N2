package com.clothingstore.service;


import com.clothingstore.dao.VoucherDAO;
import com.clothingstore.model.IVoucher;
import java.util.List;

public class VoucherService {
    private static VoucherService instance;
    private final VoucherDAO voucherDAO = new VoucherDAO();

    private VoucherService() {}

    public static VoucherService getInstance() {
        if (instance == null) {
            instance = new VoucherService();
        }
        return instance;
    }

    public void saveVoucher(IVoucher voucher) {
        voucherDAO.saveVoucher(voucher);
    }

    public List<IVoucher> getAllVouchers() {
        return voucherDAO.getAllVouchers();
    }

    public void deactivateVoucher(int voucherId) {
        voucherDAO.deactivateVoucher(voucherId);
    }

    public void updateVoucher(IVoucher voucher) {
        voucherDAO.updateVoucher(voucher);
    }
}