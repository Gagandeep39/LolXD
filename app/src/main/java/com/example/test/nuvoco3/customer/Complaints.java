package com.example.test.nuvoco3.customer;

/**
 * Created by gagandeep on 29/12/17.
 */

public class Complaints {
    private String mCustomerId;
    private String mCustomerName;
    private String mType;
    private String mDetails;
    private String mComplaintId;
    private String mDate;
    private String mCreatedOn;
    private String mCreatedBy;
    private String mUpdatedOn;
    private String mUpdatedBy;

    public Complaints(String mCustomerId, String mCustomerName, String mType, String mDetails, String mComplaintId, String mDate, String mCreatedOn, String mCreatedBy, String mUpdatedOn, String mUpdatedBy) {
        this.mCustomerId = mCustomerId;
        this.mCustomerName = mCustomerName;
        this.mType = mType;
        this.mDetails = mDetails;
        this.mComplaintId = mComplaintId;
        this.mDate = mDate;
        this.mCreatedOn = mCreatedOn;
        this.mCreatedBy = mCreatedBy;
        this.mUpdatedOn = mUpdatedOn;
        this.mUpdatedBy = mUpdatedBy;
    }

    public String getCustomerId() {
        return mCustomerId;
    }

    public String getCustomerName() {
        return mCustomerName;
    }

    public String getType() {
        return mType;
    }

    public String getDetails() {
        return mDetails;
    }

    public String getComplaintId() {
        return mComplaintId;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmCreatedOn() {
        return mCreatedOn;
    }

    public String getmCreatedBy() {
        return mCreatedBy;
    }

    public String getmUpdatedOn() {
        return mUpdatedOn;
    }

    public String getmUpdatedBy() {
        return mUpdatedBy;
    }
}

