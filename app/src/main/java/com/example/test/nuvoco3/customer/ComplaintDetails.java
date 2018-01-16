package com.example.test.nuvoco3.customer;

/**
 * Created by gagandeep on 27/12/17.
 */

public class ComplaintDetails {
    private String mRecordId;
    private String mComplaintId;
    private String mDate;
    private String mRepresentative;
    private String mCustomerId;
    private String mCustomerName;
    private String mComplaintStatus;
    private String mComplaintDetails;
    private String mComplaintRemark;
    private String mCreatedOn;
    private String mCreatedBy;
    private String mUpdatedOn;
    private String mUpdatedBy;
    private String mClosedOn;

    public ComplaintDetails(String mRecordId, String mComplaintId, String mDate, String mRepresentative, String mCustomerId, String mCustomerName, String mComplaintStatus, String mComplaintDetails, String mComplaintRemark, String mCreatedOn, String mCreatedBy, String mUpdatedOn, String mUpdatedBy, String mClosedOn) {
        this.mRecordId = mRecordId;
        this.mComplaintId = mComplaintId;
        this.mDate = mDate;
        this.mRepresentative = mRepresentative;
        this.mCustomerId = mCustomerId;
        this.mCustomerName = mCustomerName;
        this.mComplaintStatus = mComplaintStatus;
        this.mComplaintDetails = mComplaintDetails;
        this.mComplaintRemark = mComplaintRemark;
        this.mCreatedOn = mCreatedOn;
        this.mCreatedBy = mCreatedBy;
        this.mUpdatedOn = mUpdatedOn;
        this.mUpdatedBy = mUpdatedBy;
        this.mClosedOn = mClosedOn;

    }

    public String getRecordId() {
        return mRecordId;
    }

    public String getComplaintId() {
        return mComplaintId;
    }

    public String getDate() {
        return mDate;
    }

    public String getRepresentative() {
        return mRepresentative;
    }

    public String getCustomerId() {
        return mCustomerId;
    }

    public String getCustomerName() {
        return mCustomerName;
    }

    public String getComplaintStatus() {
        return mComplaintStatus;
    }

    public String getComplaintDetails() {
        return mComplaintDetails;
    }

    public String getComplaintRemark() {
        return mComplaintRemark;
    }

    public String getCreatedOn() {
        return mCreatedOn;
    }

    public String getCreatedBy() {
        return mCreatedBy;
    }

    public String getUpdatedOn() {
        return mUpdatedOn;
    }

    public String getUpdatedBy() {
        return mUpdatedBy;
    }

    public String getClosedOn() {
        return mClosedOn;
    }
}
