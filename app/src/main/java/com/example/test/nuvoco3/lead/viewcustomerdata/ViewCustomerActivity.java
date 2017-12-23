package com.example.test.nuvoco3.lead.viewcustomerdata;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.test.nuvoco3.R;

public class ViewCustomerActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        recyclerView = findViewById(R.id.recyclerView);

//        CustomerDbHelper dbHelper = new CustomerDbHelper(this);
//        ArrayList<Customer> customers = dbHelper.getDataCustomerMaster();
//        DisplayCustomerAdapter adapter = new DisplayCustomerAdapter(this, customers);
//
//        RecyclerView.LayoutManager reLayoutManager =new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(reLayoutManager);
//        recyclerView.setItemViewCacheSize(100);
//        recyclerView.getItemAnimator();
//        recyclerView.setHasFixedSize(true);
//       recyclerView.setAdapter(adapter);

    }


}
