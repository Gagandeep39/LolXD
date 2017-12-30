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

public class MasterHelper {
    private ArrayList<String> mRecordNameList;
    private Context mContext;
    private String mType;
    private RequestQueue queue;
    private String URL_VIEW_RECORD_MASTER = "/dispRecMaster";
    private String mRecordName;


    public MasterHelper(Context mContext, String mType) {
        this.mContext = mContext;
        this.mType = mType;
    }


    public ArrayList<String> getRecordName() {
        mRecordNameList = new ArrayList<>();
        queue = Volley.newRequestQueue(mContext);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                DATABASE_URL + URL_VIEW_RECORD_MASTER, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("lol", "onResponse:  " + response);
                try {
                    JSONArray jsonArray = response.getJSONArray("message");
                    for (int i = 0; i < 50; i++) {
                        if (response.getString("r_type").equals(mType)) {

                            JSONObject object = jsonArray.getJSONObject(i);
                            mRecordName = object.getString("r_name");
                            mRecordNameList.add(mRecordName);

                        }
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
        });

        // Adding request to request queue
        queue.add(jsonObjReq);


        return mRecordNameList;
    }

}
