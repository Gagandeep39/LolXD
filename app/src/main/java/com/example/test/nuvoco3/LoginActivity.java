package com.example.test.nuvoco3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.test.nuvoco3.signup.SignUpActivity;

public class LoginActivity extends AppCompatActivity {
    Button buttonSkip;
    TextView mTextViewSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

    }

    private void findViews() {

        buttonSkip = findViewById(R.id.skip);
        mTextViewSignUp = findViewById(R.id.textViewSignUp);
    }

    public void signUpFunction(View v) {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
}
}
