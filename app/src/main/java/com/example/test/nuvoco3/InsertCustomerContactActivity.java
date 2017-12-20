package com.example.test.nuvoco3;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import com.example.test.nuvoco3.customerdata.CustomerContract.CustomerEntry;
import com.example.test.nuvoco3.customerdata.CustomerDbHelper;

import java.util.Calendar;

public class InsertCustomerContactActivity extends AppCompatActivity {
    TextInputEditText editTextContactName, editTextContactPhone, editTextContactEmail;
    TextView textViewDOB, textViewDOA;
    int mYear, mMonth, mDay;
    Bundle bundle;      //Data from another activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_customer_contact);
        getReferences();
        bundle = getIntent().getExtras();
        editTextContactName.setText(bundle.getString("customerName"));
    }

    private void insertData() {
        String customerName = bundle.getString("customerName");
        String customerContactName = editTextContactName.getText().toString();
        String customerContactPhone = editTextContactPhone.getText().toString();
        String customerContactEmail = editTextContactEmail.getText().toString();
        String customerContactDOB = textViewDOB.getText().toString();
        String customerContactDOA = textViewDOA.getText().toString();
        String createdBy = bundle.getString("createdBy");
        String createdOn = bundle.getString("createdOn");
        String updatedBy = bundle.getString("updatedBy");
        String updatedOn = bundle.getString("updatedOn");

        ContentValues contentValues = new ContentValues();
        contentValues.put(CustomerEntry.COLUMN_CUSTOMER_NAME, customerName);
        contentValues.put(CustomerEntry.COLUMN_CUSTOMER_CONTACT_NAME, customerContactName);
        contentValues.put(CustomerEntry.COLUMN_CUSTOMER_CONTACT_PHONE_NO, customerContactPhone);
        contentValues.put(CustomerEntry.COLUMN_CUSTOMER_CONTACT_EMAIL_ID, customerContactEmail);
        contentValues.put(CustomerEntry.COLUMN_CUSTOMER_CONTACT_DOB, customerContactDOB);
        contentValues.put(CustomerEntry.COLUMN_CUSTOMER_CONTACT_DOA, customerContactDOA);
        contentValues.put(CustomerEntry.COLUMN_CUSTOMER_CREATED_BY, createdBy);
        contentValues.put(CustomerEntry.COLUMN_CUSTOMER_CREATED_ON, createdOn);
        contentValues.put(CustomerEntry.COLUMN_CUSTOMER_UPDATED_BY, updatedBy);
        contentValues.put(CustomerEntry.COLUMN_CUSTOMER_UPDATED_ON, updatedOn);
        CustomerDbHelper helper = new CustomerDbHelper(this);
        helper.insertData(CustomerEntry.TABLE_NAME_CONTACT, contentValues);
    }

    private void getReferences() {
        editTextContactName = findViewById(R.id.textInputEditEmployName);
        editTextContactPhone = findViewById(R.id.textInputEditEmployPhone);
        editTextContactEmail = findViewById(R.id.textInputEditEmployEmail);
        textViewDOA = findViewById(R.id.textViewSelectDOA);
        textViewDOB = findViewById(R.id.textViewSelectDOB);
    }

    public void datePickerFunction(View v) {
        final View buttonClicked = v;

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        if (buttonClicked.getId()==R.id.textViewSelectDOB)
                            textViewDOB.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        else if (buttonClicked.getId()==R.id.textViewSelectDOA)
                            textViewDOA.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void insertData(View v){

        insertData();
        finish();
        startActivity(new Intent(InsertCustomerContactActivity.this, MainActivity.class));
    }


}
