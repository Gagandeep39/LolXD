package com.example.test.nuvoco3.signup;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.test.nuvoco3.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class SignUpActivity extends AppCompatActivity {
    FloatingActionButton fab;
    TextInputEditText mTextInputEditTextName, mTextInputEditTextPhone, mTextInputEditTextEmail, mTextInputEditTextAddress, mTextInputEditTextPass1, mTextInputEditTextPass2, mTextInputEditTextAge, mTextInputEditTextPin;
    String mName, mPhone, mEmail, mAddress, mPassword1, mPassword2, mAge, mPin, mCity, mArea, mDepartment, mStatus;
    SearchableSpinner mSearchableSpinnerDepartment, mSearchableSpinnerArea, mSearchableSpinnerCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initializeViews();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initializeViews() {
        fab = findViewById(R.id.fab);
        mTextInputEditTextName = findViewById(R.id.textInputEditName);
        mTextInputEditTextPhone = findViewById(R.id.textInputEditPhone);
        mTextInputEditTextEmail = findViewById(R.id.textInputEditEmail);
        mTextInputEditTextAddress = findViewById(R.id.textInputEditAddress);
        mTextInputEditTextPass1 = findViewById(R.id.textInputEditPassword1);
        mTextInputEditTextPass2 = findViewById(R.id.textInputEditPassword2);
        mTextInputEditTextAge = findViewById(R.id.textInputEditAge);
        mTextInputEditTextPin = findViewById(R.id.textInputEditPin);
        mSearchableSpinnerDepartment = findViewById(R.id.searchDepartment);
        mSearchableSpinnerArea = findViewById(R.id.searchArea);
        mSearchableSpinnerCity = findViewById(R.id.searchCity);
    }

}
