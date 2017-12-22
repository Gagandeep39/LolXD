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
import com.android.volley.toolbox.Volley;
import com.example.test.nuvoco3.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.test.nuvoco3.signup.LoginActivity.DATABASE_URL;

public class SignUpActivity extends AppCompatActivity {
    public static final String TAG = "NUVOCO";
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
        String mAgeString = mTextInputEditTextAge.getText().toString();
        if (TextUtils.isEmpty(mPinString))
            Toast.makeText(this, "Insert Pin", Toast.LENGTH_SHORT).show();
        else
            mPin = Integer.parseInt(mPinString);

        if (TextUtils.isEmpty(mAgeString))
            Toast.makeText(this, "Insert Age", Toast.LENGTH_SHORT).show();
        else
            mAge = Integer.parseInt(mAgeString);


        mName = mTextInputEditTextName.getText().toString();
        if (TextUtils.isEmpty(mName))
            Toast.makeText(this, "Name field cannot be blank", Toast.LENGTH_SHORT).show();

        mPhone = mTextInputEditTextPhone.getText().toString();
        if (TextUtils.isEmpty(mPhone))
            Toast.makeText(this, "You need to enter phone number", Toast.LENGTH_SHORT).show();
        mEmail = mTextInputEditTextEmail.getText().toString();
        if (TextUtils.isEmpty(mEmail))
            Toast.makeText(this, "You need to enter Email", Toast.LENGTH_SHORT).show();
        mAddress = mTextInputEditTextAddress.getText().toString();
        if (TextUtils.isEmpty(mAddress))
            Toast.makeText(this, "You need to enter Address", Toast.LENGTH_SHORT).show();

        mPassword1 = mTextInputEditTextPass1.getText().toString();
        if (TextUtils.isEmpty(mPhone))
            Toast.makeText(this, "You need to Enter Password", Toast.LENGTH_SHORT).show();
        else {
            mPassword2 = mTextInputEditTextPass2.getText().toString();
            if (TextUtils.isEmpty(mPhone))
                Toast.makeText(this, "You need to Re-enter Password", Toast.LENGTH_SHORT).show();
            else if (!mPassword1.equals(mPassword2))
                Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(mArea))
            Toast.makeText(this, "Select Area", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(mCity))
            Toast.makeText(this, "Select City", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(mDepartment))
            Toast.makeText(this, "Select Department", Toast.LENGTH_SHORT).show();

        if (!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mAge + "") && !TextUtils.isEmpty(mPhone) && !TextUtils.isEmpty(mEmail) && !TextUtils.isEmpty(mAddress) && !TextUtils.isEmpty(mPassword1) && !TextUtils.isEmpty(mArea) && !TextUtils.isEmpty(mCity) && !TextUtils.isEmpty(mDepartment) && !TextUtils.isEmpty(mAge + ""))
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




    private void makeJsonObjReq() {


        Map<String, String> postParam = new HashMap<String, String>();


        postParam.put("1", mName);  //1=>Name, 2.Email, 3.Phone, 4.Age, 5.Address
        postParam.put("2", mEmail); //6=>area, 7.city, 8pincode, 9.department, 10. Password
        postParam.put("3", mPhone + "");    //11.status
        postParam.put("4", mAge + "");
        postParam.put("9", mDepartment);
        postParam.put("10", mPassword1);
        postParam.put("11", mStatus + "");
        postParam.put("5", mAddress);   //5=>Address
        postParam.put("8", mPin + "");
        postParam.put("7", mCity);
        postParam.put("6", mArea);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, DATABASE_URL + "/insert", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
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