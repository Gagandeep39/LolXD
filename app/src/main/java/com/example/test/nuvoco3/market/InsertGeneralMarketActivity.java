package com.example.test.nuvoco3.market;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.test.nuvoco3.signup.LoginActivity.DATABASE_URL;

public class InsertGeneralMarketActivity extends AppCompatActivity {
    public static final String URL_INSERT_GEN_MARKET = "/insertGen";
    private static final String TAG = "InsertGeneralMarket Activity";
    String mRepresentative, mCounter, mDate, mCustomer, mMarketDetail, mMSP, mPrice, mDemand, mCreatedOn, mCreatedBy, mUpdatedOn, mUpdatedBy;
    TextInputEditText mEditTextRepresentative, mEditTextCounter, mEditTextMSP, mEditTextPrice;
    EditText mEditTextMarketDetails, mEditTextDemands;
    SearchableSpinner mSearchCustomer;
    String mCustomerArray[] = {"Customer 1", "Customer 2", "Customer 3"};
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_general_market);
        initializeViews();
        queue = Volley.newRequestQueue(this);
        populateSpinner();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }

    private void validateData() {
        mRepresentative = mEditTextRepresentative.getText().toString();
        mCounter = mEditTextCounter.getText().toString();
        mPrice = mEditTextPrice.getText().toString();
        mMSP = mEditTextMSP.getText().toString();
        mMarketDetail = mEditTextMarketDetails.getText().toString();
        mDemand = mEditTextDemands.getText().toString();
        mDate = getDateTime();
        mCreatedBy = getUserId();
        mCreatedOn = getDateTime();
        mUpdatedBy = getUserId();
        mUpdatedOn = getDateTime();
        if (TextUtils.isEmpty(mRepresentative))
            Toast.makeText(this, "Enter Representative's ID", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(mCounter))
            Toast.makeText(this, "Enter Counter's Name", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(mMSP))
            Toast.makeText(this, "Enter MSP", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(mPrice))
            Toast.makeText(this, "Enter Price", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(mMarketDetail))
            Toast.makeText(this, "Enter Market Details", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(mDemand))
            Toast.makeText(this, "Details Field cannot be Empty", Toast.LENGTH_SHORT).show();
        if (TextUtils.equals(mCustomer, "default"))
            Toast.makeText(this, "Select a Customer", Toast.LENGTH_SHORT).show();
        if (!TextUtils.isEmpty(mRepresentative) && !TextUtils.isEmpty(mCounter) && !TextUtils.isEmpty(mMSP) && !TextUtils.isEmpty(mPrice) && !TextUtils.isEmpty(mMarketDetail) && !TextUtils.isEmpty(mDemand) && !TextUtils.equals(mCustomer, "default"))
            sendDataToServer();
    }

    private void sendDataToServer() {

        Map<String, String> postParam = new HashMap<>();

//
        postParam.put("2", mRepresentative);
        postParam.put("3", mCounter);
        postParam.put("4", mDate);
        postParam.put("5", mCustomer);
        postParam.put("6", mMarketDetail);
        postParam.put("7", mMSP);
        postParam.put("8", mPrice);
        postParam.put("9", mDemand);
        postParam.put("10", mCreatedOn);
        postParam.put("11", mCreatedBy);
        postParam.put("12", mUpdatedOn);
        postParam.put("13", mUpdatedBy);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, DATABASE_URL + URL_INSERT_GEN_MARKET, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                        try {
                            if (response.getString("status").equals("updated")) {
                                Toast.makeText(InsertGeneralMarketActivity.this, "Successfully Inserted Data", Toast.LENGTH_SHORT).show();
                                finish();

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

    private void populateSpinner() {
        ArrayAdapter mCustomerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mCustomerArray);
        mSearchCustomer.setAdapter(mCustomerAdapter);
        mSearchCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCustomer = mCustomerArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCustomer = "default";
            }
        });
    }

    private void initializeViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mEditTextRepresentative = findViewById(R.id.textInputEditRepresentative);
        mEditTextCounter = findViewById(R.id.textInputEditCounter);
        mEditTextMSP = findViewById(R.id.textInputEditMSP);
        mEditTextPrice = findViewById(R.id.textInputEditPrice);
        mEditTextMarketDetails = findViewById(R.id.editTextDetails);
        mEditTextDemands = findViewById(R.id.editTextDemand);
        mSearchCustomer = findViewById(R.id.searchCategoryBrand);
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
        return newArralist.get(6);
    }
}
