package com.example.test.nuvoco3;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.test.nuvoco3.signup.LoginActivity.DATABASE_URL;

/**
 * Created by gagandeep on 29/12/17.
 */

public class CustomerHelper {
    ArrayList<String> mCustomerArray;
    RequestQueue queue;
    private String mCustomer;
    private Context mContext;

    public CustomerHelper(Context mContext) {
        this.mContext = mContext;
    }

    public ArrayList<String> getCustomerList() {
        queue = Volley.newRequestQueue(mContext);
        mCustomerArray = new ArrayList<>();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                DATABASE_URL + "/dispCust", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("lol", "onResponse:  " + response);
                try {
                    JSONArray jsonArray = response.getJSONArray("message");
                    for (int i = 0; i < 50; i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        mCustomer = object.getString("name");
                        mCustomerArray.add(mCustomer);
                    }

                } catch (JSONException e1) {
                    e1.printStackTrace();
                    e1.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("lol", "Error with Internet : " + error.getMessage());
                // hide the progress dialog
            }
        });

        // Adding request to request queue
        queue.add(jsonObjReq);


        return mCustomerArray;
    }

}
