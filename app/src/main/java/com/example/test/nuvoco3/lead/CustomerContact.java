package com.example.test.nuvoco3.lead;

/**
 * Created by gagandeep on 17/12/17.
 */

public class CustomerContact {
    private String contactId;
    private String customerId;
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

    public CustomerContact(String contactId, String customerId, String customerName, String customerContactName, String customerContactPhone, String customerContactEmail, String customerContactDOB, String customerContactDOA, String createdBy, String createdOn, String updatedBy, String updatedOn) {
        this.contactId = contactId;
        this.customerId = customerId;
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

    public String getContactId() {
        return contactId;
    }

    public String getCustomerId() {
        return customerId;
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


}
