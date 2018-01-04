package com.example.test.nuvoco3.lead;

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

import static com.example.test.nuvoco3.helpers.Contract.BASE_URL;
import static com.example.test.nuvoco3.helpers.Contract.DISPLAY_CONTACT;

public class ViewCustomerContactActivity extends AppCompatActivity {
    private static final String TAG = "ViewCustomerContact";
    public static ArrayList<CustomerContact> mCustomerContactArrayList;
    CustomerContactAdapter mAdapter;
    RequestQueue queue;
    SearchView mSearchView;
    String mSearchText = "0";
    RecyclerView mRecyclerView;
    CoordinatorLayout mCoordinaterLayout;
    ProgressDialog progressDialog;
    String mContactId, mCustomerId, mCustomerName, mContactName, mContactPhone, mContactEmail, mContactDOB, mContactDOA, mCreatedOn, mCreatedBy, mUpdatedOn, mUpdatedBy;
    int size = 0, flag;
    private boolean isChecked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer_contact);
        initializeViews();
        progressDialog = new ProgressDialog(this);
        populateActivity();


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

    private void populateActivity() {
        queue = Volley.newRequestQueue(this);
        mCustomerContactArrayList = new ArrayList<>();
        if (getIntent().getStringExtra("customerID") != null) {
            mSearchText = getIntent().getStringExtra("customerID");
            readData();
        } else
            readData();
        mAdapter = new CustomerContactAdapter(this, mCustomerContactArrayList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.getItemAnimator();
        mRecyclerView.setAdapter(mAdapter);

    }


    private void readData() {
        flag = 0;
        showProgress();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                BASE_URL + DISPLAY_CONTACT, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("message");

                    progressDialog.dismiss();
                    flag = 1;
                    for (int i = 0; i < jsonArray.length(); i++) {

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
            if (object.getString("record_id").toLowerCase().contains(mSearchText.toLowerCase())
                    || object.getString("c_name").toLowerCase().contains(mSearchText.toLowerCase())
                    || object.getString("contactPerson").toLowerCase().contains(mSearchText.toLowerCase())
                    || object.getString("contactPerson_email").toLowerCase().contains(mSearchText.toLowerCase())) {
                mContactId = object.getString("record_id") + "";   //primary key
                mCustomerName = object.getString("c_name") + "";
                mContactName = object.getString("contactPerson") + "";
                mContactPhone = object.getString("contactPerson_phone") + "";
                mContactEmail = object.getString("contactPerson_email") + "";
                mContactDOB = object.getString("contactPerson_DOB") + "";
                mContactDOA = object.getString("contactPerson_DOA") + "";
                mCustomerId = object.getString("record_id") + ""; //Foreign Key
                mCreatedBy = object.getString("createdBy") + "";
                mCreatedOn = object.getString("createdOn") + "";
                mUpdatedBy = object.getString("updatedBy") + "";
                mUpdatedOn = object.getString("updatedOn") + "";
                Log.i("lol", "onResponse: " + mCustomerId);
                mCustomerContactArrayList.add(new CustomerContact(mContactId, mCustomerId, mCustomerName, mContactName, mContactPhone, mContactEmail, mContactDOB, mContactDOA, mCreatedBy, mCreatedOn, mUpdatedBy, mUpdatedOn));
                mAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void initializeViews() {
        mSearchView = findViewById(R.id.searchView);
        mRecyclerView = findViewById(R.id.recyclerView);
        mCoordinaterLayout = findViewById(R.id.coordinator);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    public void enableSearch(View v) {

        mSearchView.setIconified(false);
    }


    private void showProgress() {

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (flag == 0) {
                    progressDialog.dismiss();
                    Snackbar snackbar = Snackbar.make(mCoordinaterLayout, "Connection Time-out !", Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            readData();
                        }
                    });
                    snackbar.show();
                }
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 10000);
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
            mCustomerContactArrayList.clear();
            mAdapter.notifyDataSetChanged();
            readData();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
