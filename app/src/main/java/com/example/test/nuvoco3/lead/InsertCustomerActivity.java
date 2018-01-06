
package com.example.test.nuvoco3.lead;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.test.nuvoco3.helpers.CalendarHelper.getDateTime;
import static com.example.test.nuvoco3.helpers.Contract.BASE_URL;
import static com.example.test.nuvoco3.helpers.Contract.INSERT_CUSTOMER;
import static com.example.test.nuvoco3.helpers.Contract.PROGRESS_DIALOG_DURATION;
import static com.example.test.nuvoco3.helpers.UserInfoHelper.isEmailValid;

public class InsertCustomerActivity extends AppCompatActivity {
    public static final String TAG = "InsertCustomer Activity";
    SearchableSpinner searchableSpinnerCategory, searchableSpinnerDistrict, searchableSpinnerArea, searchableSpinnerState, searchableSpinnerType;
    FloatingActionButton floatingActionButtonAddData;
    TextInputEditText editTextName, editTextAddress, editTextPhone, editTextEmail;
    String mName, mType, mCategory, mAddress, mArea, mDistrict, mState, mPhone, mEmail, mStatus, mCreatedOn, mCreatedBy, mUpdatedOn, mUpdatedBy;
    RequestQueue queue;
    public CoordinatorLayout mCoordinaterLayout;
    ProgressDialog progressDialog;

    //Master Helper
    MasterHelper mTypeHelper, mCategoryHelper, mAreaHelper, mDistrictHelper, mStateHelper;
    ArrayList<String> mTypeArray, mCategoryArray, mAreaArray, mDistrictArray, mStateArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_customer);


        initializeViews();
        initializeVariables();
        initializeSpinners();
        floatingActionButtonAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }

    private void initializeVariables() {

        queue = Volley.newRequestQueue(this);

        //initialize Helpers
        mAreaHelper = new MasterHelper(this, "Area");
        mStateHelper = new MasterHelper(this, "State");
        mTypeHelper = new MasterHelper(this, "CustomerType");
        mCategoryHelper = new MasterHelper(this, "Category");
        mDistrictHelper = new MasterHelper(this, "District");

        //initialize Arrays

        mAreaArray = mAreaHelper.getRecordName();
        mStateArray = mStateHelper.getRecordName();
        mTypeArray = mTypeHelper.getRecordName();
        mCategoryArray = mCategoryHelper.getRecordName();
        mDistrictArray = mDistrictHelper.getRecordName();
    }

    //  Populates the spinners
    private void initializeSpinners() {
        ArrayAdapter mAreaAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mAreaArray);
        ArrayAdapter mStateAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mStateArray);
//        ArrayAdapter mTypeAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mTypeArray);
        ArrayAdapter mCategoryAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mCategoryArray);
        ArrayAdapter mDistrictAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mDistrictArray);

        //Seting Adapter
        searchableSpinnerArea.setAdapter(mAreaAdapter);
//        searchableSpinnerType.setAdapter(mTypeAdapter);
        searchableSpinnerState.setAdapter(mStateAdapter);
        searchableSpinnerCategory.setAdapter(mCategoryAdapter);
        searchableSpinnerDistrict.setAdapter(mDistrictAdapter);


        //Area Spinner
        searchableSpinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mArea = mAreaArray.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mArea = getString(R.string.default_name);
            }
        });

        //District Spinner
        searchableSpinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDistrict = mDistrictArray.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mArea = getString(R.string.default_name);
            }
        });

        //State Spinner
        searchableSpinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mState = mStateArray.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mArea = getString(R.string.default_name);
            }
        });


        //Category Spinner
        searchableSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCategory = mCategoryArray.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mArea = getString(R.string.default_name);
            }
        });

    }

    //  initialize the views on the screen
    private void initializeViews() {
        progressDialog = new ProgressDialog(this);
        floatingActionButtonAddData = findViewById(R.id.fabAddData);
        editTextName = findViewById(R.id.textInputEditName);
        editTextAddress = findViewById(R.id.textInputEditAddress);
        editTextPhone = findViewById(R.id.textInputEditPhone);
        editTextEmail = findViewById(R.id.textInputEditEmail);
        searchableSpinnerCategory = findViewById(R.id.searchCategory);
        searchableSpinnerArea = findViewById(R.id.searchArea);
        searchableSpinnerDistrict = findViewById(R.id.searchDistrict);
        searchableSpinnerState = findViewById(R.id.searchState);
        searchableSpinnerType = findViewById(R.id.searchType);
        mCoordinaterLayout = findViewById(R.id.coordinator);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }


    //Takes the Variable from Edit texts inside the string and starts storedata function to store data in the server
    public void validateData() {

        //I edit the prefs and add my string set and label it as "List"
        mType = "Prospect";
        mName = editTextName.getText().toString();
        mAddress = editTextAddress.getText().toString();
        mPhone = editTextPhone.getText().toString().trim();
        mEmail = editTextEmail.getText().toString().trim();
        mStatus = "1";
        mCreatedOn = getDateTime();
        mCreatedBy = new UserInfoHelper(this).getUserId();
        mUpdatedOn = getDateTime();
        mUpdatedBy = new UserInfoHelper(this).getUserId();
        if (TextUtils.isEmpty(mName)) {
            editTextName.setError("Enter Name");
        }
        if (TextUtils.isEmpty(mAddress)) {
            editTextAddress.setError("Enter Address");
        }
        if (TextUtils.isEmpty(mPhone)) {
            editTextPhone.setError("Enter Phone Number");
        }
        if (TextUtils.isEmpty(mEmail)) {
            editTextEmail.setError("Enter Email Address");
        } else {

            if (!isEmailValid(mEmail))
                editTextEmail.setError("Enter a valid Email Address");
        }
        if (TextUtils.equals(mArea, "default"))
            Toast.makeText(this, "Select Area", Toast.LENGTH_SHORT).show();
        if (TextUtils.equals(mDistrict, "default"))
            Toast.makeText(this, "Select Category", Toast.LENGTH_SHORT).show();
        if (TextUtils.equals(mState, "default"))
            Toast.makeText(this, "Select State", Toast.LENGTH_SHORT).show();
        if (TextUtils.equals(mType, "default"))
            Toast.makeText(this, "Select Type", Toast.LENGTH_SHORT).show();
        if (TextUtils.equals(mCategory, "default"))
            Toast.makeText(this, "Select Category", Toast.LENGTH_SHORT).show();
        if (!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mAddress) && !TextUtils.isEmpty(mPhone) && !TextUtils.isEmpty(mEmail) && !TextUtils.equals(mArea, "default") && !TextUtils.equals(mState, "default") && !TextUtils.equals(mDistrict, "default") && !TextUtils.equals(mType, "default") && !TextUtils.equals(mCategory, "default") && isEmailValid(mEmail))
            storeData();

    }

    //  Stores data to Server
    private void storeData() {

        showProgress();


        Map<String, String> postParam = new HashMap<>();

//
        postParam.put("1", mName);
        postParam.put("2", mType);
        postParam.put("3", mCategory);
        postParam.put("4", mAddress);
        postParam.put("5", mArea);
        postParam.put("6", mDistrict);
        postParam.put("7", mState);
        postParam.put("8", mPhone);
        postParam.put("9", mEmail);
        postParam.put("10", mStatus);
        postParam.put("11", mCreatedOn);
        postParam.put("12", mCreatedBy);
        postParam.put("13", mUpdatedOn);
        postParam.put("14", mUpdatedBy);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, BASE_URL + INSERT_CUSTOMER, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        try {
                            if (response.getString("status").equals("updated")) {
                                Toast.makeText(InsertCustomerActivity.this, "Successfully Inserted data", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(InsertCustomerActivity.this, InsertCustomerContactActivity.class);
                                intent.putExtra("customerId", response.getString("allot_id"));
                                Log.i(TAG, "onResponse: " + response.getString("allot_id"));
                                intent.putExtra("customerName", mName);
                                intent.putExtra("createdBy", mCreatedBy);
                                intent.putExtra("createdOn", mCreatedOn);
                                intent.putExtra("updatedBy", mUpdatedBy);
                                intent.putExtra("updatedOn", mUpdatedOn);
                                intent.putExtra("email", mEmail);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(InsertCustomerActivity.this, "" + response, Toast.LENGTH_SHORT).show();
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
                headers.put("x-access-token", new UserInfoHelper(InsertCustomerActivity.this).getUserToken());
                return headers;
            }


        };
        // Adding request to request queue
        queue.add(jsonObjReq);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                        validateData();
                    }
                });
                snackbar.show();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, PROGRESS_DIALOG_DURATION);

    }




}

