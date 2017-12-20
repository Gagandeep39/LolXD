
package com.example.test.nuvoco3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.test.nuvoco3.customerdata.CustomerContract;
import com.example.test.nuvoco3.customerdata.CustomerContract.CustomerEntry;
import com.example.test.nuvoco3.customerdata.CustomerDbHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InsertCustomerActivity extends AppCompatActivity {
    public static final String TAG = "NUVOCO";
    Spinner categorySpinner;
    String customerCategory = "Dealer";
    FloatingActionButton floatingActionButtonAddData;
    TextInputEditText editTextName, editTextAddress, editTextArea, editTextDistrict, editTextState, editTextPhone, editTextEmail, editTextCreatedBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_customer);
        findViews();
        spinnerFunction();
        floatingActionButtonAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertCustomer();
            }
        });

    }


    private void spinnerFunction() {
        final String[] category = {"Dealer", "Sub dealer", "Individual Customer"};
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(InsertCustomerActivity.this, android.R.layout.simple_dropdown_item_1line, category);
        categorySpinner.setAdapter(stringArrayAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        customerCategory = "Dealer";
                        break;
                    case 1:
                        customerCategory = "Sub Dealer";
                        break;
                    case 2:
                        customerCategory = "Individual Customer";
                        break;
                    default:
                        customerCategory = "Dealer";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void findViews() {
        categorySpinner = findViewById(R.id.spinnerCategory);
        floatingActionButtonAddData = findViewById(R.id.fabAddData);
        editTextName = findViewById(R.id.textInputEditName);
        editTextAddress = findViewById(R.id.textInputEditAddress);
        editTextArea = findViewById(R.id.textInputEditArea);
        editTextDistrict = findViewById(R.id.textInputEditDistrict);
        editTextState = findViewById(R.id.textInputEditState);
        editTextPhone = findViewById(R.id.textInputEditPhone);
        editTextEmail = findViewById(R.id.textInputEditEmail);
        editTextCreatedBy = findViewById(R.id.textInputEditCreatedBy);

    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }


    public void insertCustomer() {
        String currentName = editTextName.getText().toString().trim();
        String currentType = "New";
        String currentCategory = customerCategory;
        String currentAddress = editTextAddress.getText().toString().trim();
        String currentArea = editTextArea.getText().toString().trim();
        String currentDistrict = editTextDistrict.getText().toString().trim();
        String currentState = editTextState.getText().toString().trim();
        String currentPhone = editTextPhone.getText().toString().trim();
        String currentEmail = editTextEmail.getText().toString().trim();
        String currentStatus = "1";
        String createdBy = editTextCreatedBy.getText().toString().trim();
        String createdOn = getDateTime();
        String updatedBy = editTextCreatedBy.getText().toString().trim();
        String updatedOn = getDateTime();

        ContentValues values = new ContentValues();
        values.put(CustomerEntry.COLUMN_CUSTOMER_NAME, currentName);
        values.put(CustomerEntry.COLUMN_CUSTOMER_TYPE, currentType);
        values.put(CustomerEntry.COLUMN_CUSTOMER_CATEGORY, currentCategory);
        values.put(CustomerEntry.COLUMN_CUSTOMER_ADDRESS, currentAddress);
        values.put(CustomerEntry.COLUMN_CUSTOMER_AREA, currentArea);
        values.put(CustomerEntry.COLUMN_CUSTOMER_DISTRICT, currentDistrict);
        values.put(CustomerEntry.COLUMN_CUSTOMER_STATE, currentState);
        values.put(CustomerEntry.COLUMN_CUSTOMER_PHONE_NO, currentPhone);
        values.put(CustomerEntry.COLUMN_CUSTOMER_EMAIL_ID, currentEmail);
        values.put(CustomerEntry.COLUMN_CUSTOMER_STATUS, currentStatus);
        values.put(CustomerEntry.COLUMN_CUSTOMER_CREATED_BY, createdBy);
        values.put(CustomerEntry.COLUMN_CUSTOMER_CREATED_ON, createdOn);
        values.put(CustomerEntry.COLUMN_CUSTOMER_UPDATED_BY, updatedBy);
        values.put(CustomerEntry.COLUMN_CUSTOMER_UPDATED_ON, updatedOn);
        CustomerDbHelper helper = new CustomerDbHelper(this);
        helper.insertData(CustomerEntry.TABLE_NAME, values);
        Intent intent = new Intent(InsertCustomerActivity.this, InsertCustomerContactActivity.class);
        intent.putExtra("customerName", currentName);
        intent.putExtra("createdBy", createdBy);
        intent.putExtra("createdOn", createdOn);
        intent.putExtra("updatedBy", updatedBy);
        intent.putExtra("updatedOn", updatedOn);
        startActivity(intent);

    }


}

