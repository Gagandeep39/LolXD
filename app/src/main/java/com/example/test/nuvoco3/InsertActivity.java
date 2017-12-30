package com.example.test.nuvoco3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.nuvoco3.signup.ObjectSerializer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.test.nuvoco3.signup.LoginActivity.DATABASE_URL;

public class InsertActivity extends AppCompatActivity {
    public static final String URL_INSERT_PRODUCT = "/insertMaster";
    private static final String TAG = "Insert Activity";
    TextInputEditText mEditTextType, mEditTextCategory, mEditTextName, mEditTextStatus;
    FloatingActionButton fab;
    String mCategory, mType, mStatus, mName, mUpdatedOn, mCreatedOn, mCreatedBy, mUpdatedBy;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        initializeViews();
        queue = Volley.newRequestQueue(this);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }

    private void validateData() {
        mCategory = mEditTextCategory.getText().toString();
        mName = mEditTextName.getText().toString();
        mStatus = "1";
        mType = mEditTextType.getText().toString();
        mCreatedBy = getUserId();
        mUpdatedBy = getUserId();
        mCreatedOn = getDateTime();
        mUpdatedOn = getDateTime();


        if (TextUtils.isEmpty(mCategory))
            mEditTextCategory.setError("Category field Cannot Be Empty");
        if (TextUtils.isEmpty(mName))
            mEditTextName.setError("name field Cannot Be Empty");
        if (TextUtils.isEmpty(mType))
            mEditTextType.setError("Type field Cannot Be Empty");

        if (!TextUtils.isEmpty(mCategory) && !TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mType)) {
            sendDataToServer();
        }
    }

    private void sendDataToServer() {
        Map<String, String> postParam = new HashMap<>();

//
//        postParam.put("2", mType);
//        postParam.put("3", mCategory);
//        postParam.put("4", mName);
//        postParam.put("5", mStatus);
//        postParam.put("6", mCreatedOn);
//        postParam.put("7", mCreatedBy);
//        postParam.put("8", mUpdatedOn);
//        postParam.put("9", mUpdatedBy);

//
        postParam.put("2", "Category");
        postParam.put("3", "CAT");
        postParam.put("4", "Dealer");
        postParam.put("5", "1");
        postParam.put("6", "2017-12-12");
        postParam.put("7", "101");
        postParam.put("8", "2017-12-12");
        postParam.put("9", "101");


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, DATABASE_URL + URL_INSERT_PRODUCT, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                        try {
                            if (response.getString("status").equals("updated")) {
                                Toast.makeText(InsertActivity.this, "Successfully Inserted Data", Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error with Connection: " + error.getMessage() + "lol");
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

        jsonObjReq.setTag("LOL");
        // Adding request to request queue
        queue.add(jsonObjReq);


    }

    private void initializeViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        mEditTextCategory = findViewById(R.id.textInputEditCategory);
        mEditTextName = findViewById(R.id.textInputEditName);
        mEditTextStatus = findViewById(R.id.textInputEditStatus);
        mEditTextType = findViewById(R.id.textInputEditType);
    }


    //  Function to provide current data and time
    private String getDateTime() {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    // Get LoggedIn users ID
    private String getUserId() {
        ArrayList<String> newArralist = new ArrayList<>();
        // Creates a shared preferences variable to retrieve the logeed in users IDs and store it in Updated By Section
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.test.nuvoco3", Context.MODE_PRIVATE);

        try {
            newArralist = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("CustomerData", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (newArralist.size() > 0)
            return newArralist.get(6);

        return "Invalid User";

    }

}
