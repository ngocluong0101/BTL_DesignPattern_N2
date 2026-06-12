package com.clothingstore.controller;

package com.clothingstore.controller;

import com.clothingstore.view.promotion.VoucherManagementView;

public class VoucherManagementController {
    private final VoucherManagementView view;

    public VoucherManagementController() {
        view = new VoucherManagementView();
    }

    public void showView() {
        view.setVisible(true);
    }

    public void hideView() {
        view.setVisible(false);
    }
}