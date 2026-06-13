package com.clothingstore.controller.supplier;

import com.clothingstore.component.Navbar;
import com.clothingstore.model.Supplier;
import com.clothingstore.patterns.supplierstate.ActiveSupplierState;
import com.clothingstore.patterns.supplierstate.InActiveSupplierState;
import com.clothingstore.service.SupplierService;
import com.clothingstore.util.Navigator;
import com.clothingstore.view.supplier.SupplierView;

public class SupplierController {

    private final SupplierService supplierService;
    private final SupplierView supplierView;

    public SupplierController() {
        this.supplierService = new SupplierService();
        this.supplierView = new SupplierView();
        this.supplierView.setSupplierData(supplierService.getAllSuppliers());
        this.supplierView.setVisible(true);
        initListeners();
    }

    private void initListeners() {
        supplierView.setEditButtonListener(e -> editSupplier());
        supplierView.setDeleteButtonListener(e -> deleteSupplier());
        supplierView.setActivateButtonListener(e -> handleActivateSupplier());
        supplierView.setAddSupplierButtonListener(e -> addSupplier());
    }

    public void addSupplier(){
        Navigator.navigate(Navbar.NavItem.CREATE_SUPPLIER, supplierView);
    }

    public void editSupplier() {

        Supplier supplier = supplierView.getSelectedSupplierFromForm();
        if (supplier != null) {
            if(!supplier.canEdit()){
                supplierView.showError("Nhà cung cấp không thể sửa do đang ở trạng thái không hoạt động.");
                return;
            }
            supplierService.updateSupplier(supplier);
            supplierView.showMessage("Sửa nhà cung cấp thành công");
            supplierView.refreshTable(supplierService.getAllSuppliers());
        } else {
            supplierView.showError("Vui lòng chọn nhà cung cấp để sửa.");
        }

    }

    public void deleteSupplier() {
        Supplier supplier = supplierView.getSelectedSupplierFromForm();
        if (supplier != null) {
            supplierView.showConfirmDeleteDialog("Bạn có chắc chắn muốn xoá nhà cung cấp này không?",
                    e -> {
                        supplierService.deleteSupplier(supplier.getId());
                        supplierView.refreshTable(supplierService.getAllSuppliers());
                        supplierView.showMessage("Xoá nhà cung cấp thành công");
                    });

        } else {
            supplierView.showError("Vui lòng chọn nhà cung cấp để xoá.");
        }
    }

    public void handleActivateSupplier() {
        Supplier supplier = supplierView.getSelectedSupplierFromForm();
        if (supplier != null) {
            if (supplier.getState().equals("active")) {
                supplier.changeState(new InActiveSupplierState(supplier));
            } else {
                supplier.changeState(new ActiveSupplierState(supplier));
            }
            supplierService.updateSupplier(supplier);
            supplierView.showMessage("Đã " + (supplier.getState().equals("active") ? "kích hoạt" : "huỷ kích hoạt") + " nhà cung cấp thành công");
            supplierView.refreshTable(supplierService.getAllSuppliers());
        } else {
            supplierView.showError("Vui lòng chọn nhà cung cấp để kích hoạt hoặc vô hiệu hoá.");
        }
    }
}