package com.example.test.nuvoco3.visits;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.test.nuvoco3.helpers.CalendarHelper.compareSmallDate;
import static com.example.test.nuvoco3.helpers.CalendarHelper.convertSmallDateToJson;
import static com.example.test.nuvoco3.helpers.CalendarHelper.getDate;
import static com.example.test.nuvoco3.helpers.Contract.BASE_URL;
import static com.example.test.nuvoco3.helpers.Contract.DISPLAY_JCP_VISIT;
import static com.example.test.nuvoco3.helpers.Contract.DISPLAY_JCP_VISIT_DETAILS;
import static com.example.test.nuvoco3.helpers.Contract.PROGRESS_DIALOG_DURATION;

public class ViewJCPActivity extends AppCompatActivity {

    private static final String TAG = "ViewJCP Activity";
    String mRecordId, mCustomerId, mCustomerName, mDate, mStartTime, mEndTime, mObjective, mCreatedOn, mCreatedBy, mUpdatedOn, mUpdatedBy;

    TextInputEditText mEditTextRepresentative, mEditTextDate;
    int mYear, mMonth, mDay;
    String mSearchDate = getDate();
    ImageView mImageSearch;
    RecyclerView mRecyclerView;
    ArrayList<JCP> mJCPArrayList;
    RequestQueue queue;
    boolean isChecked = false;
    ProgressDialog progressDialog;
    CoordinatorLayout mCoordinatorLayout;
    int size = 25;
    JCPAdapter mJcpAdapter;


    //New Modification
    ArrayList<String> mDetailsArray;
    String mDetailsJcpId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_jcp);
        initializeViews();
//        Log.i(TAG, "onClick: " + convertSmallDateToJson(mSearchDate));

        initializeVariables();
        readData();

        mImageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchDate = mEditTextDate.getText().toString();
                mJCPArrayList.clear();
                readData();
                mJcpAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mJcpAdapter);
            }
        });

    }

    private void initializeVariables() {

        mDetailsArray = new ArrayList<>();
        queue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);
        mJCPArrayList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.getItemAnimator();
        mJcpAdapter = new JCPAdapter(this, mJCPArrayList);
        mRecyclerView.setAdapter(mJcpAdapter);


    }

    private void initializeViews() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mEditTextRepresentative = findViewById(R.id.editTextRepresentative);
        mEditTextDate = findViewById(R.id.editTextDate);
        mImageSearch = findViewById(R.id.imageViewSearch);
        mCoordinatorLayout = findViewById(R.id.coordinator);
        mRecyclerView = findViewById(R.id.recyclerView);

        mEditTextRepresentative.setText(new UserInfoHelper(this).getUserId());
        mEditTextDate.setText(getDate());
    }

    private void readData() {
        startProgressDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                BASE_URL + DISPLAY_JCP_VISIT, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                try {
                    Log.i(TAG, "onResponse: " + response);

                    JSONArray jsonArray = response.getJSONArray("message");
                    for (int i = 0; i < jsonArray.length(); i++) {


                        JSONObject object = jsonArray.getJSONObject(i);
//                            for(int j=0; j<mDetailsArray.size(); j++){
////                                if (mDetailsArray.get(j)==object.getString("record_id")){
////                                    count
////                                }
//
//                            }
                        if (object.getString("createdBy").equals(new UserInfoHelper(ViewJCPActivity.this).getUserId())) {
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
                Toast.makeText(ViewJCPActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                VolleyLog.d("lol", "Error with Internet : " + error.getMessage());
                // hide the progress dialog
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("x-access-token", new UserInfoHelper(ViewJCPActivity.this).getUserToken());
                return headers;
            }


        };

        // Adding request to request queue
        queue.add(jsonObjReq);
    }

    private void fetchData(JSONObject object) {
        try {
            if (object.getString("Date").toLowerCase().contains(convertSmallDateToJson(mSearchDate).toLowerCase())) {
                mCustomerId = object.getString("Customer_id") + "";

                mDate = object.getString("Date") + "";
                mObjective = object.getString("Objective") + "";
                mCreatedBy = object.getString("createdBy") + "";
                mCreatedOn = object.getString("createdOn") + "";
                mCustomerName = object.getString("customer_name") + "";
                mEndTime = object.getString("end_Time") + "";
                mRecordId = object.getString("record_id") + "";
                mStartTime = object.getString("start_Time") + "";
                mUpdatedBy = object.getString("updatedBy") + "";
                mUpdatedOn = object.getString("updatedOn") + "";
                mJCPArrayList.add(new JCP(mRecordId, mCustomerId, mCustomerName, mDate, mStartTime, mEndTime, mObjective, mCreatedOn, mCreatedBy, mUpdatedOn, mUpdatedBy));
                mJcpAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void datePickerFunction(View v) {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditTextDate.getWindowToken(), 0);

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if (compareSmallDate(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth, getDate())) {

                            Toast.makeText(ViewJCPActivity.this, "Date cannot Exceed Current Date", Toast.LENGTH_SHORT).show();
                        } else {


                            mEditTextDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = new Date();

                            mSearchDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + " " + dateFormat.format(date);
                            readData();
                            Log.i(TAG, "onDateSet: " + mSearchDate);
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }




    private void startProgressDialog() {

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    Snackbar snackbar = Snackbar.make(mCoordinatorLayout, "Connection Time-out !", Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    void readDataFromDetailsServer(){
       startProgressDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                BASE_URL + DISPLAY_JCP_VISIT_DETAILS, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                try {
                    Log.i(TAG, "onResponse: " + response);

                    JSONArray jsonArray = response.getJSONArray("message");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        try {

                            mDetailsJcpId = object.getString("JCP_id");
                            mDetailsArray.add(mDetailsJcpId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewJCPActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                VolleyLog.d("lol", "Error with Internet : " + error.getMessage());
                // hide the progress dialog
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("x-access-token", new UserInfoHelper(ViewJCPActivity.this).getUserToken());
                return headers;
            }


        };

        // Adding request to request queue
        queue.add(jsonObjReq);
    }

}
