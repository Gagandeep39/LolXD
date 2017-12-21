package com.example.test.nuvoco3.signup;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.nuvoco3.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    FloatingActionButton fab;
    TextInputEditText mTextInputEditTextName, mTextInputEditTextPhone, mTextInputEditTextEmail, mTextInputEditTextAddress, mTextInputEditTextPass1, mTextInputEditTextPass2, mTextInputEditTextAge, mTextInputEditTextPin;
    String mName, mEmail, mPhone, mAddress, mPassword1, mPassword2, mCity, mArea, mDepartment;
    SearchableSpinner mSearchableSpinnerDepartment, mSearchableSpinnerArea, mSearchableSpinnerCity;
    int mPin = 0, mAge = 0, mStatus = 1;
    String departmentTypes[] = {"Department 1", "Department 2", "Department 3", "Department 4"};
    String areaTypes[] = {"Area 1", "Area 2", "Area 3", "Area 4"};
    String cityTypes[] = {"Mumbai", "Pune", "Thane", "Chandigarh"};
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        queue = Volley.newRequestQueue(this);
        initializeViews();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeOnClicks();


    }

    private void initializeOnClicks() {
        ArrayAdapter mDepartmentAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, departmentTypes);
        ArrayAdapter mCityAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cityTypes);
        ArrayAdapter mAreaAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, areaTypes);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });

        mSearchableSpinnerDepartment.setAdapter(mDepartmentAdapter);
        mSearchableSpinnerCity.setAdapter(mCityAdapter);
        mSearchableSpinnerArea.setAdapter(mAreaAdapter);

        mSearchableSpinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDepartment = departmentTypes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSearchableSpinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mArea = areaTypes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSearchableSpinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCity = cityTypes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void insertData() {
        String mPinString = mTextInputEditTextPin.getText().toString();
        String mAgeString = mTextInputEditTextPhone.getText().toString();
        if (TextUtils.isEmpty(mPinString))
            Toast.makeText(this, "Insert Pin", Toast.LENGTH_SHORT).show();
        else
            mPin = Integer.parseInt(mPinString);

        if (TextUtils.isEmpty(mAgeString))
            Toast.makeText(this, "Insert Age", Toast.LENGTH_SHORT).show();
        else
            mAge = Integer.parseInt(mAgeString);


        mName = mTextInputEditTextName.getText().toString();
        mPhone = mTextInputEditTextPhone.getText().toString();
        mEmail = mTextInputEditTextEmail.getText().toString();
        mAddress = mTextInputEditTextAddress.getText().toString();
        mPassword1 = mTextInputEditTextPass1.getText().toString();
        mPassword2 = mTextInputEditTextPass2.getText().toString();

//        postRequest();
        makeJsonObjReq();
    }

    private void initializeViews() {
        fab = findViewById(R.id.fab);
        mTextInputEditTextName = findViewById(R.id.textInputEditName);
        mTextInputEditTextPhone = findViewById(R.id.textInputEditPhone);
        mTextInputEditTextEmail = findViewById(R.id.textInputEditEmail);
        mTextInputEditTextAddress = findViewById(R.id.textInputEditAddress);
        mTextInputEditTextPass1 = findViewById(R.id.textInputEditPassword1);
        mTextInputEditTextPass2 = findViewById(R.id.textInputEditPassword2);
        mTextInputEditTextAge = findViewById(R.id.textInputEditAge);
        mTextInputEditTextPin = findViewById(R.id.textInputEditPin);
        mSearchableSpinnerDepartment = findViewById(R.id.searchDepartment);
        mSearchableSpinnerArea = findViewById(R.id.searchArea);
        mSearchableSpinnerCity = findViewById(R.id.searchCity);
    }

    void postRequest() {
        String url = "https://35.165.145.61:8000/insert";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", "Error while inserting");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("1", mName);
                params.put("2", mEmail);
                params.put("3", mPhone);
                params.put("4", mAge + "");
                params.put("5", mArea);
                params.put("6", mCity);
                params.put("7", mAddress);
                params.put("8", mPin + "");
                params.put("9", mDepartment);
                params.put("10", mPassword1);
                params.put("11", mStatus + "");

                return params;
            }
        };
        queue.add(postRequest);
        Log.i("lol", "postRequest: " + "Successfull");
    }


    private void makeJsonObjReq() {


        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("1", mName);
        postParam.put("2", mEmail);
        postParam.put("3", mPhone);
        postParam.put("4", mAge + "");
        postParam.put("5", mArea);
        postParam.put("6", mCity);
        postParam.put("7", mAddress);
        postParam.put("8", mPin + "");
        postParam.put("9", mDepartment);
        postParam.put("10", mPassword1);
        postParam.put("11", mStatus + "");

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, "http://35.165.145.61:8000/insert", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("LOL", response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", "Error: " + error.getMessage());
            }
        }) {

            /**
             * Passing some request headers
             * */
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

        // Cancelling request
    /* if (queue!= null) {
    queue.cancelAll(TAG);
    } */

    }
}
