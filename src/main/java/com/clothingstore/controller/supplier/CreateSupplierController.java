package com.clothingstore.controller.supplier;

import com.clothingstore.component.Navbar;
import com.clothingstore.service.SupplierService;
import com.clothingstore.util.Navigator;
import com.clothingstore.view.supplier.CreateSupplierView;
import com.clothingstore.view.supplier.SupplierView;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CreateSupplierController {

    private final SupplierService supplierService;
    private final CreateSupplierView createSupplierView;
    private final SupplierView parentView;
    private boolean refreshParentOnClose;

    public CreateSupplierController() {
        this(null);
    }

    public CreateSupplierController(SupplierView parentView) {
        this.supplierService = new SupplierService();
        this.parentView = parentView;
        this.createSupplierView = new CreateSupplierView();
        initListeners();
        attachLifecycleCallbacks();
        this.createSupplierView.setVisible(true);
    }

    private void initListeners() {
        createSupplierView.addAddButtonListener(e -> createSupplier());
        createSupplierView.addBackButtonListener(e -> backToMain());
    }

    private void attachLifecycleCallbacks() {
        createSupplierView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                restoreParent();
            }
        });
    }

    public void createSupplier(){
        if (createSupplierView.getNameValue().isEmpty() ||
                createSupplierView.getAddressValue().isEmpty() ||
                createSupplierView.getPhoneValue().isEmpty()) {
            createSupplierView.showError("Không được để trống thông tin nhà cung cấp");
            return;
        }

        try {
            supplierService.createSupplier(createSupplierView.getSupplier());
            createSupplierView.showMessage("Tạo nhà cung cấp thành công");
            refreshParentOnClose = true;
            createSupplierView.dispose();
        } catch (RuntimeException e) {
            createSupplierView.showError(e.getMessage());
        }
    }

    public void backToMain() {
        refreshParentOnClose = false;
        createSupplierView.dispose();
    }

    private void restoreParent() {
        if (parentView != null) {
            if (refreshParentOnClose) {
                parentView.refreshTable(supplierService.getAllSuppliers());
            }
            parentView.setVisible(true);
        } else {
            Navigator.navigate(Navbar.NavItem.SUPPLIER, createSupplierView);
        }
        refreshParentOnClose = false;
    }

}