package com.clothingstore.controller;

import com.clothingstore.component.Navbar;
import com.clothingstore.model.Supplier;
import com.clothingstore.util.Navigator;
import com.clothingstore.service.SupplierService;
import com.clothingstore.view.supplier.SupplierView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SupplierController {
    private SupplierView view;
    private SupplierService service;

    public SupplierController() {
        this.service = new SupplierService();
        this.view = new SupplierView();

        loadSuppliers();
        
        this.view.setSearchButtonListener(new SearchListener());
        this.view.setRefreshButtonListener(new RefreshListener());
        this.view.setAddSupplierButtonListener(new AddListener());
        this.view.setEditButtonListener(new EditListener());
        this.view.setDeleteButtonListener(new DeleteListener());
        this.view.setActivateButtonListener(new ActivateListener());
        this.view.setClearFormButtonListener(new ClearFormListener());
        
        this.view.setVisible(true);
    }

    private void loadSuppliers() {
        try {
            List<Supplier> suppliers = service.getAllSuppliers();
            view.setSupplierData(suppliers);
        } catch (Exception e) {
            view.showError(e.getMessage());
        }
    }

    class SearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String keyword = view.getSearchKeyword();
            String category = view.getSearchCategory();
            String state = view.getSearchState();
            try {
                List<Supplier> suppliers = service.searchSuppliers(keyword, category, state);
                view.setSupplierData(suppliers);
            } catch (Exception ex) {
                view.showError(ex.getMessage());
            }
        }
    }

    class RefreshListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.clearSearchFields();
            loadSuppliers();
        }
    }

    class AddListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Navigator.navigate(Navbar.NavItem.CREATE_SUPPLIER, view);
        }
    }

    class EditListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Supplier supplier = view.getSelectedSupplierFromForm();
                if (supplier.getId() <= 0) {
                    view.showError("Vui lòng chọn một nhà cung cấp để cập nhật.");
                    return;
                }
                service.updateSupplier(supplier);
                view.showMessage("Cập nhật nhà cung cấp thành công!");
                loadSuppliers();
                view.clearForm();
            } catch (Exception ex) {
                view.showError(ex.getMessage());
            }
        }
    }

    class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Supplier supplier = view.getSelectedSupplierFromForm();
                if (supplier.getId() <= 0) {
                    view.showError("Vui lòng chọn một nhà cung cấp để xóa.");
                    return;
                }
                view.showConfirmDeleteDialog("Bạn có chắc chắn muốn xóa nhà cung cấp này?", new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            service.deleteSupplier(supplier.getId());
                            view.showMessage("Xóa thành công.");
                            loadSuppliers();
                            view.clearForm();
                        } catch (Exception ex) {
                            view.showError(ex.getMessage());
                            if (ex.getMessage().contains("tự động chuyển trạng thái")) {
                                loadSuppliers();
                                view.clearForm();
                            }
                        }
                    }
                });
            } catch (Exception ex) {
                view.showError(ex.getMessage());
            }
        }
    }

    class ActivateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Supplier supplier = view.getSelectedSupplierFromForm();
                if (supplier.getId() <= 0) {
                    view.showError("Vui lòng chọn nhà cung cấp.");
                    return;
                }
                if (supplier.getState().equalsIgnoreCase("inactive")) {
                    service.activateSupplier(supplier.getId());
                    view.showMessage("Đã kích hoạt nhà cung cấp.");
                } else {
                    service.deactivateSupplier(supplier.getId());
                    view.showMessage("Đã ngừng hợp tác nhà cung cấp.");
                }
                loadSuppliers();
                view.clearForm();
            } catch (Exception ex) {
                view.showError(ex.getMessage());
            }
        }
    }

    class ClearFormListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.clearForm();
        }
    }
}
