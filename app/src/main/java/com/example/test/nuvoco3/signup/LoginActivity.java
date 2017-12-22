package com.example.test.nuvoco3.signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "NUVOCO";
    public static final String DATABASE_URL = "http://52.38.68.15:8000";
    public static String mEmailLogin, mPasswordLogin, mNameLogin, mDepartmentLogin, mCityLogin, mAreaLogin, mPhoneLogin;  //Stores logged in User's Information
    public static String LOGIN_KEY = "loggedin";    //Stores login status eg. Keep me Logged in
    int mIdLogin;   //Stores Logged in user data
    Button buttonSkip, mButtonLogin;
    TextView mTextViewSignUp;
    TextInputEditText mTextInputEditTextEmail, mTextInputEditTextPassword;
    RequestQueue queue;
    ProgressDialog progressDialog;
    CheckBox mCheckBoxLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        queue = Volley.newRequestQueue(this);
        if (checkLoggedIn() == 1) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryLoggingIn();
            }
        });

        progressDialog = new ProgressDialog(this);
    }

    private void tryLoggingIn() {
        mEmailLogin = mTextInputEditTextEmail.getText().toString();
        mPasswordLogin = mTextInputEditTextPassword.getText().toString();
        if (TextUtils.isEmpty(mEmailLogin))
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(mPasswordLogin))
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        if (!TextUtils.isEmpty(mEmailLogin) && !TextUtils.isEmpty(mPasswordLogin)) {
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            makeJsonObjReq();
        }



    }

    private void findViews() {

        buttonSkip = findViewById(R.id.skip);
        mButtonLogin = findViewById(R.id.buttonLogin);
        mTextViewSignUp = findViewById(R.id.textViewSignUp);
        mTextInputEditTextEmail = findViewById(R.id.textInputEditEmail);
        mTextInputEditTextPassword = findViewById(R.id.textInputEditPassword);
        mCheckBoxLogin = findViewById(R.id.checkbox);
    }

    public void signUpFunction(View v) {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
}


    private void makeJsonObjReq() {


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
                            else if (response.getString("status").equals("successful")) {
                                SharedPreferences.Editor editor = getSharedPreferences("LoginCode", MODE_PRIVATE).edit();
                                if (mCheckBoxLogin.isChecked()) {
                                    editor.putInt(LOGIN_KEY, 1);
                                    editor.apply();
                                } else {
                                    editor.putInt(LOGIN_KEY, 0);
                                    editor.apply();
                                }
                                storeLoggedInUserInfo();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i(TAG, "Error while getting Response Code : " + response.toString());
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


    private void storeLoggedInUserInfo() {


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                DATABASE_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
//                Log.d(TAG, response.toString());    //To check actual response

                try {
                    JSONArray array = response.getJSONArray("message");
                    for (int i = 0; i < 10; i++) {
                        JSONObject object = array.getJSONObject(i);
                        String email = object.getString("u_email");
                        if (Objects.equals(email, mEmailLogin)) {       //Comparing email ID in database to retrieve user info
                            mNameLogin = object.getString("u_name");
                            mAreaLogin = object.getString("u_area");
                            mPhoneLogin = object.getString("u_phone");
                            mCityLogin = object.getString("u_city");
                            mIdLogin = object.getInt("u_id");
                            mDepartmentLogin = object.getString("u_department");
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

    private int checkLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginCode", MODE_PRIVATE);
        int code = sharedPreferences.getInt(LOGIN_KEY, 0);
        return code;
    }


}
