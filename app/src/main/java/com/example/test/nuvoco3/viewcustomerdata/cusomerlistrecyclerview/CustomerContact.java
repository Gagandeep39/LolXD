package com.example.test.nuvoco3.viewcustomerdata.cusomerlistrecyclerview;

/**
 * Created by gagandeep on 17/12/17.
 */

public class CustomerContact {
    public int getRecordID() {
        return recordID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerContactName() {
        return customerContactName;
    }

    public String getCustomerContactPhone() {
        return customerContactPhone;
    }

    public String getCustomerContactEmail() {
        return customerContactEmail;
    }

    public String getCustomerContactDOB() {
        return customerContactDOB;
    }

    public String getCustomerContactDOA() {
        return customerContactDOA;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public CustomerContact(int recordID, int customerID, String customerName, String customerContactName, String customerContactPhone, String customerContactEmail, String customerContactDOB, String customerContactDOA, String createdBy, String createdOn, String updatedBy, String updatedOn) {
        this.recordID = recordID;
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerContactName = customerContactName;
        this.customerContactPhone = customerContactPhone;
        this.customerContactEmail = customerContactEmail;
        this.customerContactDOB = customerContactDOB;
        this.customerContactDOA = customerContactDOA;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.updatedBy = updatedBy;
        this.updatedOn = updatedOn;
    }

    private int recordID;
    private int customerID;
    private String customerName;
    private String customerContactName;
    private String customerContactPhone;
    private String customerContactEmail;
    private String customerContactDOB;
    private String customerContactDOA;
    private String createdBy;
    private String createdOn;
    private String updatedBy;
    private String updatedOn;


}
