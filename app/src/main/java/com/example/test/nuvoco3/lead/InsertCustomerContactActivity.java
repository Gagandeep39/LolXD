package com.example.test.nuvoco3.lead;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.nuvoco3.MainActivity;
import com.example.test.nuvoco3.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.test.nuvoco3.signup.LoginActivity.DATABASE_URL;

public class InsertCustomerContactActivity extends AppCompatActivity {
    private static final String TAG = "NUVOCO";
    TextInputEditText editTextContactName, editTextContactPhone, editTextContactEmail, editTextCustomerName, editTextCustomerId;
    TextView textViewDOB, textViewDOA;
    int mYear, mMonth, mDay;
    Bundle bundle;      //Data from another activity
    String mContactName, mCustomerName, mCreatedBy, mCreatedOn, mUpdatedBy, mUpdatedOn, mContactEmail, mContactDOB, mContactDOA, mContactPhone, mCustomerId;  //Customer=>Brand, CustomerContact=>Human
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_customer_contact);
        getReferences();
        queue = Volley.newRequestQueue(this);
        bundle = getIntent().getExtras();
        if (getIntent().getStringExtra("customerName") != null) {

            retrieveCustomerId();
            mCustomerName = bundle.getString("customerName");
            mCreatedBy = bundle.getString("createdBy");
            mCreatedOn = bundle.getString("createdOn");
            mUpdatedBy = bundle.getString("updatedBy");
            mUpdatedOn = bundle.getString("updatedOn");
            editTextCustomerName.setText(mCustomerName);
            editTextCustomerName.setKeyListener(null);

        } else {
            mCustomerName = editTextCustomerName.getText().toString();
            mCreatedBy = "200";
            mCreatedOn = getDateTime();
            mUpdatedBy = "200";
            mUpdatedOn = getDateTime();
        }
    }

    private void insertDataInFields() {
//        Log.i("LOL", "insertData: " + getIntent().getStringExtra("customerName"));

        mContactName = editTextContactName.getText().toString();
        mContactPhone = editTextContactPhone.getText().toString();
        Log.i(TAG, "insertDataInFields: " + mContactPhone);
        mContactEmail = editTextContactEmail.getText().toString();
        Log.i(TAG, "insertDataInFields: " + mContactEmail);
        mContactDOB = textViewDOB.getText().toString();
        mContactDOA = textViewDOA.getText().toString();
        mCustomerId = editTextCustomerId.getText().toString();
        storeData();
    }

    private void getReferences() {
        editTextContactName = findViewById(R.id.textInputEditContactName);
        editTextCustomerName = findViewById(R.id.textInputEditCustomerName);
        editTextContactPhone = findViewById(R.id.textInputEditContactPhone);
        editTextContactEmail = findViewById(R.id.textInputEditContactEmail);
        editTextCustomerId = findViewById(R.id.textInputEditCustomerId);
        textViewDOA = findViewById(R.id.textViewSelectDOA);
        textViewDOB = findViewById(R.id.textViewSelectDOB);
    }

    public void datePickerFunction(View v) {
        final View buttonClicked = v;

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

                        if (buttonClicked.getId()==R.id.textViewSelectDOB)
                            textViewDOB.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        else if (buttonClicked.getId()==R.id.textViewSelectDOA)
                            textViewDOA.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void insertData(View v){
        insertDataInFields();
//        insertData();
//        finish();
//        startActivity(new Intent(InsertCustomerContactActivity.this, MainActivity.class));
    }

    private String getDateTime() {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void storeData() {


        Map<String, String> postParam = new HashMap<>();

//

//        postParam.put("2", mCustomerName);
//        postParam.put("3", mContactName);
//        postParam.put("4", mContactPhone);
//        postParam.put("5", mContactEmail);
//        postParam.put("6", mContactDOB);
//        postParam.put("7", mContactDOA);
//        postParam.put("8", mCreatedOn);
//        postParam.put("9", mCreatedBy);
//        postParam.put("10", mUpdatedOn);
//        postParam.put("11", mUpdatedBy);
//        postParam.put("12", mCustomerId);


        postParam.put("2", mCustomerName);
        postParam.put("3", mContactName);
        postParam.put("4", mContactEmail);
        postParam.put("5", mContactPhone);
        postParam.put("6", mContactDOB);
        postParam.put("7", mContactDOA);
        postParam.put("8", mCreatedOn);
        postParam.put("9", mCreatedBy);
        postParam.put("10", mUpdatedOn);
        postParam.put("11", mUpdatedBy);
        postParam.put("12", mCustomerId);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, DATABASE_URL + "/insertCon", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                        try {
                            if (response.getString("status").equals("updated")) {
                                Toast.makeText(InsertCustomerContactActivity.this, "Successfully Inserted New Contact", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(InsertCustomerContactActivity.this, MainActivity.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error with Connection: " + error.getMessage());
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }


        };

        jsonObjReq.setTag("LOL");
        // Adding request to request queue
        queue.add(jsonObjReq);

    }


    private void retrieveCustomerId() {


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                DATABASE_URL + "/dispCust", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
//                Log.d(TAG, response.toString());    //To check actual response
                String customerEmail = bundle.getString("email");

                try {
                    JSONArray array = response.getJSONArray("message");
                    for (int i = 0; i != 50; i++) {
                        JSONObject object = array.getJSONObject(i);
                        String email = object.getString("c_email");
                        if (Objects.equals(email, customerEmail)) {       //Comparing email ID in database to retrieve user info
                            mCustomerId = object.getString("record_id");
                            editTextCustomerId.setText(mCustomerId);
                            editTextCustomerId.setKeyListener(null);
                            Log.i(TAG, "onResponse: " + mCustomerId);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error with Internet : " + error.getMessage());
                // hide the progress dialog
            }
        });

        // Adding request to request queue
        queue.add(jsonObjReq);
    }





}
