package com.example.test.nuvoco3.visits;

import android.app.ProgressDialog;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.nuvoco3.R;
import com.example.test.nuvoco3.helpers.MasterHelper;
import com.example.test.nuvoco3.helpers.UserInfoHelper;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.test.nuvoco3.helpers.CalendarHelper.convertSmallDateToJson;
import static com.example.test.nuvoco3.helpers.Contract.BASE_URL;
import static com.example.test.nuvoco3.helpers.Contract.DISPLAY_JCP_VISIT;
import static com.example.test.nuvoco3.helpers.Contract.MASTER_VISIT_STATUS;
import static com.example.test.nuvoco3.helpers.Contract.PROGRESS_DIALOG_DURATION;
import static com.example.test.nuvoco3.helpers.Contract.VISIT_STATUS_COMPLETED;

public class ViewVisitDetailsActivity extends AppCompatActivity {
    private static final String TAG = "ViewVisitDetails";
    CoordinatorLayout mCoordinatorLayout;
    TextInputEditText mEditTextRepresentative, mEditTextJcpId, mEditTextCustomerName, mEditTextDate, mEditTextStartTime, mEditTextEndTime, mEditTextOrderTaken, mEditTextProduct, mEditTextOrderQuantity, mEditTextVisitRemark, mEditTextVisitStatus, mEditTextObjective;
    SearchableSpinner mVisitStatusSpinner;
    LinearLayout mOrderTakenLayout, mVisitStatusLayout;
    String mRepresentative, mJcpId, mCustomerId, mCustomerName, mStatus, mDate, mStartTime, mEndTime, mQuantity, mOrderTaken, mProduct, mRemark, mObjective;
ProgressDialog progressDialog;
RequestQueue queue;

//helper
    MasterHelper mVisitStatusHelper;
    ArrayList<String> mVisitStatusList;
    ArrayAdapter mVisitStatusAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_visit_details);
        initializeViews();
        initializeVariables();

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    private void initializeVariables(){
        progressDialog = new ProgressDialog(this);
        queue = Volley.newRequestQueue(this);
        if (getIntent().getStringExtra("JcpId")!=null){
            mStatus = getIntent().getStringExtra("VisitStatus");
            mRemark = getIntent().getStringExtra("VisitRemark");
            mOrderTaken =getIntent().getStringExtra("OrderTaken") ;
            mJcpId =getIntent().getStringExtra("JcpId") ;
            mEditTextJcpId.setText(mJcpId);
            mEditTextVisitStatus.setText(mStatus);
            mEditTextVisitRemark.setText(mRemark);
            mEditTextOrderTaken.setText(mOrderTaken);
            Log.i(TAG, "initializeVariables: " + getIntent().getStringExtra("OrderTaken"));
            if (!getIntent().getStringExtra("OrderTaken").equals("0")){
                mOrderTakenLayout.setVisibility(View.VISIBLE);
                mEditTextOrderQuantity.setText(getIntent().getStringExtra("OrderQuantity"));
                mEditTextProduct.setText(getIntent().getStringExtra("OrderedProduct"));
            }
            if (!mStatus.equals(VISIT_STATUS_COMPLETED)){
                mVisitStatusLayout.setVisibility(View.VISIBLE);
                mStatus = getString(R.string.default_name);
                populateSpinners();
            }
        }

        readData();
    }

    private void populateSpinners()   {
    mVisitStatusAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mVisitStatusList);

        mVisitStatusSpinner.setAdapter(mVisitStatusAdapter);
        mVisitStatusAdapter.notifyDataSetChanged();
        mVisitStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mStatus = mVisitStatusList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mStatus = getString(R.string.default_name);

            }
        });
    }

    private void initializeViews(){


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mEditTextRepresentative = findViewById(R.id.editTextRepresentative);
        mEditTextJcpId = findViewById(R.id.editTextJcpId);
        mEditTextCustomerName = findViewById(R.id.editTextCustomerName);
        mEditTextDate = findViewById(R.id.editTextDate);
        mEditTextEndTime = findViewById(R.id.editTextEndTime);
        mEditTextStartTime = findViewById(R.id.editTextStartTime);
        mEditTextVisitStatus = findViewById(R.id.editTextStatus);
        mEditTextVisitRemark = findViewById(R.id.editTextRemark);
        mEditTextOrderTaken = findViewById(R.id.editTextOrderTaken);
        mEditTextProduct = findViewById(R.id.editTextProduct);
        mEditTextOrderQuantity = findViewById(R.id.editTextQuantity);
        mVisitStatusSpinner = findViewById(R.id.searchStatus);
        mEditTextObjective = findViewById(R.id.editTextObjective);
        mOrderTakenLayout = findViewById(R.id.orderTakenLayout);
        mVisitStatusLayout = findViewById(R.id.visitStatusLayout);
        mCoordinatorLayout = findViewById(R.id.coordinator);

        mVisitStatusHelper = new MasterHelper(this, MASTER_VISIT_STATUS);
        mVisitStatusList = mVisitStatusHelper.getRecordName();
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
                        fetchData(object);


                    }

                } catch (JSONException e1) {
                    e1.printStackTrace();
                    e1.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewVisitDetailsActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                VolleyLog.d("lol", "Error with Internet : " + error.getMessage());
                // hide the progress dialog
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("x-access-token", new UserInfoHelper(ViewVisitDetailsActivity.this).getUserToken());
                return headers;
            }


        };

        // Adding request to request queue
        queue.add(jsonObjReq);
    }

    private void fetchData(JSONObject object) {
        try {
            if (object.getString("record_id").toLowerCase().equals(getIntent().getStringExtra("JcpId"))) {
                mCustomerId = object.getString("Customer_id") + "";

                mDate = object.getString("Date") + "";
                mObjective = object.getString("Objective") + "";
                mCustomerName = object.getString("customer_name") + "";
                mEndTime = object.getString("end_Time") + "";
                mStartTime = object.getString("start_Time") + "";
            }
            mEditTextDate.setText(mDate);
            mEditTextObjective.setText(mObjective);
            mEditTextCustomerName.setText(mCustomerName);
            mEditTextStartTime.setText(mStartTime);
            mEditTextEndTime.setText(mEndTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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



}
