package com.example.test.nuvoco3.signup;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.test.nuvoco3.signup.LoginActivity.DATABASE_URL;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUp Activity";
    FloatingActionButton fab;
    TextInputEditText mTextInputEditTextName, mTextInputEditTextPhone, mTextInputEditTextEmail, mTextInputEditTextAddress, mTextInputEditTextPass1, mTextInputEditTextPass2, mTextInputEditTextAge, mTextInputEditTextPin;
    String mName, mEmail, mPhone, mAddress, mPassword1, mPassword2, mCity, mArea, mDepartment;
    SearchableSpinner mSearchableSpinnerDepartment, mSearchableSpinnerArea, mSearchableSpinnerCity;
    int mPin = 0, mAge = 0, mStatus = 1;
    String departmentTypes[] = {"Department 1", "Department 2", "Department 3", "Department 4"};
    String areaTypes[] = {"Area 1", "Area 2", "Area 3", "Area 4"};
    String cityTypes[] = {"Mumbai", "Pune", "Thane", "Chandigarh"};
    RequestQueue queue;
    CoordinatorLayout mCoordinaterLayout;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        progressDialog = new ProgressDialog(this);
        queue = Volley.newRequestQueue(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeViews();
        initializeOnClicks();


    }

    //  Populates Spinners
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

                mDepartment = getString(R.string.default_name);
            }
        });
        mSearchableSpinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mArea = areaTypes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                mArea = getString(R.string.default_name);
            }
        });
        mSearchableSpinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCity = cityTypes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCity = getString(R.string.default_name);
            }
        });
    }

    //  Performs A check if any field is left Empty
    private void insertData() {
        View focusView;
        String mPinString = mTextInputEditTextPin.getText().toString();
        String mAgeString = mTextInputEditTextAge.getText().toString();
        mName = mTextInputEditTextName.getText().toString();
        mPhone = mTextInputEditTextPhone.getText().toString();
        mEmail = mTextInputEditTextEmail.getText().toString();
        mAddress = mTextInputEditTextAddress.getText().toString();
        mPassword1 = mTextInputEditTextPass1.getText().toString();
        mPassword2 = mTextInputEditTextPass2.getText().toString();

        if (TextUtils.isEmpty(mPinString)) {
            mTextInputEditTextPin.setError("Enter Pincode");
        } else {
            mPin = Integer.parseInt(mPinString);
        }
//        if (TextUtils.isEmpty(mAgeString)) {
//            mTextInputEditTextAge.setError("Enter Age");
//        } else {
//            mAge = Integer.parseInt(mAgeString);
//        }
        if (TextUtils.isEmpty(mName))
            mTextInputEditTextName.setError("Enter Name");
        if (TextUtils.isEmpty(mPhone))
            mTextInputEditTextPhone.setError("Enter Phone number");
        if (TextUtils.isEmpty(mEmail)) {

            mTextInputEditTextEmail.setError("Enter Email");
        } else if (!isEmailValid(mEmail)) {
            mTextInputEditTextEmail.setError("Invalid Email");
        }
        if (TextUtils.isEmpty(mAddress))
            mTextInputEditTextAddress.setError("Enter Address");

        //Check Passoword
        if (TextUtils.isEmpty(mPassword1))
            mTextInputEditTextPass1.setError("Enter Password");
        else {
            if (TextUtils.isEmpty(mPassword2))
                mTextInputEditTextPass2.setError("Re-Enter Password");
            else if (!TextUtils.equals(mPassword1, mPassword2))
                mTextInputEditTextPass2.setError("Both the Passwords are different");
        }
        if (TextUtils.isEmpty(mPassword2))
            mTextInputEditTextPass2.setError("Re-Enter Password");

        if (TextUtils.equals(mArea, getString(R.string.default_name)))
            Toast.makeText(this, "Select Area", Toast.LENGTH_SHORT).show();
        if (TextUtils.equals(mCity, getString(R.string.default_name)))
            Toast.makeText(this, "Select City", Toast.LENGTH_SHORT).show();
        if (TextUtils.equals(mDepartment, getString(R.string.default_name)))
            Toast.makeText(this, "Select Department", Toast.LENGTH_SHORT).show();


        if (!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mPhone) && isEmailValid(mEmail) && !TextUtils.isEmpty(mEmail) && !TextUtils.isEmpty(mAddress) && !TextUtils.isEmpty(mPassword1) && TextUtils.equals(mPassword1, mPassword2) && !TextUtils.equals(mArea, getString(R.string.default_name)) && !TextUtils.equals(mCity, getString(R.string.default_name)) && !TextUtils.equals(mDepartment, getString(R.string.default_name)))
            makeJsonObjReq();
    }

    //  Initialize Views
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
        mCoordinaterLayout = findViewById(R.id.coordinator);
        mSearchableSpinnerArea = findViewById(R.id.searchArea);
        mSearchableSpinnerCity = findViewById(R.id.searchCity);
    }


    //  Stores User data on Server on Successful Account Creation
    private void makeJsonObjReq() {
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                Snackbar snackbar = Snackbar.make(mCoordinaterLayout, "Connection Time-out !", Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                snackbar.show();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 20000);



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
                        try {
                            if (response.getString("status").equals("updated")) {
                                progressDialog.dismiss();
                                Toast.makeText(SignUpActivity.this, "Successfully Signed Up", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }


        };

        jsonObjReq.setTag(TAG);
        // Adding request to request queue
        queue.add(jsonObjReq);
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }


}