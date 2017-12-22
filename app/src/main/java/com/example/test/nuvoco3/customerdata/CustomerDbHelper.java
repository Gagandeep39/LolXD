package com.example.test.nuvoco3.customerdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.test.nuvoco3.customerdata.CustomerContract.CustomerEntry;
import com.example.test.nuvoco3.lead.viewcustomerdata.cusomerlistrecyclerview.Customer;
import com.example.test.nuvoco3.lead.viewcustomerdata.cusomerlistrecyclerview.CustomerContact;

import java.util.ArrayList;

/**
 * Created by Administrator on 15-12-2017.
 */

public class CustomerDbHelper extends SQLiteOpenHelper {
    public static final String TAG = "Nuvoco";
    private static final String DATABASE_NAME = "/storage/emulated/0/customerlist.db";
    private static final int DATABASE_VERSION = 1;
    public CustomerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_CUSTOMER_MASTER_TABLE =  "CREATE TABLE " + CustomerEntry.TABLE_NAME + " ("
                + CustomerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CustomerEntry.COLUMN_CUSTOMER_NAME + " VARCHAR(50), "
                + CustomerEntry.COLUMN_CUSTOMER_TYPE + " VARCHAR(20), "
                + CustomerEntry.COLUMN_CUSTOMER_CATEGORY + " VARCHAR(20), "
                + CustomerEntry.COLUMN_CUSTOMER_ADDRESS + " VARCHAR, "
                + CustomerEntry.COLUMN_CUSTOMER_AREA + " VARCHAR(20), "
                + CustomerEntry.COLUMN_CUSTOMER_DISTRICT + " VARCHAR(20), "
                + CustomerEntry.COLUMN_CUSTOMER_STATE + " VARCHAR(20), "
                + CustomerEntry.COLUMN_CUSTOMER_PHONE_NO + " INTEGER NOT NULL DEFAULT 0, "
                + CustomerEntry.COLUMN_CUSTOMER_EMAIL_ID + " VARCHAR(50), "
                + CustomerEntry.COLUMN_CUSTOMER_STATUS + " BIT, "
                + CustomerEntry.COLUMN_CUSTOMER_CREATED_ON+ " DATETIME, "
                + CustomerEntry.COLUMN_CUSTOMER_CREATED_BY + " VARCHAR(50), "
                + CustomerEntry.COLUMN_CUSTOMER_UPDATED_ON + " DATETIME, "
                + CustomerEntry.COLUMN_CUSTOMER_UPDATED_BY + " VARCHAR(50) );";

//         Execute the SQL statement
        db.execSQL(SQL_CREATE_CUSTOMER_MASTER_TABLE);
//
        String SQL_CREATE_CUSTOMER_CONTACT_TABLE =  "CREATE TABLE " + CustomerEntry.TABLE_NAME_CONTACT + " ("
                + CustomerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CustomerEntry.CUSTOMER_ID + " INTEGER NOT NULL DEFAULT 0, "
                + CustomerEntry.COLUMN_CUSTOMER_NAME + " VARCHAR(50), "
                + CustomerEntry.COLUMN_CUSTOMER_CONTACT_NAME + " VARCHAR(50), "
                + CustomerEntry.COLUMN_CUSTOMER_CONTACT_PHONE_NO + " INTEGER NOT NULL DEFAULT 0, "
                + CustomerEntry.COLUMN_CUSTOMER_CONTACT_EMAIL_ID + " VARCHAR(50), "
                + CustomerEntry.COLUMN_CUSTOMER_CONTACT_DOB + " DATETIME, "
                + CustomerEntry.COLUMN_CUSTOMER_CONTACT_DOA+ " DATETIME, "
                + CustomerEntry.COLUMN_CUSTOMER_CREATED_BY + " VARCHAR(50), "
                + CustomerEntry.COLUMN_CUSTOMER_CREATED_ON + " DATETIME, "
                + CustomerEntry.COLUMN_CUSTOMER_UPDATED_BY + " VARCHAR(50), "
                + CustomerEntry.COLUMN_CUSTOMER_UPDATED_ON + " DATETIME );";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_CUSTOMER_CONTACT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public ArrayList<Customer> getDataCustomerMaster(){
        ArrayList<Customer> customers = new ArrayList<>();

        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from "+ CustomerContract.CustomerEntry.TABLE_NAME +" ;",null);
        int customerName = cursor.getColumnIndex(CustomerEntry.COLUMN_CUSTOMER_NAME);
        int customerType = cursor.getColumnIndex(CustomerEntry.COLUMN_CUSTOMER_TYPE);
        int customerCategory = cursor.getColumnIndex(CustomerEntry.COLUMN_CUSTOMER_CATEGORY);
        int customerAddress = cursor.getColumnIndex(CustomerEntry.COLUMN_CUSTOMER_ADDRESS);
        int customerArea = cursor.getColumnIndex(CustomerEntry.COLUMN_CUSTOMER_AREA);
        int customerDistrict = cursor.getColumnIndex(CustomerEntry.COLUMN_CUSTOMER_DISTRICT);
        int customerState = cursor.getColumnIndex(CustomerEntry.COLUMN_CUSTOMER_STATE);
        int customerPhonono = cursor.getColumnIndex(CustomerEntry.COLUMN_CUSTOMER_PHONE_NO);
        int customerEmailid = cursor.getColumnIndex(CustomerEntry.COLUMN_CUSTOMER_EMAIL_ID);
        int customerStatus = cursor.getColumnIndex(CustomerEntry.COLUMN_CUSTOMER_STATUS);
        int customerCreatedBy = cursor.getColumnIndex(CustomerEntry.COLUMN_CUSTOMER_CREATED_BY);
        int customerCreatedOn = cursor.getColumnIndex(CustomerEntry.COLUMN_CUSTOMER_CREATED_ON);
        int customerUpdatedBy = cursor.getColumnIndex(CustomerEntry.COLUMN_CUSTOMER_UPDATED_BY  );
        int customerUpdatedOn = cursor.getColumnIndex(CustomerEntry.COLUMN_CUSTOMER_UPDATED_ON);
        if (cursor.moveToFirst()){
            customers.clear();
            do {
               customers.add(new Customer(
                        cursor.getString(customerName),
                        cursor.getString(customerType),
                        cursor.getString(customerCategory),
                        cursor.getString(customerAddress),
                        cursor.getString(customerArea),
                       cursor.getString(customerDistrict),
                       cursor.getString(customerState),
                       cursor.getString(customerPhonono),
                       cursor.getString(customerEmailid),
                       cursor.getString(customerStatus),
                       cursor.getString(customerCreatedBy),
                       cursor.getString(customerCreatedOn),
                       cursor.getString(customerUpdatedBy),
                       cursor.getString(customerUpdatedOn)

               ));

            }while (cursor.moveToNext());
        }
        database.close();
        return customers;

    }
    public ArrayList<CustomerContact> getDataCustomerContact(){
        ArrayList<CustomerContact> mCustomerContact = new ArrayList<>();

        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from "+ CustomerContract.CustomerEntry.TABLE_NAME +" ;",null);
        int recordID = cursor.getColumnIndex(CustomerEntry._ID);
        int customerID = cursor.getColumnIndex(CustomerEntry._ID);
        int customerName = cursor.getColumnIndex(CustomerEntry.COLUMN_CUSTOMER_NAME);
        int customerContactName = cursor.getColumnIndex(CustomerEntry.COLUMN_CUSTOMER_CONTACT_NAME);
        int customerContactPhone = cursor.getColumnIndex(CustomerEntry.COLUMN_CUSTOMER_CONTACT_PHONE_NO);
        int customerContactEmail = cursor.getColumnIndex(CustomerEntry.COLUMN_CUSTOMER_CONTACT_EMAIL_ID);
        int customerContactDOB = cursor.getColumnIndex(CustomerEntry.COLUMN_CUSTOMER_CONTACT_DOB);
        int customerContactDOA = cursor.getColumnIndex(CustomerEntry.COLUMN_CUSTOMER_CONTACT_DOA);
        int createdBy = cursor.getColumnIndex(CustomerEntry.COLUMN_CUSTOMER_CREATED_BY);
        int createdOn = cursor.getColumnIndex(CustomerEntry.COLUMN_CUSTOMER_CREATED_ON);
        int updatedBy = cursor.getColumnIndex(CustomerEntry.COLUMN_CUSTOMER_UPDATED_BY);
        int updatedOn = cursor.getColumnIndex(CustomerEntry.COLUMN_CUSTOMER_UPDATED_ON);
        if (cursor.moveToFirst()){
            mCustomerContact.clear();
            do {
                mCustomerContact.add(new CustomerContact(
                        cursor.getInt(recordID),
                        cursor.getInt(customerID),
                        cursor.getString(customerName),
                        cursor.getString(customerContactName),
                        cursor.getString(customerContactPhone),
                        cursor.getString(customerContactEmail),
                        cursor.getString(customerContactDOB),
                        cursor.getString(customerContactDOA),
                        cursor.getString(createdBy),
                        cursor.getString(createdOn),
                        cursor.getString(updatedBy),
                        cursor.getString(updatedOn)

                ));

            }while (cursor.moveToNext());
        }
        return mCustomerContact;

    }

    public void insertData(String tableName, ContentValues values){
        SQLiteDatabase database = getWritableDatabase();
        database.insert(tableName, null, values);
    }

    public void updateRow(String tableName, ContentValues contentValues, String position){
        SQLiteDatabase database = this.getWritableDatabase();
        database.update(tableName, contentValues, position, null);
    }

    public void deleteRow(String tableName, String position){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(tableName, position, null);
    }

}
