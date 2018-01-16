package com.example.test.nuvoco3.helpers;

import android.app.ProgressDialog;
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
import java.util.HashMap;
import java.util.Map;

import static com.example.test.nuvoco3.helpers.Contract.BASE_URL;
import static com.example.test.nuvoco3.helpers.Contract.DISPLAY_BRANDS;
import static com.example.test.nuvoco3.lead.InsertCustomerActivity.TAG;

public class ProductHelper {

    private ArrayList<String> mProductNameList;
    private Context mContext;
    private String mType;
    private RequestQueue queue;
    private String mRecordName;
    ProgressDialog progressDialog;

    public ProductHelper(Context mContext, String mType) {
        this.mContext = mContext;
        this.mType = mType;
    }

    public ArrayList<String> getRecordName() {
        progressDialog = new ProgressDialog(mContext);
        mProductNameList = new ArrayList<>();
        queue = Volley.newRequestQueue(mContext);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                BASE_URL + DISPLAY_BRANDS + "/" + mType, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("lol", "onResponse:  " + response);
                try {
                    JSONArray jsonArray = response.getJSONArray("message");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        mRecordName = object.getString("product");
                        mProductNameList.add(mRecordName);
                        Log.i(TAG, "onResponse: " + mRecordName);

//                        }
                    }

                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("lol", "Error with Internet : " + error.getMessage());
                // hide the progress dialog
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("x-access-token", new UserInfoHelper(mContext).getUserToken());
                return headers;
            }


        };

        // Adding request to request queue
        queue.add(jsonObjReq);

//            progressDialog.dismiss();
        Log.d(TAG, "getRecordName: i" +"deftg;");
            return mProductNameList;
    }

}
