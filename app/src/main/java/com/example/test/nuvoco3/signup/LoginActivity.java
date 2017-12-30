package com.example.test.nuvoco3.signup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.nuvoco3.MainActivity;
import com.example.test.nuvoco3.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "Login Activity";
    public static final String DATABASE_URL = "http://52.38.68.15:8000";
    public static final String LOGIN_KEY = "loggedin";    //Stores login status eg. Keep me Logged in
    public static final String CUSTOMER_DATA = "CustomerData";
    public static String mEmailLogin, mPasswordLogin;
    Button buttonSkip, mButtonLogin;
    TextView mTextViewSignUp;
    TextInputEditText mTextInputEditTextEmail, mTextInputEditTextPassword;
    RequestQueue queue;
    ProgressDialog progressDialog;
    ConstraintLayout mConstraintLayout;
    CheckBox mCheckBoxLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        progressDialog = new ProgressDialog(this);
        queue = Volley.newRequestQueue(this);
        // Checks if User has an Account or Not
        if (checkLoggedIn() == 1) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        //Skip Button to be removed in future
        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
        //Tries user to LogIn into the App
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryLoggingIn();
            }
        });

        progressDialog = new ProgressDialog(this);
    }

    //    Sends Email ID and Password to Server for Authentication
    private void tryLoggingIn() {
        View focusView;
        boolean cancel = false;
        mEmailLogin = mTextInputEditTextEmail.getText().toString();
        mPasswordLogin = mTextInputEditTextPassword.getText().toString();
        if (TextUtils.isEmpty(mEmailLogin)) {
            mTextInputEditTextEmail.setError("Enter Email");
            focusView = mTextInputEditTextEmail;
            focusView.requestFocus();
            cancel = true;
        } else if (!isEmailValid(mEmailLogin)) {
            mTextInputEditTextEmail.setError("Enter a valid Email");
            focusView = mTextInputEditTextEmail;
            focusView.requestFocus();
            cancel = true;
        } else {
            if (TextUtils.isEmpty(mPasswordLogin)) {
                mTextInputEditTextPassword.setError("Enter Password");
                focusView = mTextInputEditTextPassword;
                focusView.requestFocus();
                cancel = true;
            }

        }
        if (!TextUtils.isEmpty(mEmailLogin) && !TextUtils.isEmpty(mPasswordLogin)) {
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            makeJsonObjReq();
        }


    }

    //    initialize Views
    private void findViews() {

        buttonSkip = findViewById(R.id.skip);
        mButtonLogin = findViewById(R.id.buttonLogin);
        mTextViewSignUp = findViewById(R.id.textViewSignUp);
        mTextInputEditTextEmail = findViewById(R.id.textInputEditEmail);
        mTextInputEditTextPassword = findViewById(R.id.textInputEditPassword);
        mCheckBoxLogin = findViewById(R.id.checkbox);
        mConstraintLayout = findViewById(R.id.coordinator);
    }

    //    If user doesn't have an account then he can Sign Up
    public void signUpFunction(View v) {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }

    //    Actual authentication function which gives us a response code on connection to server
    private void makeJsonObjReq() {
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                Snackbar snackbar = Snackbar.make(mConstraintLayout, "Connection Time-out !", Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                snackbar.show();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 20000);



        Map<String, String> postParam = new HashMap<>();


        postParam.put("1", mEmailLogin);  //1=>Name, 2.Email, 3.Phone, 4.Age, 5.Address
        postParam.put("2", mPasswordLogin);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, DATABASE_URL + "/auth", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            if (response.getString("status").equals("not_existing"))
                                Toast.makeText(LoginActivity.this, "Account does't Exist", Toast.LENGTH_SHORT).show();
                            else if (response.getString("status").equals("unsuccessful"))
                                Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                            else if (response.getString("status").contains("successful")) {
                                //Store Logged in User Data that is received from Response Code
                                ArrayList<String> set = new ArrayList<>();
                                set.add(response.getString("u_add"));
                                set.add(response.getString("u_age"));
                                set.add(response.getString("u_area"));
                                set.add(response.getString("u_city"));
                                set.add(response.getString("u_department"));
                                set.add(response.getString("u_email"));
                                set.add(response.getString("u_id"));
                                set.add(response.getString("u_name"));
                                set.add(response.getString("u_phone"));
                                set.add(response.getString("u_pincode"));
                                set.add(response.getString("u_status"));
                                set.add(1 + "");

                                SharedPreferences sharedPreferences = getSharedPreferences("com.example.test.nuvoco3", Context.MODE_PRIVATE);

                                try {
                                    sharedPreferences.edit().putString(CUSTOMER_DATA, ObjectSerializer.serialize(set)).apply();
                                    sharedPreferences.edit().apply();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i(TAG, "Response Code : " + response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error while making database connection : " + error.getMessage());
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }


        };
        // Adding request to request queue
        queue.add(jsonObjReq);

    }

    // Allows user to login without credentials if already had Logged in before
    private int checkLoggedIn() {
        ArrayList<String> newArralist = new ArrayList<>();
        // Creates a shared preferences variable to retrieve the logeed in users IDs and store it in Updated By Section
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.test.nuvoco3", Context.MODE_PRIVATE);

        try {
            newArralist = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("CustomerData", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (newArralist.size() != 0)
            return Integer.parseInt(newArralist.get(11));
        return 0;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }


}
