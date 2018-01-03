package com.example.test.nuvoco3.market;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.nuvoco3.MasterHelper;
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

public class InsertGeneralMarketActivity extends AppCompatActivity {
    public static final String URL_INSERT_GEN_MARKET = "/insertGen";
    private static final String TAG = "GeneralMarket Activity";
    String mRepresentative, mCounter, mDate, mCustomer, mMarketDetail, mMSP, mPrice, mDemand, mCreatedOn, mCreatedBy, mUpdatedOn, mUpdatedBy;
    TextInputEditText mEditTextRepresentative, mEditTextCounter, mEditTextMSP, mEditTextPrice;
    TextInputEditText mEditTextMarketDetails, mEditTextDemands;
    SearchableSpinner mSearchCustomer, mSearchProduct, mSearchBrand;
    RequestQueue queue;
    ArrayList<String> mCustomerList, mIdList;
    CoordinatorLayout mCoordinaterLayout;
    ProgressDialog progressDialog;
    MasterHelper mBrandHelper, mProductHelper;
    ArrayList<String> mBrandList, mProductList;
    ArrayAdapter mBrandAdapter, mProductAdapter;
    TextView mTextViewDate;
    int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_general_market);
        initializeViews();
        initializeVariables();
        populateCustomers();
        populateSpinner();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }

    private void initializeVariables() {

        progressDialog = new ProgressDialog(this);
        queue = Volley.newRequestQueue(this);

        mBrandHelper = new MasterHelper(this, "Brand");
        mProductHelper = new MasterHelper(this, "Product");

        mBrandList = mBrandHelper.getRecordName();
        mProductList = mProductHelper.getRecordName();
    }

    private void validateData() {
        mRepresentative = mEditTextRepresentative.getText().toString();
        mPrice = mEditTextPrice.getText().toString();
        mMarketDetail = mEditTextMarketDetails.getText().toString();
        mDemand = mEditTextDemands.getText().toString();
        mCreatedBy = getUserId();
        mCreatedOn = getDateTime();
        mUpdatedBy = getUserId();
        mUpdatedOn = getDateTime();
        if (TextUtils.isEmpty(mRepresentative)) {
            mEditTextRepresentative.setError("Enter Representative's Name");
        }
        if (TextUtils.isEmpty(mCounter)) {
            mEditTextCounter.setError("Enter Counter's Name");
        }
        if (TextUtils.isEmpty(mMSP)) {
            mEditTextMSP.setError("Enter MSP");
        }
        if (TextUtils.isEmpty(mPrice)) {
            mEditTextPrice.setError("Enter Price");
        }
        if (TextUtils.isEmpty(mMarketDetail)) {
            mEditTextMarketDetails.setError("Enter Market Details");
        }


        if (TextUtils.equals(mCustomer, "default"))
            Toast.makeText(this, "Select a Customer", Toast.LENGTH_SHORT).show();
        if (!TextUtils.isEmpty(mRepresentative) && !TextUtils.isEmpty(mCounter) && !TextUtils.isEmpty(mMSP) && !TextUtils.isEmpty(mPrice) && !TextUtils.isEmpty(mMarketDetail) && !TextUtils.isEmpty(mDemand) && !TextUtils.equals(mCustomer, "default"))
            sendDataToServer();
    }

    //Saves Data to Server
    private void sendDataToServer() {
        showValidationDialogue();

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
                                progressDialog.dismiss();
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

    //Shows Dialogue to Populate Customer


    //Shows dialogue to Send Data to server
    private void showValidationDialogue() {

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
                        validateData();
                    }
                });
                snackbar.show();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 20000);
    }

    //Adds data to Spinner
    private void populateSpinner() {
        ArrayAdapter<String> mCustomerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mCustomerList);
        mSearchCustomer.setAdapter(mCustomerAdapter);
        mSearchCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCustomer = mCustomerList.get(position);
//                mCustomerId = mIdList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCustomer = "default";
            }
        });

        //Master helper Spinner
        mBrandAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mBrandList);
        mProductAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mProductList);

        mSearchBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSearchProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mMSP = mProductList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mMSP = getString(R.string.default_name);

            }
        });
    }

    //Get References for Views
    private void initializeViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mEditTextRepresentative = findViewById(R.id.textInputEditRepresentative);
        mEditTextPrice = findViewById(R.id.textInputEditPrice);
        mEditTextMarketDetails = findViewById(R.id.editTextDetails);
        mCoordinaterLayout = findViewById(R.id.coordinator);
        mEditTextDemands = findViewById(R.id.editTextDemand);
        mSearchCustomer = findViewById(R.id.searchCategoryCustomer);
        mSearchProduct = findViewById(R.id.searchCategoryProduct);
        mSearchBrand = findViewById(R.id.searchCategoryBrand);
        mTextViewDate = findViewById(R.id.textViewDate);
        mEditTextRepresentative.setText(getUserId());
        mEditTextRepresentative.setKeyListener(null);
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
        ArrayAdapter<String> mCustomerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mCustomerList);

        mCustomerList = new ArrayList<String>();
        mIdList = new ArrayList<>();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                DATABASE_URL + "/dispCust", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = response.getJSONArray("message");
                    for (int i = 0; i < 50; i++) {

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


                        mTextViewDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        mDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }





}
