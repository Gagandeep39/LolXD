package com.example.test.nuvoco3.customer;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.nuvoco3.R;
import com.example.test.nuvoco3.signup.ObjectSerializer;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

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

public class InsertComplaintActivity extends AppCompatActivity {
    private static final String TAG = "InsertComplaints Activity";
    private static final String URL_INSERT_COMPLAINT = "/insertComplaint";
    String mDate, mCustomerId, mCustomerName, mComplaintType, mComplaintDetails, mCreatedOn, mCreatedBy, mUpdatedOn, mUpdatedBy;
    SearchableSpinner mSearchType, mSearchCustomer;
    TextInputEditText mEditTextDate, mEditTextCustomerId, mEditTextComplaintDetails;
    int mYear, mMonth, mDay;
    ImageView imageViewCalendar;
    EditText editTextDate;
    RequestQueue queue;
    FloatingActionButton fab;
    String mCustomerArray[] = {"Customer 1", "Customer 2", "Customer 3"};
    String mTypeArray[] = {"Type 1", "Type 2"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        initializeViews();
        queue = Volley.newRequestQueue(this);
        populateSpinners();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

    }

    private void validateData() {
        mDate = mEditTextDate.getText().toString();
        mComplaintDetails = mEditTextComplaintDetails.getText().toString();
        mCustomerId = mEditTextCustomerId.getText().toString();
        mCreatedBy = getUserId();
        mCreatedOn = getDateTime();
        mUpdatedBy = getUserId();
        mUpdatedOn = getDateTime();

        if (TextUtils.isEmpty(mDate))
            mEditTextDate.setError("Enter Date");
        if (TextUtils.isEmpty(mComplaintDetails))
            mEditTextComplaintDetails.setError("Enter Details");
        if (TextUtils.isEmpty(mCustomerId))
            mEditTextCustomerId.setError("Enter Customer ID");
        if (TextUtils.equals(mCustomerName, getString(R.string.default_name)))
            Toast.makeText(this, "Select Customer", Toast.LENGTH_SHORT).show();
        if (TextUtils.equals(mComplaintType, getString(R.string.default_name)))
            Toast.makeText(this, "Select Complaint Type", Toast.LENGTH_SHORT).show();

        if (!TextUtils.isEmpty(mDate) && !TextUtils.isEmpty(mComplaintDetails) && !TextUtils.isEmpty(mCustomerId) && !TextUtils.equals(mCustomerName, getString(R.string.default_name)) && !TextUtils.equals(mComplaintType, getString(R.string.default_name)))
            storeDataToServer();
    }

    private void storeDataToServer() {


        Map<String, String> postParam = new HashMap<>();

//
        postParam.put("2", mDate);
        postParam.put("3", mCustomerId);
        postParam.put("4", mCustomerName);
        postParam.put("5", mComplaintType);
        postParam.put("6", mComplaintDetails);
        postParam.put("7", mCreatedOn);
        postParam.put("8", mCreatedBy);
        postParam.put("9", mUpdatedOn);
        postParam.put("10", mUpdatedBy);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, DATABASE_URL + URL_INSERT_COMPLAINT, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                        try {
                            if (response.getString("status").equals("updated")) {
                                Toast.makeText(InsertComplaintActivity.this, "Successfully Inserted Data", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(InsertComplaintActivity.this, InsertComplaintDetailsActivity.class);
                                intent.putExtra("date", mDate);
                                intent.putExtra("customerId", mCustomerId);
                                intent.putExtra("customerName", mCustomerName);
                                intent.putExtra("complaintDetails", mComplaintDetails);
                                intent.putExtra("complaintId", response.getString("complaint_ID"));
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error with Connection: " + error.getMessage() + "lol");
            }
        }) {

            /**
             * Passing some request headers
             * */
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


    private void populateSpinners() {
        ArrayAdapter<String> mCustomerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mCustomerArray);
        ArrayAdapter<String> mTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mTypeArray);
        mSearchCustomer.setAdapter(mCustomerAdapter);
        mSearchType.setAdapter(mTypeAdapter);
        mSearchCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCustomerName = mCustomerArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCustomerName = getString(R.string.default_name);
            }
        });
        mSearchType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mComplaintType = mTypeArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mComplaintType = getString(R.string.default_name);
            }
        });


    }

    private void initializeViews() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        fab = findViewById(R.id.fab);
        imageViewCalendar = findViewById(R.id.imageViewCalendar);
        mEditTextDate = findViewById(R.id.editTextDate);
        mEditTextCustomerId = findViewById(R.id.textInputEditCustomerId);
        mEditTextComplaintDetails = findViewById(R.id.editTextDetails);
        mSearchCustomer = findViewById(R.id.searchCustomer);
        mSearchType = findViewById(R.id.searchType);
        mEditTextDate.setKeyListener(null);
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


                        mEditTextDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //  Function to provide current data and time
    private String getDateTime() {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    // Get LoggedIn users ID
    private String getUserId() {
        ArrayList<String> newArralist = new ArrayList<>();
        // Creates a shared preferences variable to retrieve the logeed in users IDs and store it in Updated By Section
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.test.nuvoco3", Context.MODE_PRIVATE);

        try {
            newArralist = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("CustomerData", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (newArralist.size() > 0)
            return newArralist.get(6);

        return "Invalid User";

    }


}
