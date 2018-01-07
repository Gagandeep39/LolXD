package com.example.test.nuvoco3.market;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

public class ViewBrandPriceActivity extends AppCompatActivity {
    public static ArrayList<BrandPrice> mBrandPriceArrayList;
    RecyclerView mRecyclerView;
    BrandPriceAdapter mBrandPriceAdapter;
    RequestQueue queue;
    SearchView mSearchView;
    String mSearchText = "0";
    CoordinatorLayout mCoordinaterLayout;
    ProgressDialog progressDialog;
    private boolean isChecked = false;
    int size = 25;
    String mCustomer, mDate, mProduct, mRSP, mStock, mWSP, mCounter, mCreatedBy, mCreatedOn, mUpdatedBy, mUpdatedOn, mRecordId, mRemarks, mRepresentative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_brand_price);
        initializeViews();
        initializeVariables();
        initializeSearch();
        readData();


    }

    private void initializeVariables() {
        progressDialog = new ProgressDialog(this);
        mBrandPriceArrayList = new ArrayList<>();
        queue = Volley.newRequestQueue(this);
        mBrandPriceAdapter = new BrandPriceAdapter(this, mBrandPriceArrayList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mBrandPriceAdapter);

    }

    private void initializeSearch() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mBrandPriceArrayList.clear();
                mSearchText = query;
                readData();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
                mBrandPriceAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mBrandPriceAdapter);
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
        mSearchView = findViewById(R.id.searchView);
        mCoordinaterLayout = findViewById(R.id.coordinator);

    }

    public void enableSearch(View v) {

        mSearchView.setIconified(false);
    }

    private void readData() {
        showProgress();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                BASE_URL + DISPLAY_PRICE, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if (progressDialog.isShowing()) {

                    progressDialog.dismiss();
                }
                try {
                    JSONArray jsonArray = response.getJSONArray("message");
                    if (mSearchText.equals("0")) {
                        if (jsonArray.length() > 25) {
                            size = 25;
                        } else {
                            size = jsonArray.length();
                        }
                    }

                    for (int i = 0; i < size; i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        if (isChecked) {
                            if (object.getString("createdBy").equals(new UserInfoHelper(ViewBrandPriceActivity.this).getUserId())) {
                                fetchData(object);
                            }
                        } else {
                            fetchData(object);
                        }
                    }

                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewBrandPriceActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                VolleyLog.d("lol", "Error with Internet : " + error.getMessage());
                // hide the progress dialog
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("x-access-token", new UserInfoHelper(ViewBrandPriceActivity.this).getUserToken());
                return headers;
            }


        };
        queue.add(jsonObjReq);
    }

    private void fetchData(JSONObject object) {
        try {
//            if (object.getString("counter").toLowerCase().contains(mSearchText.toLowerCase())) {
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
                mBrandPriceArrayList.add(new BrandPrice(mRecordId, mRepresentative, mCounter, mDate, mCustomer, mProduct, mWSP, mRSP, mStock, mRemarks, mCreatedOn, mCreatedBy, mUpdatedOn, mUpdatedBy));
                mBrandPriceAdapter.notifyDataSetChanged();
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.checkable_menu) {
            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            isChecked = !item.isChecked();
            item.setChecked(isChecked);
            mBrandPriceArrayList.clear();
            mBrandPriceAdapter.notifyDataSetChanged();
            readData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showProgress() {
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (progressDialog.isShowing()) {

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
        handler.postDelayed(runnable, PROGRESS_DIALOG_DURATION);

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


}
