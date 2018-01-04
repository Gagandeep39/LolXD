package com.example.test.nuvoco3.helpers;

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

import java.util.HashMap;

import static com.example.test.nuvoco3.helpers.Contract.BASE_URL;
import static com.example.test.nuvoco3.helpers.Contract.DISPLAY_CUSTOMER;

/**
 * Created by gagandeep on 29/12/17.
 */

public class CustomerHelper {
    private HashMap<String, String> mCustomerArray;
    private RequestQueue queue;
    private String mCustomer, mId;


    public CustomerHelper(Context mContext) {
        this.mContext = mContext;
    }

    private Context mContext;

    //Makes an Array Based on This (Customer Name or ID)
    private String mType;

    public HashMap<String, String> getCustomerList() {
        queue = Volley.newRequestQueue(mContext);
        mCustomerArray = new HashMap<>();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                BASE_URL + DISPLAY_CUSTOMER, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("lol", "onResponse:  " + response);
                try {
                    JSONArray jsonArray = response.getJSONArray("message");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        mCustomer = object.getString("name");
                        mId = object.getString("record_id");
                        mCustomerArray.put(mId, mCustomer);
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
