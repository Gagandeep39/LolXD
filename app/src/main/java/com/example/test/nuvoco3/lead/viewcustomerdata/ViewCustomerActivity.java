package com.example.test.nuvoco3.lead.viewcustomerdata;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.nuvoco3.R;
import com.example.test.nuvoco3.lead.viewcustomerdata.cusomerlistrecyclerview.Customer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.test.nuvoco3.signup.LoginActivity.DATABASE_URL;

public class ViewCustomerActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    String mAddress, mArea, mDistrict, mCategory, mEmail, mPhone, mState, mCreatedBy, mCreatedOn, mUpdatedBy, mUpdatedOn, mId, mName, mStatus;
    ArrayList<Customer> mCustomerArrayList;
    CustomerAdapter mAdapter;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        queue = Volley.newRequestQueue(this);
        mRecyclerView = findViewById(R.id.recyclerView);
        mCustomerArrayList = new ArrayList<>();
//        mCustomerArrayList.add(new Customer("lol", "lol", "lol", "lol", "lol", "lol", "lol", "lol", "lol", "lol", "lol", "lol", "lol", "lol"));
        mCustomerArrayList = readData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setHasFixedSize(true);
        mAdapter = new CustomerAdapter(this, mCustomerArrayList);
        mRecyclerView.setAdapter(mAdapter);

        NewsAsyncTask task = new NewsAsyncTask();
        task.execute();
//
    }


    private ArrayList<Customer> readData() {


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                DATABASE_URL + "/dispCust", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("lol", "onResponse:  " + response);
                try {
                    JSONArray jsonArray = response.getJSONArray("message");
                    for (int i = 0; i < 10; i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
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
                        Log.i("lol", "onResponse: " + mName + mArea + mCategory + "  " + mStatus + " " + mId);
                        mCustomerArrayList.add(new Customer("de", "we", "we", "", "w", "we", "w", "w", "w", "w", "w", "w", "w", "w"));
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
        return mCustomerArrayList;
    }

    public class NewsAsyncTask extends AsyncTask<String, Void, List<Customer>> {


        @Override
        protected List<Customer> doInBackground(String... strings) {
            if (strings.length < 1 || strings[0] == null)
                return null;
            List<Customer> result = readData();
            return result;
        }

        @Override
        protected void onPostExecute(List<Customer> news) {

            //mAdapter.clear();
            if (news != null && !news.isEmpty()) {
                //mAdapter.addAll(news);
                CustomerAdapter adapter1 = new CustomerAdapter(getBaseContext(), news);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                mRecyclerView.setAdapter(adapter1);


            }
        }
    }



}
