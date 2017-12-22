package com.example.test.nuvoco3.lead;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.test.nuvoco3.MainActivity;
import com.example.test.nuvoco3.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InsertCustomerContactActivity extends AppCompatActivity {
    TextInputEditText editTextContactName, editTextContactPhone, editTextContactEmail;
    TextView textViewDOB, textViewDOA;
    int mYear, mMonth, mDay;
    Bundle bundle;      //Data from another activity
    String mContactName, mCreatedBy, mCreatedOn, mUpdatedBy, mUpdatedOn, mContactEmail, mContactDOB, mContactDOA, mContactPhone;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_customer_contact);
        getReferences();
        queue = Volley.newRequestQueue(this);
        bundle = getIntent().getExtras();
    }

    private void insertData() {
        if (bundle != null) {

            mContactName = bundle.getString("customerName");
            mCreatedBy = bundle.getString("createdBy");
            mCreatedOn = bundle.getString("createdOn");
            mUpdatedBy = bundle.getString("updatedBy");
            mUpdatedOn = bundle.getString("updatedOn");
            editTextContactName.setText(mContactName);

        } else {
            mContactName = editTextContactName.getText().toString();
            mCreatedBy = "200";
            mCreatedOn = getDateTime();
            mUpdatedBy = "200";
            mUpdatedOn = getDateTime();
        }

        mContactPhone = editTextContactPhone.getText().toString();
        mContactEmail = editTextContactEmail.getText().toString();
        mContactDOB = textViewDOB.getText().toString();
        mContactDOA = textViewDOA.getText().toString();

    }

    private void getReferences() {
        editTextContactName = findViewById(R.id.textInputEditEmployName);
        editTextContactPhone = findViewById(R.id.textInputEditEmployPhone);
        editTextContactEmail = findViewById(R.id.textInputEditEmployEmail);
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
                            textViewDOB.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        else if (buttonClicked.getId()==R.id.textViewSelectDOA)
                            textViewDOA.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void insertData(View v){

        insertData();
        finish();
        startActivity(new Intent(InsertCustomerContactActivity.this, MainActivity.class));
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

//    private void storeData() {
//
//
//        Map<String, String> postParam = new HashMap<>();
//
////
//        postParam.put("1", mName);
//        postParam.put("2", mType);
//        postParam.put("3", mCategory);
//        postParam.put("4", mAddress);
//        postParam.put("5", mArea);
//        postParam.put("6", mDistrict);
//        postParam.put("7", mState);
//        postParam.put("8", mPhone);
//        postParam.put("9", mEmail);
//        postParam.put("10", mStatus);
//        postParam.put("11", mCreatedOn);
//        postParam.put("12", mCreatedBy);
//        postParam.put("13", mUpdatedOn);
//        postParam.put("14", mUpdatedBy);
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, DATABASE_URL + "/insertCus", new JSONObject(postParam),
//                new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.i(TAG,  response.toString());
//                        try {
//                            if (response.getString("status").equals("updated")){
//                                Toast.makeText(InsertCustomerActivity.this, "Successfully Inserted data", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(InsertCustomerActivity.this, InsertCustomerContactActivity.class);
//                                intent.putExtra("customerName", mName);
//                                intent.putExtra("createdBy", mCreatedBy);
//                                intent.putExtra("createdOn", mCreatedOn);
//                                intent.putExtra("updatedBy", mUpdatedBy);
//                                intent.putExtra("updatedOn", mUpdatedOn);
//                                startActivity(intent);
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error with Connection: " + error.getMessage());
//            }
//        }) {
//
//            /**
//             * Passing some request headers
//             * */
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json; charset=utf-8");
//                return headers;
//            }
//
//
//        };
//
//        jsonObjReq.setTag("LOL");
//        // Adding request to request queue
//        queue.add(jsonObjReq);
//
//        // Cancelling request
//    /* if (queue!= null) {
//    queue.cancelAll(TAG);
//    } */
//
//    }


}
