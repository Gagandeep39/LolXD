package com.example.test.nuvoco3.signup;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.test.nuvoco3.R;

import java.io.IOException;
import java.util.ArrayList;

public class UserAccountActivity extends AppCompatActivity {
    TextView mName, mPhone, mEmail, mArea, mCity, mState, mStatus, mPin, mDepartment, mAge, mId, mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initializeViews();

        displayData();
    }

    private void displayData() {
        ArrayList<String> newArralist = new ArrayList<>();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.test.nuvoco3", Context.MODE_PRIVATE);

        try {
            newArralist = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("CustomerData", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //I edit the prefs and add my string set and label it as "List"
        mAddress.append(newArralist.get(0) + "");
        mAge.append(newArralist.get(1) + "");
        mArea.append(newArralist.get(2) + "");
        mCity.append(newArralist.get(3) + "");
        mDepartment.append(newArralist.get(4) + "");
        mEmail.append(newArralist.get(5) + "");
        mId.append(newArralist.get(6) + "");
        mName.append(newArralist.get(7) + "");
        mPhone.append(newArralist.get(8) + "");
        mPin.append(newArralist.get(9) + "");
        mStatus.append(newArralist.get(10) + "");

    }

    private void initializeViews() {
        mName = findViewById(R.id.textViewName);
        mAge = findViewById(R.id.textViewAge);
        mPhone = findViewById(R.id.textViewPhone);
        mEmail = findViewById(R.id.textViewEmail);
        mCity = findViewById(R.id.textViewCity);
        mArea = findViewById(R.id.textViewArea);
        mDepartment = findViewById(R.id.textViewDepartment);
//        mState = findViewById(R.id.textViewState);
        mStatus = findViewById(R.id.textViewStatus);
        mAddress = findViewById(R.id.textViewAddress);
        mId = findViewById(R.id.textViewId);
        mPin = findViewById(R.id.textViewPincode);
    }

}
