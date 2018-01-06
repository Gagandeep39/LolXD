package com.example.test.nuvoco3.market;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.nuvoco3.R;
import com.example.test.nuvoco3.helpers.UserInfoHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.test.nuvoco3.helpers.Contract.BASE_URL;
import static com.example.test.nuvoco3.helpers.Contract.DISPLAY_PRICE;
import static com.example.test.nuvoco3.helpers.Contract.PROGRESS_DIALOG_DURATION;

public class ViewBrandPriceDetailsActivity extends AppCompatActivity {
    private static final String TAG = "ViewPriceDetails";
    Toolbar toolbar;
    //Recycler view
    RecyclerView mRecyclerView;
    BrandPriceDetailsAdapter mDetailsAdapter;
    ArrayList<BrandPrice> mPriceList;
    //Initializing Volley Library
    RequestQueue queue;
    String mRecordId, mCustomer, mDate, mProduct, mRSP, mStock, mWSP, mCounter, mRemarks, mRepresentative, mCreatedBy, mCreatedOn, mUpdatedBy, mUpdatedOn;
    //for Show only my data toggle
    private boolean isChecked = false;
    //variables for arraylist
    //To Sow only Specific Customer
    private String mSearchText;
    ProgressDialog progressDialog;
    CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_price_details);
        initializeViews();
        initializeVariables();

    }

    private void initializeVariables() {
        progressDialog = new ProgressDialog(this);
        queue = Volley.newRequestQueue(this);
        if (getIntent().getStringExtra("CustomerName") != null) {
            mSearchText = getIntent().getStringExtra("CustomerName");
            Log.i(TAG, "initializeVariables: " + mSearchText);
            toolbar.setTitle(mSearchText);
            readData();
        }
        mPriceList = new ArrayList<>();
        mDetailsAdapter = new BrandPriceDetailsAdapter(this, mPriceList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mDetailsAdapter);


    }

    private void initializeViews() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mRecyclerView = findViewById(R.id.recyclerView);
        mCoordinatorLayout = findViewById(R.id.coordinator);
    }

    private void readData() {
        showProgressDialogue();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                BASE_URL + DISPLAY_PRICE, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                try {
                    JSONArray jsonArray = response.getJSONArray("message");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        if (isChecked) {
                            if (object.getString("createdBy").equals(new UserInfoHelper(ViewBrandPriceDetailsActivity.this).getUserId())) {
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
                Toast.makeText(ViewBrandPriceDetailsActivity.this, "" + error, Toast.LENGTH_SHORT).show();
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
                headers.put("x-access-token", new UserInfoHelper(ViewBrandPriceDetailsActivity.this).getUserToken());
                return headers;
            }


        };

        // Adding request to request queue
        queue.add(jsonObjReq);
    }

    private void fetchData(JSONObject object) {
        try {
            if (object.getString("counter").toLowerCase().contains(mSearchText.toLowerCase())) {
                Log.i(TAG, "fetchData: " + "just another test");
                mCustomer = object.getString("Brand") + "";
                mDate = object.getString("Date") + "";
                mProduct = object.getString("Product") + "";
                mRSP = object.getString("RSP") + "";
                mStock = object.getString("Stock") + "";
                mWSP = object.getString("WSP") + "";
                mCounter = object.getString("counter") + "";
                mRemarks = object.getString("remarks") + "";
                mRecordId = object.getString("record_id") + "";
                mRepresentative = object.getString("representative") + "";
                mCreatedBy = object.getString("createdBy") + "";
                mCreatedOn = object.getString("createdOn") + "";
                mUpdatedBy = object.getString("updatedBy") + "";
                mUpdatedOn = object.getString("updatedOn") + "";
                mPriceList.add(new BrandPrice(mRecordId, mRepresentative, mCounter, mDate, mCustomer, mProduct, mWSP, mRSP, mStock, mRemarks, mCreatedOn, mCreatedBy, mUpdatedOn, mUpdatedBy));
                mDetailsAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mDetailsAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem checkable = menu.findItem(R.id.checkable_menu);
        checkable.setChecked(isChecked);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_customer_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.checkable_menu) {
            isChecked = !item.isChecked();
            item.setChecked(isChecked);
            mPriceList.clear();
            mDetailsAdapter.notifyDataSetChanged();
            readData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showProgressDialogue() {
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    Snackbar snackbar = Snackbar.make(mCoordinatorLayout, "Connection Time-out !", Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(ViewBrandPriceDetailsActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                    snackbar.show();
                }
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, PROGRESS_DIALOG_DURATION);

    }

}
