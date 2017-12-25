package com.example.test.nuvoco3.lead;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.nuvoco3.MainActivity;
import com.example.test.nuvoco3.R;
import com.example.test.nuvoco3.signup.ObjectSerializer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.test.nuvoco3.signup.LoginActivity.DATABASE_URL;

public class InsertCustomerContactActivity extends AppCompatActivity {
    private static final String TAG = "NUVOCO";
    TextInputEditText editTextContactName, editTextContactPhone, editTextContactEmail, editTextCustomerName, editTextCustomerId;
    TextView textViewDOB, textViewDOA;
    int mYear, mMonth, mDay;
    Bundle bundle;      //Data from another activity
    String mContactName, mCustomerName, mCreatedBy, mCreatedOn, mUpdatedBy, mUpdatedOn, mContactEmail, mContactDOB, mContactDOA, mContactPhone, mCustomerId;  //Customer=>Brand, CustomerContact=>Human
    RequestQueue queue;
    FloatingActionButton mInsertData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_customer_contact);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getReferences();

        queue = Volley.newRequestQueue(this);
        populatingDataFields();
    }

    // Populates the Customer ID and Customer Name when Redirected from New Customer Section
    private void populatingDataFields() {
        bundle = getIntent().getExtras();
        ArrayList<String> newArralist = new ArrayList<>();
        // Creates a shared preferences variable to retrieve the logeed in users IDs and store it in Updated By Section
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.test.nuvoco3", Context.MODE_PRIVATE);

        try {
            newArralist = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("CustomerData", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (getIntent().getStringExtra("customerName") != null) {
            mCustomerId = bundle.getString("customerId");
            mCustomerName = bundle.getString("customerName");
            mCreatedBy = bundle.getString("createdBy");
            mCreatedOn = bundle.getString("createdOn");
            editTextCustomerName.setText(mCustomerName);
            editTextCustomerId.setText(mCustomerId);
            editTextCustomerId.setKeyListener(null);
            editTextCustomerName.setKeyListener(null);

        } else {

            mCustomerName = editTextCustomerName.getText().toString();
            mCreatedBy = newArralist.get(6);
            mCreatedOn = getDateTime();
        }

        mUpdatedBy = newArralist.get(6);
        mUpdatedOn = getDateTime();
    }

    private void insertDataInFields() {

        mContactName = editTextContactName.getText().toString();
        mContactPhone = editTextContactPhone.getText().toString();
        mContactEmail = editTextContactEmail.getText().toString();
        mContactDOB = textViewDOB.getText().toString();
        mContactDOA = textViewDOA.getText().toString();
        mCustomerId = editTextCustomerId.getText().toString();
        storeData();
    }

    // initialize Views
    private void getReferences() {
        editTextContactName = findViewById(R.id.textInputEditContactName);
        editTextCustomerName = findViewById(R.id.textInputEditCustomerName);
        editTextContactPhone = findViewById(R.id.textInputEditContactPhone);
        editTextContactEmail = findViewById(R.id.textInputEditContactEmail);
        editTextCustomerId = findViewById(R.id.textInputEditCustomerId);
        textViewDOA = findViewById(R.id.textViewSelectDOA);
        textViewDOB = findViewById(R.id.textViewSelectDOB);
    }

    // Allows to select data when clicked on datapicker textView
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

                        if (buttonClicked.getId() == R.id.textViewSelectDOB)
                            textViewDOB.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        else if (buttonClicked.getId() == R.id.textViewSelectDOA)
                            textViewDOA.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    // Stores data in strings and calls storeData() to store dta in server
    public void insertData(View v) {
        insertDataInFields();
    }

    // Used to get current date and time
    private String getDateTime() {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    // Stores data in server
    private void storeData() {


        Map<String, String> postParam = new HashMap<>();


        postParam.put("2", mCustomerName);
        postParam.put("3", mContactName);
        postParam.put("4", mContactEmail);
        postParam.put("5", mContactPhone);
        postParam.put("6", mContactDOB);
        postParam.put("7", mContactDOA);
        postParam.put("8", mCreatedOn);
        postParam.put("9", mCreatedBy);
        postParam.put("10", mUpdatedOn);
        postParam.put("11", mUpdatedBy);
        postParam.put("12", mCustomerId);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, DATABASE_URL + "/insertCon", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                        try {
                            if (response.getString("status").equals("updated")) {
                                Toast.makeText(InsertCustomerContactActivity.this, "Successfully Inserted New Contact", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(InsertCustomerContactActivity.this, MainActivity.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error with Connection: " + error.getMessage());
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }


        };

        jsonObjReq.setTag("LOL");
        // Adding request to request queue
        queue.add(jsonObjReq);

    }


}
