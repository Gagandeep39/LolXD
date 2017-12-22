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

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "NUVOCO";
    public static String mEmailLogin, mPasswordLogin, mNameLogin, mDepart, mCity, mArea;
    public static String LOGIN_KEY = "loggedin";
    Button buttonSkip, mButtonLogin;
    TextView mTextViewSignUp;
    TextInputEditText mTextInputEditTextEmail, mTextInputEditTextPassword;
    RequestQueue queue;
    ProgressDialog progressDialog;

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
                authenticate();
            }
        });

        progressDialog = new ProgressDialog(this);
    }

    private void authenticate() {
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
    }

    public void signUpFunction(View v) {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
}


    private void makeJsonObjReq() {


        Map<String, String> postParam = new HashMap<String, String>();


        postParam.put("1", mEmailLogin);  //1=>Name, 2.Email, 3.Phone, 4.Age, 5.Address
        postParam.put("2", mPasswordLogin);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, "http://52.38.68.15:8000/auth", new JSONObject(postParam),
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
                                editor.putInt(LOGIN_KEY, 1);
                                editor.apply();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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


    private void makeJsonObjectRequest() {


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                "http://52.38.68.15:8000", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    JSONArray array = response.getJSONArray("message");
                    for (int i = 0; i < 10; i++) {

                        JSONObject object = array.getJSONObject(i);
                        String name = object.getString("u_phone");
                        String email = object.getString("u_email");
                        if (email == "something@mail.com") {
                            String lol = "";

                            lol += "Name: " + name + "\n\n";
                            lol += "Email: " + email + "\n\n";
                            Log.i(TAG, "onResponse: " + lol);
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
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
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
