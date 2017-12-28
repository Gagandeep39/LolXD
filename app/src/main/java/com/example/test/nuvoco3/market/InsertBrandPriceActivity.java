package com.example.test.nuvoco3.market;

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

public class InsertBrandPriceActivity extends AppCompatActivity {
    public static final String URL_INSERT_MARKET_PRICE = "/insertR";
    public static final String URL_BRAND_INFO = "/dispBrand";
    private static final String TAG = "BrandPrice Activity";
    String mRecordId, mRepresentative, mCounter, mDate, mBrand, mProduct, mWSP, mRSP, mStock, mRemarks, mCreatedBy, mCreatedOn, mUpdatedBy, mUpdatedOn;
    SearchableSpinner mSearchProduct, mSearchBrand;
    TextInputEditText mEditTextRepresentative, mEditTextCounter, mEditTextWSP, mEditTextRSP, mEditTextStocks;
    EditText mEditTextRemarks;
    String mBrandArray[] = {"Brand 1", "Brand 2", "Brand 3"};
    String mProductArray[] = {"Product 1", "Product 2", "Product 3"};
    RequestQueue queue;
    ArrayList<String> mBrandArrayList, mProductArrayList;
    CoordinatorLayout mCoordinaterLayout;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_price);
        initializeViews();
        // Initialize Queue
        progressDialog = new ProgressDialog(this);
        mBrandArrayList = new ArrayList<>();
        mProductArrayList = new ArrayList<>();

        queue = Volley.newRequestQueue(this);
        populateSpinners();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateDate();
            }
        });

    }

    //Stores data and variables and checks if Something is left empty or not
    private void validateDate() {
        mRepresentative = mEditTextRepresentative.getText().toString();
        mCounter = mEditTextCounter.getText().toString();
        mWSP = mEditTextWSP.getText().toString();
        mRSP = mEditTextRSP.getText().toString();
        mStock = mEditTextStocks.getText().toString();
        mRemarks = mEditTextRemarks.getText().toString();
        mDate = getDateTime();
        mCreatedBy = getUserId();
        mCreatedOn = getDateTime();
        mUpdatedBy = getUserId();
        mUpdatedOn = getDateTime();

        if (TextUtils.isEmpty(mRepresentative))
            Toast.makeText(this, "Enter Representative's ID", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(mCounter))
            Toast.makeText(this, "Enter Counter's Name", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(mWSP))
            Toast.makeText(this, "Enter WSP", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(mRSP))
            Toast.makeText(this, "Enter RSP", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(mStock))
            Toast.makeText(this, "Stocks cannot be Empty", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(mRemarks))
            Toast.makeText(this, "Remarks Field cannot be Empty", Toast.LENGTH_SHORT).show();
        if (TextUtils.equals(mProduct, "default"))
            Toast.makeText(this, "Select a Product", Toast.LENGTH_SHORT).show();
        if (TextUtils.equals(mBrand, "default"))
            Toast.makeText(this, "Select a Brand", Toast.LENGTH_SHORT).show();
        if (!TextUtils.isEmpty(mRepresentative) && !TextUtils.isEmpty(mCounter) && !TextUtils.isEmpty(mWSP) && !TextUtils.isEmpty(mRSP) && !TextUtils.isEmpty(mStock) && !TextUtils.isEmpty(mRemarks) && !TextUtils.equals(mProduct, "default") && !TextUtils.equals(mBrand, "default"))
            sendDataToServer();

    }

    // All fields are Validated and now the data will be saved into the server
    private void sendDataToServer() {
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
                    }
                });
                snackbar.show();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 20000);


        Map<String, String> postParam = new HashMap<>();

//
        postParam.put("2", mRepresentative);
        postParam.put("3", mCounter);
        postParam.put("4", mDate);
        postParam.put("5", mBrand);
        postParam.put("6", mWSP);
        postParam.put("7", mRSP);
        postParam.put("8", mStock);
        postParam.put("9", mRemarks);
        postParam.put("10", mCreatedOn);
        postParam.put("11", mCreatedBy);
        postParam.put("12", mUpdatedOn);
        postParam.put("13", mUpdatedBy);
        postParam.put("14", mProduct);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, DATABASE_URL + URL_INSERT_MARKET_PRICE, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                        try {
                            if (response.getString("status").equals("updated")) {
                                progressDialog.dismiss();
                                Toast.makeText(InsertBrandPriceActivity.this, "Successfully Inserted Data", Toast.LENGTH_SHORT).show();
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


    // Populates Spinner with Data and allows Selection of Items
    private void populateSpinners() {

        ArrayAdapter<String> mProductAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mProductArray);
        ArrayAdapter<String> mBrandAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mBrandArray);
        mSearchProduct.setAdapter(mProductAdapter);
        mSearchBrand.setAdapter(mBrandAdapter);
        mSearchBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mBrand = mBrandArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mBrand = "default";

            }
        });
        mSearchProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mProduct = mProductArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mProduct = "default";

            }
        });

    }

    //Initialize Widgets on the Screen
    private void initializeViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mEditTextRepresentative = findViewById(R.id.textInputEditRepresentative);
        mEditTextCounter = findViewById(R.id.textInputEditCounter);
        mEditTextWSP = findViewById(R.id.textInputEditWSP);
        mEditTextRSP = findViewById(R.id.textInputEditRSP);
        mEditTextStocks = findViewById(R.id.textInputEditStocks);
        mEditTextRemarks = findViewById(R.id.editTextRemark);
        mSearchBrand = findViewById(R.id.searchCategoryBrand);
        mCoordinaterLayout = findViewById(R.id.coordinator);
        mSearchProduct = findViewById(R.id.searchCategoryProduct);
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


}
