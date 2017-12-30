package com.example.test.nuvoco3.lead;

import android.app.ProgressDialog;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.test.nuvoco3.signup.LoginActivity.DATABASE_URL;

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
    int size = 0;
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

        showProgress();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                DATABASE_URL + "/dispCon/0", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("message");

                    if (mSearchText.equals("0"))
                        size = 50;
                    else
                        size = jsonArray.length();


                    progressDialog.dismiss();
                    for (int i = 0; i < size; i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
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


    private void initializeViews() {
        mSearchView = findViewById(R.id.searchView);
        mRecyclerView = findViewById(R.id.recyclerView);
        mCoordinaterLayout = findViewById(R.id.coordinator);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void enableSearch(View v) {

        mSearchView.setIconified(false);
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
