package com.example.test.nuvoco3.visits;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
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

import static com.example.test.nuvoco3.helpers.Contract.BASE_URL;
import static com.example.test.nuvoco3.helpers.Contract.DISPLAY_CUSTOMER;

public class CreateJCPActivity extends AppCompatActivity {
    private static final String TAG = "CreateJCP Activity";
    int mYear, mMonth, mDay, mHour, mMinute;
    TextView mTextViewStartTime, mTextViewEndTime, mTextViewDate;
    FloatingActionButton fab;
    TextInputEditText mEditTextRepresentative, mEditTextObjective;
    String mCustomerId, mCustomerName, mDate, mStartTime, mEndTime, mObjective, mCreatedOn, mCreatedBy, mUpdatedOn, mUpdatedBy;
    SearchableSpinner mCustomerSpinner;
    ArrayList<String> mCustomerList, mIdList;
    ArrayAdapter mCustomerAdapter;
    RequestQueue queue;
    boolean isChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_jcp);
        initializeViews();
        initializeVariables();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }

    private void validateData() {
        mObjective = mEditTextObjective.getText().toString();
        mCreatedBy = getUserId();
        mCreatedOn = getDateTime();
        mUpdatedBy = getUserId();
        mUpdatedOn = getDateTime();

        if (TextUtils.isEmpty(mDate))
            Toast.makeText(this, "Select Date", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(mStartTime))
            Toast.makeText(this, "Select Start Time", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(mEndTime))
            Toast.makeText(this, "Select End Time", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(mObjective))
            mEditTextObjective.setError("Enter Objective");
        if (TextUtils.equals(mCustomerName, getString(R.string.default_name)))
            Toast.makeText(this, "Select Customer", Toast.LENGTH_SHORT).show();

        if (!TextUtils.isEmpty(mDate) && !TextUtils.isEmpty(mStartTime) && !TextUtils.isEmpty(mEndTime) && !TextUtils.isEmpty(mDate) && !TextUtils.equals(mCustomerName, getString(R.string.default_name)))
            sendDataToServer();

    }

    private void sendDataToServer() {
    }

    private void populateSpinners() {
        mCustomerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mCustomerList);

        mCustomerSpinner.setAdapter(mCustomerAdapter);
        mCustomerAdapter.notifyDataSetChanged();
        mCustomerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mCustomerName = mCustomerList.get(i);
                mCustomerId = mIdList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mCustomerName = getString(R.string.default_name);

            }
        });
    }

    private void initializeVariables() {
        mCustomerList = new ArrayList();
        mIdList = new ArrayList();
        queue = Volley.newRequestQueue(this);
        mEditTextRepresentative.setText(getUserId());
        populateCustomers();

    }

    private void initializeViews() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        fab = findViewById(R.id.fab);
        mTextViewDate = findViewById(R.id.textViewDate);
        mTextViewEndTime = findViewById(R.id.textViewEndTime);
        mTextViewStartTime = findViewById(R.id.textViewStartTime);
        mEditTextRepresentative = findViewById(R.id.textInputEditTextRepresentative);
//        mEditTextObjective = findViewById(R.id.textInputEditTextObjective);
        mCustomerSpinner = findViewById(R.id.searchCustomer);
    }

    public void timePickerFunction(final View v) {

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        if (v.getId() == R.id.textViewStartTime) {
                            mStartTime = hourOfDay + ":" + minute;
                            mTextViewStartTime.setText(hourOfDay + ":" + minute);
                        }
                        if (v.getId() == R.id.textViewEndTime) {
                            mEndTime = hourOfDay + ":" + minute;
                            mTextViewEndTime.setText(hourOfDay + ":" + minute);
                        }
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
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


    public void datePickerFunction(View v) {

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


                        mTextViewDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date();

                        mDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + " " + dateFormat.format(date);


                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    //  Function to provide current data and time
    private String getDateTime() {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }


    public void populateCustomers() {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                BASE_URL + DISPLAY_CUSTOMER, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("message");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        if (isChecked) {
                            if (object.getString("createdBy").equals(getUserId())) {
                                mCustomerList.add(object.getString("name"));
                                mIdList.add(object.getString("record_id"));

                            }


                        } else {
                            mCustomerList.add(object.getString("name"));
                            mIdList.add(object.getString("record_id"));

                        }
                    }
                    populateSpinners();
                    Log.i(TAG, "onResponse: " + mCustomerList.size());

                } catch (JSONException e1) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_customer_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem checkable = menu.findItem(R.id.checkable_menu);
        checkable.setChecked(isChecked);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.checkable_menu) {
            isChecked = !item.isChecked();
            item.setChecked(isChecked);
            mCustomerList.clear();
            populateCustomers();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




}
