package com.example.test.nuvoco3.market;

/**
 * Created by gagandeep on 26/12/17.
 */

public class BrandPrice {

    private String mRecordId;
    private String mRepresentative;
    private String mCounter;
    private String mDate;
    private String mCustomer;
    private String mProduct;
    private String mWSP;
    private String mRSP;
    private String mStock;
    private String mRemarks;
    private String mCreatedOn;
    private String mCreatedBy;
    private String mUpdatedOn;
    private String mUpdatedBy;

    public BrandPrice(String mRecordId, String mRepresentative, String mCounter, String mDate, String mCustomer, String mProduct, String mWSP, String mRSP, String mStock, String mRemarks, String mCreatedOn, String mCreatedBy, String mUpdatedOn, String mUpdatedBy) {
        this.mRecordId = mRecordId;
        this.mRepresentative = mRepresentative;
        this.mCounter = mCounter;
        this.mDate = mDate;
        this.mCustomer = mCustomer;
        this.mProduct = mProduct;
        this.mWSP = mWSP;
        this.mRSP = mRSP;
        this.mStock = mStock;
        this.mRemarks = mRemarks;
        this.mCreatedOn = mCreatedOn;
        this.mCreatedBy = mCreatedBy;
        this.mUpdatedOn = mUpdatedOn;
        this.mUpdatedBy = mUpdatedBy;
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

    public String getProduct() {
        return mProduct;
    }

    public String getWSP() {
        return mWSP;
    }

    public String getRSP() {
        return mRSP;
    }

    public String getStock() {
        return mStock;
    }

    public String getRemarks() {
        return mRemarks;
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
