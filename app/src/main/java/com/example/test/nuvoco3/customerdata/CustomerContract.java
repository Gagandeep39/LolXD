package com.example.test.nuvoco3.customerdata;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Administrator on 15-12-2017.
 */

public class CustomerContract {
    private CustomerContract() {}
    public static final String CONTENT_AUTHORITY = "com.example.test.nuvoco3";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //customer master
    public static final String PATH_CUSTOMER = "customer_master";
    public static final class CustomerEntry implements BaseColumns {

        //Customer master
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CUSTOMER);
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CUSTOMER;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CUSTOMER;

        //Customer Master
        public final static String TABLE_NAME = "customer_master";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_CUSTOMER_NAME ="name";
        public final static String COLUMN_CUSTOMER_TYPE = "type";   //Actual, Prospect
        public final static String COLUMN_CUSTOMER_CATEGORY = "category";   //Dealer, Sub dealer, Individual Customer
        public final static String COLUMN_CUSTOMER_ADDRESS = "address";
        public final static String COLUMN_CUSTOMER_AREA = "area";
        public final static String COLUMN_CUSTOMER_DISTRICT = "district";
        public final static String COLUMN_CUSTOMER_STATE = "state";
        public final static String COLUMN_CUSTOMER_PHONE_NO = "phone";
        public final static String COLUMN_CUSTOMER_EMAIL_ID = "email";
        public final static String COLUMN_CUSTOMER_STATUS = "status";
        public final static String COLUMN_CUSTOMER_CREATED_ON = "created_by";
        public final static String COLUMN_CUSTOMER_CREATED_BY = "created_on";
        public final static String COLUMN_CUSTOMER_UPDATED_ON = "updated_by";
        public final static String COLUMN_CUSTOMER_UPDATED_BY = "updated_on";

//        //Customer Contact Master
        public final static String TABLE_NAME_CONTACT = "customer_contact";
        public final static String CUSTOMER_ID = "customer_id";
        public final static String COLUMN_CUSTOMER_CONTACT_NAME ="contact_name";
        public final static String COLUMN_CUSTOMER_CONTACT_PHONE_NO = "contact_phone";
        public final static String COLUMN_CUSTOMER_CONTACT_EMAIL_ID = "contact_email";
        public final static String COLUMN_CUSTOMER_CONTACT_DOB = "contact_dob";
        public final static String COLUMN_CUSTOMER_CONTACT_DOA = "contact_doa";
    }

}
