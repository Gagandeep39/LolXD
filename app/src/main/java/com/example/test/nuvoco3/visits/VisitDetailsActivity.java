package com.example.test.nuvoco3.visits;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.test.nuvoco3.R;
import com.example.test.nuvoco3.helpers.MasterHelper;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

public class VisitDetailsActivity extends AppCompatActivity {
    FloatingActionButton fab;
    boolean isChecked = false;
    TextInputEditText mEditTextRepresentative, mEditTextJcpId, mEditTextCustomerId, mEditTextCustomerName, mEditTextRemark, mEditTextStartTime, mEditTextEndTime, mEditTextDate, mEditTextQuantity;
    SearchableSpinner mStatusSpinner;
    CheckBox mCheckBoxOrder;
    String mStatus;

    MasterHelper mStatusHelper;
    ArrayList<String> mStatusArrayList;
    ArrayAdapter mStatusAdapter;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_details);
        initializeViews();
        initializeVariables();
        populateSpinner();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }

    private void validateData() {
    }

    private void populateSpinner() {
        mStatusAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mStatusArrayList);
        mStatusSpinner.setAdapter(mStatusAdapter);
        mStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mStatus = mStatusArrayList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mStatus = getString(R.string.default_name);
            }
        });
    }

    private void initializeVariables() {
        mStatusHelper = new MasterHelper(this, "VisitStatus");
        mStatusArrayList = mStatusHelper.getRecordName();

        queue = Volley.newRequestQueue(this);

        if (getIntent().getStringExtra("CustomerName") != null) {

        }
    }

    private void initializeViews() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        fab = findViewById(R.id.fab);
        mEditTextRepresentative = findViewById(R.id.textInputEditName);
        mEditTextJcpId = findViewById(R.id.textInputEditJcpId);
        mEditTextCustomerId = findViewById(R.id.textInputEditCustomerId);
        mEditTextCustomerName = findViewById(R.id.textInputEditCustomerName);
        mEditTextDate = findViewById(R.id.textInputEditDate);
        mEditTextStartTime = findViewById(R.id.textInputLayoutStartTime);
        mEditTextEndTime = findViewById(R.id.textInputLayoutEndTime);
        mEditTextQuantity = findViewById(R.id.textInputEditQuantity);
        mEditTextRemark = findViewById(R.id.textInputEditRemark);
        mCheckBoxOrder = findViewById(R.id.checkboxOrder);
        mStatusSpinner = findViewById(R.id.searchStatus);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_customer_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem checkable = menu.findItem(R.id.checkable_menu);
        checkable.setChecked(isChecked);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
//        else if (item.getItemId() == R.id.checkable_menu) {
//            isChecked = !item.isChecked();
//            item.setChecked(isChecked);
//            mCustomerArrayList.clear();
//            populateCustomers();
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }


}
