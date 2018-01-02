package com.example.test.nuvoco3.market;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.test.nuvoco3.R;

import static com.example.test.nuvoco3.market.ViewBrandPriceActivity.mBrandPriceArrayList;

public class ViewBrandPriceDetailsActivity extends AppCompatActivity {
    private static final String TAG = "ViewPriceDetails";
    TextView mTextViewCustomer, mTextViewProduct, mTextViewRSP, mTextViewWSP, mTextViewStock, mTextViewDate, mTextViewCreatedBy, mTextViewCreatedOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_price_details);
        initializeViews();
        initializeVariables();

    }

    private void initializeVariables() {
        int mPosition = getIntent().getIntExtra("Position", -1);
        mTextViewCustomer.append(mBrandPriceArrayList.get(mPosition).getCustomer());
        mTextViewProduct.append(mBrandPriceArrayList.get(mPosition).getProduct());
        mTextViewRSP.append(mBrandPriceArrayList.get(mPosition).getRSP());
        mTextViewWSP.append(mBrandPriceArrayList.get(mPosition).getWSP());
        mTextViewStock.append(mBrandPriceArrayList.get(mPosition).getStock());
        mTextViewDate.append(mBrandPriceArrayList.get(mPosition).getDate());
        mTextViewCreatedBy.append(mBrandPriceArrayList.get(mPosition).getCreatedBy());
        mTextViewCreatedOn.append(mBrandPriceArrayList.get(mPosition).getCreatedOn());


    }

    private void initializeViews() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mTextViewCustomer = findViewById(R.id.textViewCustomer);
        mTextViewProduct = findViewById(R.id.textViewProduct);
        mTextViewRSP = findViewById(R.id.textViewRSP);
        mTextViewWSP = findViewById(R.id.textViewWSP);
        mTextViewStock = findViewById(R.id.textViewStock);
        mTextViewDate = findViewById(R.id.textViewDate);
        mTextViewCreatedBy = findViewById(R.id.textViewCreatedBy);
        mTextViewCreatedOn = findViewById(R.id.textViewCreatedOn);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
