package com.example.test.nuvoco3.visits;

public class JCP {


    private String mRecordId;
    private String mCustomerId;
    private String mCustomerName;
    private String mDate;
    private String mStartTime;
    private String mEndTime;
    private String mObjective;
    private String mCreatedOn;
    private String mCreatedBy;
    private String mUpdatedOn;
    private String mUpdatedBy;

    public JCP(String mRecordId, String mCustomerId, String mCustomerName, String mDate, String mStartTime, String mEndTime, String mObjective, String mCreatedOn, String mCreatedBy, String mUpdatedOn, String mUpdatedBy) {
        this.mRecordId = mRecordId;
        this.mCustomerId = mCustomerId;
        this.mCustomerName = mCustomerName;
        this.mDate = mDate;
        this.mStartTime = mStartTime;
        this.mEndTime = mEndTime;
        this.mObjective = mObjective;
        this.mCreatedOn = mCreatedOn;
        this.mCreatedBy = mCreatedBy;
        this.mUpdatedOn = mUpdatedOn;
        this.mUpdatedBy = mUpdatedBy;
    }


    public String getmRecordId() {
        return mRecordId;
    }

    public String getCustomerId() {
        return mCustomerId;
    }

    public String getCustomerName() {
        return mCustomerName;
    }

    public String getDate() {
        return mDate;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public String getObjective() {
        return mObjective;
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
}
