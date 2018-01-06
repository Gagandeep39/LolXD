package com.example.test.nuvoco3.visits;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.test.nuvoco3.R;
import com.example.test.nuvoco3.helpers.UserInfoHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.test.nuvoco3.helpers.UserInfoHelper.getDateTime;

public class ViewJCPActivity extends AppCompatActivity {

    private static final String TAG = "ViewJCP Activity";
    TextInputEditText mEditTextRepresentative, mEditTextDate;
    String mDate;
    int mYear, mMonth, mDay;
    ImageView mImageSearch;
    RecyclerView mRecyclerView;
    ArrayList<JCP> mJCPArrayList;
    RequestQueue queue;
    boolean isChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_jcp);
        initializeViews();
        initializeVariables();

        mImageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void initializeVariables() {
        queue = Volley.newRequestQueue(this);
        mJCPArrayList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mImageSearch.requestFocus();


    }

    private void initializeViews() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mEditTextRepresentative = findViewById(R.id.editTextRepresentative);
        mEditTextDate = findViewById(R.id.editTextDate);
        mImageSearch = findViewById(R.id.imageViewSearch);
        mRecyclerView = findViewById(R.id.recyclerView);

        mEditTextRepresentative.setText(new UserInfoHelper(this).getUserId());
        mEditTextDate.setText(getDateTime());
    }


    public void datePickerFunction(View v) {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditTextDate.getWindowToken(), 0);
        final View buttonClicked = v;

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {


                        mEditTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date();

                        mDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + " " + dateFormat.format(date);
                        Log.i(TAG, "onDateSet: " + mDate);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

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
