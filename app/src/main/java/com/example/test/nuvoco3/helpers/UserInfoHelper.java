package com.example.test.nuvoco3.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.test.nuvoco3.signup.ObjectSerializer;

import java.io.IOException;
import java.util.ArrayList;

public class UserInfoHelper {
    private ArrayList<String> userArrayList;
    private SharedPreferences sharedPreferences;
    private Context mContext;

    public UserInfoHelper(Context mContext) {
        this.mContext = mContext;
    }

    public String getUserInfo(int mInfoType) {
        userArrayList = new ArrayList<>();
        sharedPreferences = mContext.getSharedPreferences("com.example.test.nuvoco3", Context.MODE_PRIVATE);

        try {
            userArrayList = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("CustomerData", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String mUserInfo;
        mUserInfo = userArrayList.get(mInfoType) + "";
        return mUserInfo;
    }

    public static boolean isEmailValid(String mEmail) {
        return mEmail.contains("@");
    }
}
