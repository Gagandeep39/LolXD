package com.example.test.nuvoco3.market;

/**
 * Created by gagandeep on 25/12/17.
 */

public class GeneralMarket {

    private String mRecordId;
    private String mRepresentative;
    private String mCounter;
    private String mDate;
    private String mCustomer;
    private String mMarketDetail;
    private String mMSP;
    private String mPrice;
    private String mDemand;
    private String mCreatedBn;
    private String mCreatedOn;
    private String mUpdatedBy;
    private String mUpdatedOn;

    public GeneralMarket(String mRecordId, String mRepresentative, String mCounter, String mDate, String mCustomer, String mMarketDetail, String mMSP, String mPrice, String mDemand, String mCreatedBn, String mCreatedOn, String mUpdatedBy, String mUpdatedOn) {
        this.mRecordId = mRecordId;
        this.mRepresentative = mRepresentative;
        this.mCounter = mCounter;
        this.mDate = mDate;
        this.mCustomer = mCustomer;
        this.mMarketDetail = mMarketDetail;
        this.mMSP = mMSP;
        this.mPrice = mPrice;
        this.mDemand = mDemand;
        this.mCreatedBn = mCreatedBn;
        this.mCreatedOn = mCreatedOn;
        this.mUpdatedBy = mUpdatedBy;
        this.mUpdatedOn = mUpdatedOn;
    }

    public String getRecordId() {
        return mRecordId;
    }

    public String getRepresentative() {
        return mRepresentative;
    }

    public String getCounter() {
        return mCounter;
    }

    public String getDate() {
        return mDate;
    }

    public String getCustomer() {
        return mCustomer;
    }

    public String getMarketDetail() {
        return mMarketDetail;
    }

    public String getMSP() {
        return mMSP;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getDemand() {
        return mDemand;
    }

    public String getCreatedBn() {
        return mCreatedBn;
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
