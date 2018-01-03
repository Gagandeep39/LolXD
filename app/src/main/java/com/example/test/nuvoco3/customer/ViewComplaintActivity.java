package com.example.test.nuvoco3.customer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
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
import com.example.test.nuvoco3.signup.ObjectSerializer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.test.nuvoco3.signup.LoginActivity.DATABASE_URL;

public class ViewComplaintActivity extends AppCompatActivity {
    public static final String URL_DISPLAY_COMPLAINT_DTLS = "/dispComplaint";
    String mComplaintId, mCustomerId, mCustomerName, mCreatedBy, mCreatedOn, mUpdatedBy, mUpdatedOn, mDetails, mType, mDate;
    RecyclerView mRecyclerView;
    ArrayList<Complaints> mComplaintArrayList;
    ComplaintAdapter mComplaintAdapter;
    RequestQueue queue;
    SearchView mSearchView;
    String mSearchText = "0";
    ProgressDialog progressDialog;
    CoordinatorLayout mCoordinatorLayout;
    int size = 50;
    private boolean isChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaint);
        initializeViews();
        queue = Volley.newRequestQueue(this);
        initializeSearch();
        readData();
        mComplaintAdapter = new ComplaintAdapter(this, mComplaintArrayList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mComplaintAdapter);
    }

    private void readData() {
        startProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                DATABASE_URL + URL_DISPLAY_COMPLAINT_DTLS, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("lol", "onResponse:  " + response);
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = response.getJSONArray("message");
                    if (mSearchText.equals("0")) {
                        if (jsonArray.length() > 25)
                            size = 25;
                        else size = jsonArray.length();
                    }
                    else
                        size = jsonArray.length();


                    for (int i = 0; i < size; i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        if (isChecked) {
                            if (object.getString("createdBy").equals(getUserId())) {
//                                Log.i(TAG, "onResponse: " + "created by onlu" + isChecked);
                                fetchData(object);

                            }


                        } else {
                            fetchData(object);

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

    private void fetchData(JSONObject object) {
        try {
            if (object.getString("record_id").toLowerCase().contains(mSearchText.toLowerCase())) {
                mComplaintId = object.getString("record_id") + "";
                mCustomerId = object.getString("Customer_id") + "";
                mCustomerName = object.getString("Customer_name") + "";
                mDate = object.getString("Date") + "";
                mType = object.getString("Type_Ofcomplaint") + "";
                mDetails = object.getString("complaint_details") + "";

                mCreatedOn = object.getString("createdOn") + "";
                mCreatedBy = object.getString("createdBy") + "";
                mUpdatedBy = object.getString("upatedBy") + "";
                mUpdatedOn = object.getString("upatedOn") + "";
                mComplaintArrayList.add(new Complaints(mCustomerId, mCustomerName, mType, mDetails, mComplaintId, mDate, mCreatedOn, mCreatedBy, mUpdatedOn, mUpdatedBy));
                mComplaintAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initializeSearch() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchText = query;
                readData();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initializeViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mRecyclerView = findViewById(R.id.recyclerView);
//        mSwipeRefresh = findViewById(R.id.swipeRefreshLayout);
        mSearchView = findViewById(R.id.searchView);
        mCoordinatorLayout = findViewById(R.id.coordinator);
        progressDialog = new ProgressDialog(this);
        mComplaintArrayList = new ArrayList<>();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.checkable_menu) {
            isChecked = !item.isChecked();
            item.setChecked(isChecked);
            mComplaintArrayList.clear();
            mComplaintAdapter.notifyDataSetChanged();
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
                        readData();
                    }
                });
                snackbar.show();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 20000);
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
