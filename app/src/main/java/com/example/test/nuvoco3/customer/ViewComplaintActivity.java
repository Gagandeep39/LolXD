package com.example.test.nuvoco3.customer;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

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

public class ViewComplaintActivity extends AppCompatActivity {
    public static final String URL_DISPLAY_COMPLAINT_DTLS = "/dispCmpDetails/0";
    String mComplaintId, mCustomerId, mCustomerName, mCreatedBy, mCreatedOn, mUpdatedBy, mUpdatedOn, mRepresentative, mDetails, mClosedOn, mDate, mStatus, mRemark, mRecordId;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefresh;
    ArrayList<ComplaintDetails> mComplaintArrayList;
    ComplaintDetailsAdapter mComplaintAdapter;
    RequestQueue queue;
    SearchView mSearchView;
    String mSearchText = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaint);
        initializeViews();
        mComplaintArrayList = new ArrayList<>();
        queue = Volley.newRequestQueue(this);
        initializeSearch();
        readData();
        mComplaintAdapter = new ComplaintDetailsAdapter(this, mComplaintArrayList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mComplaintAdapter);
    }

    private void readData() {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                DATABASE_URL + URL_DISPLAY_COMPLAINT_DTLS, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("lol", "onResponse:  " + response);
                try {
                    JSONArray jsonArray = response.getJSONArray("message");
                    for (int i = 0; i < 50; i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        if (object.getString("Complaint_id").toLowerCase().contains(mSearchText.toLowerCase())
                                || object.getString("Customer_name").toLowerCase().contains(mSearchText.toLowerCase())
                                || object.getString("Customer_id").toLowerCase().contains(mSearchText.toLowerCase())) {
                            mComplaintId = object.getString("Complaint_id") + "";
                            mCustomerId = object.getString("Customer_id") + "";
                            mCustomerName = object.getString("Customer_name") + "";
                            mDate = object.getString("Date") + "";
                            mRepresentative = object.getString("Representative") + "";
                            mClosedOn = object.getString("complaint_closedOn") + "";
                            mDetails = object.getString("complaint_details") + "";
                            mStatus = object.getString("complaint_status") + "";
                            mCreatedOn = object.getString("createdOn") + "";
                            mCreatedBy = object.getString("createdBy") + "";
                            mRecordId = object.getString("record_id") + "";
                            mRemark = object.getString("resolution_remark") + "";
                            mUpdatedBy = object.getString("updatedBy") + "";
                            mUpdatedOn = object.getString("updatedOn") + "";
                            mComplaintArrayList.add(new ComplaintDetails(mRecordId, mComplaintId, mDate, mRepresentative, mCustomerId, mCustomerName, mStatus, mDetails, mRemark, mCreatedOn, mCreatedBy, mUpdatedOn, mUpdatedBy, mClosedOn));
                            mComplaintAdapter.notifyDataSetChanged();
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

    private void initializeSearch() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchText = query;
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSearchText = "0";
                mRecyclerView.setAdapter(mComplaintAdapter);
            }
        });
    }

    private void initializeViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mRecyclerView = findViewById(R.id.recyclerView);
        mSwipeRefresh = findViewById(R.id.swipeRefreshLayout);
        mSearchView = findViewById(R.id.searchView);
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
