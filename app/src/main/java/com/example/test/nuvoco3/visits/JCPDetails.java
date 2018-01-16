package com.example.test.nuvoco3.visits;

public class JCPDetails {

    private String mRecordId;
    private String mJcpId;
    private String mDate;
    private String mCustomerId;
    private String mCustomerName;
    private String mObjective;
    private String mOrder;
    private String mProductName;
    private String mOrderQuantity;
    private String mVisitRemark;
    private String mVisitStatus;
    private String mCreatedBy;
    private String mCreatedOn;
    private String mUpdatedBy;
    private String mUpdatedOn;

    public JCPDetails(String mRecordId, String mJcpId, String mDate, String mCustomerId, String mCustomerName, String mObjective, String mOrder, String mProductName, String mOrderQuantity, String mVisitRemark, String mVisitStatus, String mCreatedBy, String mCreatedOn, String mUpdatedBy, String mUpdatedOn) {
        this.mRecordId = mRecordId;
        this.mJcpId = mJcpId;
        this.mDate = mDate;
        this.mCustomerId = mCustomerId;
        this.mCustomerName = mCustomerName;
        this.mObjective = mObjective;
        this.mOrder = mOrder;
        this.mProductName = mProductName;
        this.mOrderQuantity = mOrderQuantity;
        this.mVisitRemark = mVisitRemark;
        this.mVisitStatus = mVisitStatus;
        this.mCreatedBy = mCreatedBy;
        this.mCreatedOn = mCreatedOn;
        this.mUpdatedBy = mUpdatedBy;
        this.mUpdatedOn = mUpdatedOn;
    }

    public String getRecordId() {
        return mRecordId;
    }

    public String getJcpId() {
        return mJcpId;
    }

    public String getDate() {
        return mDate;
    }

    public String getCustomerId() {
        return mCustomerId;
    }

    public String getCustomerName() {
        return mCustomerName;
    }

    public String getObjective() {
        return mObjective;
    }

    public String getOrder() {
        return mOrder;
    }

    public String getProductName() {
        return mProductName;
    }

    public String getOrderQuantity() {
        return mOrderQuantity;
    }

    public String getVisitRemark() {
        return mVisitRemark;
    }

    public String getVisitStatus() {
        return mVisitStatus;
    }

    public String getCreatedBy() {
        return mCreatedBy;
    }

    public String getCreatedOn() {
        return mCreatedOn;
    }

    public String getUpdatedBy() {
        return mUpdatedBy;
    }

    public String getUpdatedOn() {
        return mUpdatedOn;
    }
}
