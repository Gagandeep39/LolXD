package com.example.test.nuvoco3.customer;

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
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.test.nuvoco3.signup.LoginActivity.DATABASE_URL;

public class UpdateStatusActivity extends AppCompatActivity {

    private static final String TAG = "UpdateStatus Activity";

    FloatingActionButton fab;
    String mComplaintId;
    ProgressDialog progressDialog;
    RequestQueue queue;
    int size = 50;
    String mRecordId, mType, mDate, mRepresentative, mCustomerId, mCustomerName, mStatus, mDetails, mRemark, mCreatedOn, mCreatedBy, mUpdatedOn, mUpdatedBy, mClosedOn;
    CoordinatorLayout mCoordinatorLayout;
    TextInputEditText mEditTextComplaintId, mEditTextCustomerId, mEditTextCustomerName, mEditTextDetails, mEditTextRemark;

    //Master Helper
    MasterHelper mComplaintTypeHelper;
    ArrayList<String> mComplaintList;

    SearchableSpinner mComplaintSpinner;

    NestedScrollView mScrollView;
    SearchView mSearchView;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);
        initializeViews();
        initializeVariables();
        if (mComplaintId != null) {
            readData();
            viewComplaintsNormal();
            populateSpinner();
            mTextView.setVisibility(View.GONE);
            mScrollView.setVisibility(View.VISIBLE);
            fab.setVisibility(View.VISIBLE);
            mSearchView.setVisibility(View.GONE);

        } else {
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    mComplaintId = query;
                    readData();
                    viewComplaintsNormal();
                    populateSpinner();
                    mTextView.setVisibility(View.GONE);
                    mScrollView.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.VISIBLE);
                    mSearchView.setVisibility(View.GONE);

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });


        }



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRemark = mEditTextRemark.getText().toString();

                if (TextUtils.isEmpty(mRemark))
                    mEditTextRemark.setError("Enter Remark");
                else {

                    if (!mStatus.equals("Closed")) {
                        mClosedOn = getDateTime();

                    } else if (mStatus.equals(getString(R.string.default_name))) {
                        Toast.makeText(UpdateStatusActivity.this, "Select Status", Toast.LENGTH_SHORT).show();
                    } else {
                        mClosedOn = "2017-01-01 00:00:10";
                    }

                    updateDataNormal();

                }

            }
        });
    }


    private void populateSpinner() {
        ArrayAdapter mComplaintAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mComplaintList);
        mComplaintSpinner.setAdapter(mComplaintAdapter);
        mComplaintSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mStatus = mComplaintList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mStatus = getString(R.string.default_name);
            }
        });
    }

    private void populateTextFields() {

        mEditTextComplaintId.setText(mComplaintId);
        mEditTextCustomerId.setText(mCustomerId);
        mEditTextCustomerName.setText(mCustomerName);
        if (!TextUtils.isEmpty(mRemark))
            mEditTextRemark.setText(mRemark);
        mEditTextDetails.setText(mDetails);
        mUpdatedOn = getDateTime();
        mUpdatedBy = getUserId();
        mDate = getDateTime();
        mCreatedOn = getDateTime();
        mUpdatedOn = getDateTime();


        mEditTextComplaintId.getBackground().mutate().setColorFilter(getResources().getColor(R.color.light_grey), PorterDuff.Mode.SRC_ATOP);
        mEditTextCustomerId.getBackground().mutate().setColorFilter(getResources().getColor(R.color.light_grey), PorterDuff.Mode.SRC_ATOP);
        mEditTextCustomerName.getBackground().mutate().setColorFilter(getResources().getColor(R.color.light_grey), PorterDuff.Mode.SRC_ATOP);
        mEditTextRemark.getBackground().mutate().setColorFilter(getResources().getColor(R.color.light_grey), PorterDuff.Mode.SRC_ATOP);
        mEditTextDetails.getBackground().mutate().setColorFilter(getResources().getColor(R.color.light_grey), PorterDuff.Mode.SRC_ATOP);


    }

    private void initializeVariables() {
        progressDialog = new ProgressDialog(this);
        queue = Volley.newRequestQueue(this);
        mComplaintTypeHelper = new MasterHelper(this, "ComplaintStatus");
        mComplaintList = mComplaintTypeHelper.getRecordName();

    }

    private void initializeViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        fab = findViewById(R.id.fab);
        mComplaintId = getIntent().getStringExtra("ComplaintId");
        mCoordinatorLayout = findViewById(R.id.coordinator);
        mEditTextComplaintId = findViewById(R.id.editTextComplaintId);
        mEditTextCustomerId = findViewById(R.id.editTextCustomerId);
        mEditTextCustomerName = findViewById(R.id.editTextName);
        mEditTextRemark = findViewById(R.id.editTextRemark);
        mEditTextDetails = findViewById(R.id.editTextDetails);
        mComplaintSpinner = findViewById(R.id.searchStatus);
        mScrollView = findViewById(R.id.nestedScrollView);
        mSearchView = findViewById(R.id.searchView);
        mTextView = findViewById(R.id.textView);

    }

    private void readData() {
        startProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                DATABASE_URL + "/dispCmpDetails/0", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("lol", "onResponse:  " + response);
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = response.getJSONArray("message");

                    size = jsonArray.length();

                    for (int i = 0; i < size; i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        if (object.getString("Complaint_id").toLowerCase().equals(mComplaintId)) {


                            mCustomerId = object.getString("Customer_id") + "";
                            mCustomerName = object.getString("Customer_name") + "";
                            mRepresentative = object.getString("Representative");
                            mDetails = object.getString("complaint_details") + "";
                            mRemark = object.getString("resolution_remark");
                            mCreatedOn = object.getString("createdOn") + "";
                            mCreatedBy = object.getString("creayedBy") + "";
                            mUpdatedBy = object.getString("updatedBy") + "";
                            mUpdatedOn = object.getString("updatedOn") + "";
                            mClosedOn = object.getString("complaint_closedOn") + "";
                            mStatus = object.getString("complaint_status") + "";
                        }

                    }

                    populateTextFields();

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

    private void startProgressDialog() {

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                Snackbar snackbar = Snackbar.make(mCoordinatorLayout, "Connection Time-out !", Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        readData();
                    }
                });
                snackbar.show();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 20000);
    }

    private void updateDataDetails() {
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                Snackbar snackbar = Snackbar.make(mCoordinatorLayout, "Connection Time-out !", Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateDataDetails();
                    }
                });
                snackbar.show();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 20000);

        Map<String, String> postParam = new HashMap<>();

//
        postParam.put("2", mComplaintId);
        postParam.put("3", mDate);
        postParam.put("4", mRepresentative);
        postParam.put("5", mCustomerId);
        postParam.put("6", mCustomerName);
        postParam.put("7", mStatus);
        postParam.put("8", mDetails);
        postParam.put("9", mRemark);
        postParam.put("10", mCreatedOn);
        postParam.put("11", mCreatedBy);
        postParam.put("12", mUpdatedOn);
        postParam.put("13", mUpdatedBy);
        postParam.put("14", mClosedOn);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, DATABASE_URL + "/insertCmpD", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                        try {
                            if (response.getString("status").equals("updated")) {

                                progressDialog.dismiss();
                                Toast.makeText(UpdateStatusActivity.this, "Successfully Updated Data", Toast.LENGTH_SHORT).show();
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


    //  Function to provide current data and time
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    private void updateDataNormal() {

        Map<String, String> postParam = new HashMap<>();

//

        postParam.put("1", mComplaintId);
//        postParam.put("2", "2017-01-01 00:00:10");  //date
        postParam.put("4", mCustomerName);
        postParam.put("5", mType);
        postParam.put("6", mDetails);
        postParam.put("9", mUpdatedOn);    //updated on
        postParam.put("10", mUpdatedBy);
        postParam.put("11", mStatus);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, DATABASE_URL + "/updateComplaints", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                        try {
                            if (response.getString("status").equals("updated")) {
//                                progressDialog.dismiss();
//                                Toast.makeText(InsertComplaintActivity.this, "Successfully Inserted Data", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(InsertComplaintActivity.this, InsertComplaintDetailsActivity.class);
//                                intent.putExtra("date", mDate);
//                                intent.putExtra("customerId", mCustomerId);
//                                intent.putExtra("customerName", mCustomerName);
//                                intent.putExtra("complaintDetails", mComplaintDetails);
                                updateDataDetails();
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

    private void viewComplaintsNormal() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                DATABASE_URL + "/dispComplaint", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("lol", "onResponse:  " + response);
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = response.getJSONArray("message");


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        if (object.getString("record_id").equals(mComplaintId)) {
                            mType = object.getString("Type_Ofcomplaint") + "";
                        }
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

    public void enableSearch(View v) {

        mSearchView.setIconified(false);
    }


}
