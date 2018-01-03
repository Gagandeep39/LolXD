package com.example.test.nuvoco3.customer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.nuvoco3.R;
import com.example.test.nuvoco3.lead.Customer;
import com.example.test.nuvoco3.lead.CustomerAdapter;
import com.example.test.nuvoco3.signup.ObjectSerializer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.test.nuvoco3.signup.LoginActivity.DATABASE_URL;

public class ViewNewCustomerActivity extends AppCompatActivity {
    private static final String TAG = "NUVOCO";
    public static ArrayList<Customer> mCustomerArrayList;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefresh;
    String mAddress, mArea, mDistrict, mCategory, mEmail, mPhone, mState, mCreatedBy, mCreatedOn, mUpdatedBy, mUpdatedOn, mId, mName, mStatus;
    CustomerAdapter mAdapter;
    RequestQueue queue;
    SearchView mSearchView;
    String mSearchText = "0";
    String mUserId;
    ProgressDialog progressDialog;
    CoordinatorLayout mCoordinatorLayout;
    int size = 0;
    private boolean isChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);
        progressDialog = new ProgressDialog(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        queue = Volley.newRequestQueue(this);
        initializeViews();

        mCustomerArrayList = new ArrayList<>();
        readData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.getItemAnimator();
        mAdapter = new CustomerAdapter(this, mCustomerArrayList);
        mRecyclerView.setAdapter(mAdapter);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i(TAG, "onQueryTextSubmit: " + query);
                mCustomerArrayList.clear();
                mSearchText = query;
                readData();

                mRecyclerView.setAdapter(mAdapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

    }

    private void initializeViews() {

        mRecyclerView = findViewById(R.id.recyclerView);
        mSearchView = findViewById(R.id.searchView);
        mCoordinatorLayout = findViewById(R.id.coordinator);
    }


    private void readData() {
        startProgressDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                DATABASE_URL + "/dispCust", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("lol", "onResponse:  " + response);
                try {
                    JSONArray jsonArray = response.getJSONArray("message");


                    if (mSearchText.equals("0"))
                        size = 50;
                    else
                        size = jsonArray.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject object = jsonArray.getJSONObject(i);


                        if (object.getString("createdBy").equals(getUserId())) {
                            if (object.getString("c_email").toLowerCase().contains(mSearchText.toLowerCase())
                                    || object.getString("c_phone").toLowerCase().contains(mSearchText.toLowerCase())
                                    || object.getString("c_state").toLowerCase().contains(mSearchText.toLowerCase())
                                    || object.getString("name").toLowerCase().contains(mSearchText.toLowerCase())
                                    || object.getString("record_id").toLowerCase().contains(mSearchText.toLowerCase())) {
                                mAddress = object.getString("address") + "";
                                mArea = object.getString("c_area") + "";
                                mCategory = object.getString("c_category") + "";
                                mDistrict = object.getString("c_district") + "";
                                mEmail = object.getString("c_email") + "";
                                mPhone = object.getString("c_phone") + "";
                                mState = object.getString("c_state") + "";
                                mName = object.getString("name") + "";
                                mId = object.getString("record_id") + "";
                                mStatus = object.getString("status") + "";
                                mCreatedBy = object.getString("createdBy") + "";
                                mCreatedOn = object.getString("createdOn") + "";
                                mUpdatedBy = object.getString("updatedBy") + "";
                                mUpdatedOn = object.getString("updatedOn") + "";
                                Log.i(TAG, "onResponse: " + mAddress);
                                mCustomerArrayList.add(new Customer(mName, mId, mCategory, mAddress, mArea, mDistrict, mState, mPhone, mEmail, mStatus, mCreatedBy, mCreatedOn, mUpdatedBy, mUpdatedOn));
                                mAdapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                            }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.checkable_menu) {
            isChecked = !item.isChecked();
            item.setChecked(isChecked);
            mCustomerArrayList.clear();
            mAdapter.notifyDataSetChanged();
            readData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void enableSearch(View v) {

        mSearchView.setIconified(false);
    }

    private void startProgressDialog() {

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                Snackbar snackbar = Snackbar.make(mCoordinatorLayout, "Connection Time-out !", Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

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




}