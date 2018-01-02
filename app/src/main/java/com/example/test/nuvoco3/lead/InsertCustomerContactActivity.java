package com.example.test.nuvoco3.lead;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONArray;
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
    private static final String TAG = "InsertContacts";
    TextInputEditText editTextContactName, editTextContactPhone, editTextContactEmail, editTextCustomerName, editTextCustomerId;
    TextView textViewDOB, textViewDOA;
    int mYear, mMonth, mDay;
    Bundle bundle;      //Data from another activity
    String mContactName, mCustomerName, mCreatedBy, mCreatedOn, mUpdatedBy, mUpdatedOn, mContactEmail, mContactDOB, mContactDOA, mContactPhone, mCustomerId;  //Customer=>Brand, CustomerContact=>Human
    RequestQueue queue;
    SearchableSpinner mSearchCustomer;
    LinearLayout mLinearLayout;
    ArrayList<String> mCustomerList, mIdList;
    CoordinatorLayout mCoordinaterLayout;
    ProgressDialog progressDialog;
    FloatingActionButton fab;
    int size = 50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_customer_contact);
        getReferences();
        queue = Volley.newRequestQueue(this);
        populateCustomers();
        populatingSpinner();
        populatingDataFields();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
    }

    private void populatingSpinner() {
        ArrayAdapter<String> mCustomerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mCustomerList);

        mSearchCustomer.setAdapter(mCustomerAdapter);
        mCustomerAdapter.notifyDataSetChanged();
        mSearchCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCustomerName = mCustomerList.get(position);
                mCustomerId = mIdList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCustomerName = getString(R.string.default_name);
            }
        });
    }

    // Populates the Customer ID and Customer Name when Redirected from New Customer Section
    private void populatingDataFields() {
        bundle = getIntent().getExtras();
        if (getIntent().getStringExtra("customerName") != null) {
            mCustomerId = bundle.getString("customerId");
            mCustomerName = bundle.getString("customerName");

            editTextCustomerName.setText(mCustomerName);
            editTextCustomerName.setKeyListener(null);
            editTextCustomerName.getBackground().mutate().setColorFilter(getResources().getColor(R.color.light_grey), PorterDuff.Mode.SRC_ATOP);

        }

        mCreatedBy = getUserId();
        mCreatedOn = getDateTime();
        mUpdatedBy = getUserId();
        mUpdatedOn = getDateTime();
    }

    private void insertDataInFields() {

        mContactName = editTextContactName.getText().toString();
        mContactPhone = editTextContactPhone.getText().toString();
        mContactEmail = editTextContactEmail.getText().toString();
        mContactDOB = textViewDOB.getText().toString();
        mContactDOA = textViewDOA.getText().toString();
        if (TextUtils.isEmpty(mContactName)) {
            editTextContactName.setError("Enter Contact's Name");
        }
        if (TextUtils.isEmpty(mContactPhone)) {
            editTextContactPhone.setError("Enter Contact's Phone Number");
        }
        if (TextUtils.isEmpty(mContactEmail)) {
            editTextContactEmail.setError("Enter Contact's Email ID");
        } else {
            if (!isEmailValid(mContactEmail))
                editTextContactEmail.setError("Enter a Valid Email ID");
        }
        if (TextUtils.isEmpty(mContactDOB)) {
            textViewDOB.setError("Enter Contact's Date of Birth");
        }
        if (TextUtils.isEmpty(mContactDOA)) {
            textViewDOA.setError("Enter Contact's Date of Anniversary");
        }

        if (!TextUtils.isEmpty(mContactName) && !TextUtils.isEmpty(mContactPhone) && !TextUtils.isEmpty(mContactEmail) && !TextUtils.isEmpty(mContactDOB) && !TextUtils.isEmpty(mContactDOA) && !TextUtils.isEmpty(mCustomerId) && !TextUtils.isEmpty(mCustomerName) && isEmailValid(mContactEmail))
            storeData();
    }

    // initialize Views
    private void getReferences() {
        editTextContactName = findViewById(R.id.textInputEditContactName);
        editTextCustomerName = findViewById(R.id.textInputEditCustomerName);
        editTextContactPhone = findViewById(R.id.textInputEditContactPhone);
        editTextContactEmail = findViewById(R.id.textInputEditContactEmail);
        fab = findViewById(R.id.fabAddData);
        textViewDOA = findViewById(R.id.textViewSelectDOA);
        textViewDOB = findViewById(R.id.textViewSelectDOB);
        mSearchCustomer = findViewById(R.id.searchCustomer);
        mLinearLayout = findViewById(R.id.linearLayout);
        mCoordinaterLayout = findViewById(R.id.coordinator);

        //Set Up Toolbar and Back Button
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        progressDialog = new ProgressDialog(this);

        if (getIntent().getStringExtra("needSearch") != null && getIntent().getStringExtra("needSearch").equals("Need")) {
            editTextCustomerName.setVisibility(View.GONE);
            mLinearLayout.setVisibility(View.VISIBLE);
        }
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
    public void insertData() {
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
        showProgress();



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
                                progressDialog.dismiss();
                                Toast.makeText(InsertCustomerContactActivity.this, "Successfully Inserted New Contact", Toast.LENGTH_SHORT).show();
                                finish();
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
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }


        };

        jsonObjReq.setTag("LOL");
        // Adding request to request queue
        queue.add(jsonObjReq);

    }

    private void showProgress() {
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                Snackbar snackbar = Snackbar.make(mCoordinaterLayout, "Connection Time-out !", Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        insertData();
                    }
                });
                snackbar.show();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 20000);
    }


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void populateCustomers() {
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                Snackbar snackbar = Snackbar.make(mCoordinaterLayout, "Connection Time-out !", Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        populateCustomers();
                    }
                });
                snackbar.show();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 20000);

        mCustomerList = new ArrayList<String>();
        mIdList = new ArrayList<>();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                DATABASE_URL + "/dispCust", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = response.getJSONArray("message");

                    if (mCustomerName.equals("0"))
                        size = 50;
                    else
                        size = jsonArray.length();


                    for (int i = 0; i < size; i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        mIdList.add(object.getString("record_id"));   //primary key
                        mCustomerList.add(object.getString("name"));
                    }


                } catch (JSONException e1) {
                    e1.printStackTrace();
                    e1.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("lol", "Error with Internet : " + error.getMessage());
                // hide the progress dialog
            }
        });

        // Adding request to request queue
        queue.add(jsonObjReq);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        progressDialog.dismiss();
    }


    private boolean isEmailValid(String email) {
        return email.contains("@");
    }



}
