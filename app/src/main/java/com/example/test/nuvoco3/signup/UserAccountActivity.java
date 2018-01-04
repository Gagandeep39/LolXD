package com.example.test.nuvoco3.signup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.test.nuvoco3.R;
import com.example.test.nuvoco3.helpers.UserInfoHelper;

import static com.example.test.nuvoco3.helpers.Contract.USER_ADDRESS;
import static com.example.test.nuvoco3.helpers.Contract.USER_AGE;
import static com.example.test.nuvoco3.helpers.Contract.USER_AREA;
import static com.example.test.nuvoco3.helpers.Contract.USER_CITY;
import static com.example.test.nuvoco3.helpers.Contract.USER_DEPARTMENT;
import static com.example.test.nuvoco3.helpers.Contract.USER_EMAIL;
import static com.example.test.nuvoco3.helpers.Contract.USER_ID;
import static com.example.test.nuvoco3.helpers.Contract.USER_NAME;
import static com.example.test.nuvoco3.helpers.Contract.USER_PHONE_NO;
import static com.example.test.nuvoco3.helpers.Contract.USER_PIN;
import static com.example.test.nuvoco3.helpers.Contract.USER_STATUS;

public class UserAccountActivity extends AppCompatActivity {
    private static final String TAG = "UserAccount Activity";
    TextView mName, mPhone, mEmail, mArea, mCity, mState, mStatus, mPin, mDepartment, mAge, mId, mAddress;

    UserInfoHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mHelper = new UserInfoHelper(this);
        initializeViews();

        displayData();
    }

    private void displayData() {
//        ArrayList<String> newArralist = new ArrayList<>();
//        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.test.nuvoco3", Context.MODE_PRIVATE);
//
//        try {
//            newArralist = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("CustomerData", ObjectSerializer.serialize(new ArrayList<String>())));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        if (newArralist.size() < 0) {
//            Log.i(TAG, "displayData: " + "user Not Logged In");
//        } else {

            //Shows user Information
        mAddress.append(mHelper.getUserInfo(USER_ADDRESS));
        mAge.append(mHelper.getUserInfo(USER_AGE));
        mArea.append(mHelper.getUserInfo(USER_AREA));
        mCity.append(mHelper.getUserInfo(USER_CITY));
        mDepartment.append(mHelper.getUserInfo(USER_DEPARTMENT));
        mEmail.append(mHelper.getUserInfo(USER_EMAIL));
        mId.append(mHelper.getUserInfo(USER_ID));
        mName.append(mHelper.getUserInfo(USER_NAME));
        mPhone.append(mHelper.getUserInfo(USER_PHONE_NO));
        mPin.append(mHelper.getUserInfo(USER_PIN));
        mStatus.append(mHelper.getUserInfo(USER_STATUS));
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
