package com.example.test.nuvoco3.market;

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

public class ViewBrandPriceActivity extends AppCompatActivity {
    public static final String URL_DISPLAY_MARKET = "/dispMarket";
    public static ArrayList<BrandPrice> mBrandPriceArrayList;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefresh;
    BrandPriceAdapter mBrandPriceAdapter;
    RequestQueue queue;
    SearchView mSearchView;
    String mSearchText = "0";
    private String mCustomer, mDate, mProduct, mRSP, mStock, mWSP, mCounter, mCreatedBy, mCreatedOn, mUpdatedBy, mUpdatedOn, mRecordId, mRemarks, mRepresentative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_brand_price);
        initializeViews();
        mBrandPriceArrayList = new ArrayList<>();
        queue = Volley.newRequestQueue(this);
        initializeSearch();
        readData();
        mBrandPriceAdapter = new BrandPriceAdapter(this, mBrandPriceArrayList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mBrandPriceAdapter);


    }

    private void initializeSearch() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchText = query;
                mBrandPriceAdapter.notifyDataSetChanged();
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
        mSwipeRefresh = findViewById(R.id.swipeRefreshLayout);
        mSearchView = findViewById(R.id.searchView);
    }

    private void readData() {


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                DATABASE_URL + URL_DISPLAY_MARKET, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("lol", "onResponse:  " + response);
                try {
                    JSONArray jsonArray = response.getJSONArray("message");
                    for (int i = 0; i < 50; i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        if (object.getString("Brand").toLowerCase().contains(mSearchText.toLowerCase())
                                || object.getString("record_id").toLowerCase().contains(mSearchText.toLowerCase())
                                || object.getString("counter").toLowerCase().contains(mSearchText.toLowerCase())) {
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
        }
        return super.onOptionsItemSelected(item);
    }

}
