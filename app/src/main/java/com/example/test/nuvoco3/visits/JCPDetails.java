package com.example.test.nuvoco3.visits;

public class JCPDetails {

    private String mRecordId;
    private String mJcpId;
    private String mDate;
    private String mCustomerId;
    private String mCustomerName;
    private String mObjective;
    private String mOrder;
    private String mOrderQuantity;
    private String mRemark;
    private String mStatus;
    private String mCreatedBy;
    private String mCreatedOn;
    private String mUpdatedBy;
    private String mUpdatedOn;

    public JCPDetails(String mRecordId, String mJcpId, String mDate, String mCustomerId, String mCustomerName, String mObjective, String mOrder, String mOrderQuantity, String mRemark, String mStatus, String mCreatedBy, String mCreatedOn, String mUpdatedBy, String mUpdatedOn) {
        this.mRecordId = mRecordId;
        this.mJcpId = mJcpId;
        this.mDate = mDate;
        this.mCustomerId = mCustomerId;
        this.mCustomerName = mCustomerName;
        this.mObjective = mObjective;
        this.mOrder = mOrder;
        this.mOrderQuantity = mOrderQuantity;
        this.mRemark = mRemark;
        this.mStatus = mStatus;
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

    public String getOrderQuantity() {
        return mOrderQuantity;
    }

    public String getRemark() {
        return mRemark;
    }

    public String getStatus() {
        return mStatus;
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
