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
    private String taxCode;
    private String category;
    private String contactPerson;
    private int adminId;
    private SupplierState state;

    public Supplier() {
        this.setAdminId(Session.getInstance().getId());
        this.state = new InActiveSupplierState(this);
    }

    public Supplier(int id, String name, String phone, String address, String email, String taxCode, String category, String contactPerson, int adminId) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.taxCode = taxCode;
        this.category = category;
        this.contactPerson = contactPerson;
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

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
}