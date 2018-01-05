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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.example.test.nuvoco3.helpers.MasterHelper;
import com.example.test.nuvoco3.helpers.UserInfoHelper;
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

import static com.example.test.nuvoco3.helpers.Contract.BASE_URL;
import static com.example.test.nuvoco3.helpers.Contract.DISPLAY_CUSTOMER;
import static com.example.test.nuvoco3.helpers.Contract.INSERT_PRICES;

public class InsertBrandPriceActivity extends AppCompatActivity {
    private static final String TAG = "BrandPrice Activity";
    String mRepresentative, mCounter, mDate, mBrand, mProduct, mWSP, mRSP, mStock, mRemarks, mCreatedBy, mCreatedOn, mUpdatedBy, mUpdatedOn;
    SearchableSpinner mSearchProduct, mSearchBrand, mSearchCustomers;
    TextInputEditText mEditTextRepresentative, mEditTextCounter, mEditTextWSP, mEditTextRSP, mEditTextStocks, mUserName;
    EditText mEditTextRemarks;
    RequestQueue queue;
    ArrayList<String> mBrandArrayList, mProductArrayList, mCustomerArrayList;
    CoordinatorLayout mCoordinaterLayout;
    ProgressDialog progressDialog;
    MasterHelper mBrandHelper, mProductHelper;
    boolean isChecked = false;

    //Date picker
    TextView mTextViewDate;
    //Displaying recently Added items
    RecyclerView mRecyclerView;
    BrandPriceDetailsAdapter mDetailsAdapter;
    ArrayList<BrandPrice> mBrandPricePriceList;
    int mDay, mYear, mMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_price);
        initializeViews();
        initializeVariables();
        initializeBottomViews();
        populateSpinners();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateDate();
            }
        });

    }

    private void initializeBottomViews() {
        mDetailsAdapter = new BrandPriceDetailsAdapter(this, mBrandPricePriceList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mDetailsAdapter);
    }

    private void initializeVariables() {
        queue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);
        mBrandArrayList = new ArrayList<>();
        mProductArrayList = new ArrayList<>();
        mCustomerArrayList = new ArrayList<>();
        mBrandPricePriceList = new ArrayList<>();

        mBrandHelper = new MasterHelper(this, "Brand");
        mProductHelper = new MasterHelper(this, "Product");

        mBrandArrayList = mBrandHelper.getRecordName();
        mProductArrayList = mProductHelper.getRecordName();

        populateCustomers();


    }

    //Stores data and variables and checks if Something is left empty or not
    private void validateDate() {
        mRepresentative = mEditTextRepresentative.getText().toString();
        mWSP = mEditTextWSP.getText().toString();
        mRSP = mEditTextRSP.getText().toString();
        mStock = mEditTextStocks.getText().toString();
        mRemarks = mEditTextRemarks.getText().toString();
        mCreatedBy = getUserId();
        mCreatedOn = getDateTime();
        mUpdatedBy = getUserId();
        mUpdatedOn = getDateTime();

        if (TextUtils.isEmpty(mRepresentative))
            Toast.makeText(this, "Enter Representative's ID", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(mCounter))
            mEditTextCounter.setError("Enter Counter's name");
        if (TextUtils.isEmpty(mWSP))
            mEditTextWSP.setError("Enter WSP");
        if (TextUtils.isEmpty(mRSP))
            mEditTextRSP.setError("Enter RSP");
        if (TextUtils.isEmpty(mStock))
            mEditTextStocks.setError("Stocks cannot be Empty");
        if (TextUtils.isEmpty(mRemarks))
            mEditTextRemarks.setError("Remarks Field cannot be Empty");
        if (TextUtils.isEmpty(mDate))
            Toast.makeText(this, "Select Date", Toast.LENGTH_SHORT).show();


        if (TextUtils.equals(mProduct, "default"))
            Toast.makeText(this, "Select a Product", Toast.LENGTH_SHORT).show();
        if (TextUtils.equals(mBrand, "default"))
            Toast.makeText(this, "Select a Brand", Toast.LENGTH_SHORT).show();
        if (!TextUtils.isEmpty(mRepresentative) && !TextUtils.isEmpty(mCounter) && !TextUtils.isEmpty(mWSP) && !TextUtils.isEmpty(mRSP) && !TextUtils.isEmpty(mStock) && !TextUtils.isEmpty(mRemarks) && !TextUtils.equals(mProduct, "default") && !TextUtils.equals(mBrand, "default") && !TextUtils.isEmpty(mDate))
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
                        validateDate();
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

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, BASE_URL + INSERT_PRICES, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                        try {
                            if (response.getString("status").equals("updated")) {
                                progressDialog.dismiss();
                                Toast.makeText(InsertBrandPriceActivity.this, "Successfully Inserted Data", Toast.LENGTH_SHORT).show();
                                mBrandPricePriceList.add(new BrandPrice("1", mRepresentative, mCounter, mDate, mBrand, mProduct, mWSP, mRSP, mStock, mRemarks, mCreatedOn, mCreatedBy, mUpdatedOn, mUpdatedBy));
                                mDetailsAdapter.notifyDataSetChanged();

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
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("x-access-token", new UserInfoHelper(InsertBrandPriceActivity.this).getUserToken());
                return headers;
            }


        };

        jsonObjReq.setTag("LOL");
        // Adding request to request queue
        queue.add(jsonObjReq);

    }


    // Populates Spinner with Data and allows Selection of Items
    private void populateSpinners() {

        ArrayAdapter<String> mProductAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mProductArrayList);
        ArrayAdapter<String> mBrandAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mBrandArrayList);
        ArrayAdapter<String> mCustomerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mCustomerArrayList);

        progressDialog.dismiss();
        mSearchProduct.setAdapter(mProductAdapter);
        mSearchBrand.setAdapter(mBrandAdapter);
        mSearchCustomers.setAdapter(mCustomerAdapter);

        mSearchBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mBrand = mBrandArrayList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mBrand = "default";

            }
        });
        mSearchProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mProduct = mProductArrayList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mProduct = "default";

            }
        });
        mSearchCustomers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mCounter = mCustomerArrayList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mCounter = getString(R.string.default_name);
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
        mEditTextWSP = findViewById(R.id.textInputEditWSP);
        mEditTextRSP = findViewById(R.id.textInputEditRSP);
        mEditTextStocks = findViewById(R.id.textInputEditStocks);
        mEditTextRemarks = findViewById(R.id.editTextRemark);
        mSearchBrand = findViewById(R.id.searchCategoryBrand);
        mCoordinaterLayout = findViewById(R.id.coordinator);
        mSearchProduct = findViewById(R.id.searchCategoryProduct);
        mUserName = findViewById(R.id.textInputEditName);
        mSearchCustomers = findViewById(R.id.searchCounter);
        mTextViewDate = findViewById(R.id.textViewDate);
        mRecyclerView = findViewById(R.id.recyclerView);
        mEditTextRepresentative.setText(getUserId());
        mEditTextRepresentative.setKeyListener(null);
        mUserName.setText(getUserName());
        mUserName.setKeyListener(null);
    }


    //  Function to provide current data and time
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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

    private String getUserName() {
        ArrayList<String> newArralist = new ArrayList<>();
        // Creates a shared preferences variable to retrieve the logeed in users IDs and store it in Updated By Section
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.test.nuvoco3", Context.MODE_PRIVATE);

        try {
            newArralist = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("CustomerData", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (newArralist.size() > 0)
            return newArralist.get(7);

        return "Invalid User";

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


                        mTextViewDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date();

                        mDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + " " + dateFormat.format(date);
                        Log.i(TAG, "onDateSet: " + mDate);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

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


        Map<String, String> postParam = new HashMap<>();


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
//                                Log.i(TAG, "onResponse: " + "created by onlu" + isChecked);
                                mCustomerArrayList.add(object.getString("name"));

                            }


                        } else {
                            mCustomerArrayList.add(object.getString("name"));

                        }
//                        mIdList.add(object.getString("record_id"));   //primary key
                    }
                    progressDialog.dismiss();


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
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("x-access-token", new UserInfoHelper(InsertBrandPriceActivity.this).getUserToken());
                return headers;
            }


        };

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
            mCustomerArrayList.clear();
            populateCustomers();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }





}
