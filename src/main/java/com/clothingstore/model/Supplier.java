package com.clothingstore.model;


import com.clothingstore.patterns.sessionsingleton.Session;
import com.clothingstore.patterns.supplierstate.InActiveSupplierState;
import com.clothingstore.patterns.supplierstate.SupplierState;

public class Supplier {

    private int id;
    private String name;
    private String phone;
    private String address;
    private String email;
    private int adminId;
    private SupplierState state;

    public Supplier() {
        this.setAdminId(Session.getInstance().getId());
        this.state = new InActiveSupplierState(this);
    }

    public Supplier(int id, String name, String phone, String address, String email, int adminId) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.adminId = adminId;
        this.state = new InActiveSupplierState(this);
    }

    public String getState() {
        if (state instanceof InActiveSupplierState) {
            return "inactive";
        } else {
            return "active";
        }
    }

    public void changeState(SupplierState state) {
        this.state = state;
    }

    public boolean canEdit() {
        return state.canEdit();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
}