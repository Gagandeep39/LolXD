package com.example.test.nuvoco3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

public class ComplaintStatusActivity extends AppCompatActivity {
    SearchableSpinner mSearchCustomer;
    ArrayList<String> mCustomerArrayList;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_status);
        mCustomerArrayList = new ArrayList<>();
        queue = Volley.newRequestQueue(this);
        initializeViews();
        populateSpinner();


    }

    private void populateSpinner() {
        ArrayAdapter mCustomerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mCustomerArrayList);
    }

    private void initializeViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSearchCustomer = findViewById(R.id.searchCustomer);

    }
}
