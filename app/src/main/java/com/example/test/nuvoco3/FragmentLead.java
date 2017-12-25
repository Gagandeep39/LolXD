package com.example.test.nuvoco3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.test.nuvoco3.lead.InsertCustomerActivity;
import com.example.test.nuvoco3.lead.InsertCustomerContactActivity;
import com.example.test.nuvoco3.lead.ViewCustomerActivity;
import com.example.test.nuvoco3.lead.ViewCustomerContactActivity;

/**
 * Created by gagandeep on 15/12/17.
 */

public class FragmentLead extends android.support.v4.app.Fragment {
    private static final String TAG = "LEAD FRAGMENT";
    Button button, button2, mButtonContact, mButtonViewContact;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmentlead_layout, container, false);
        initializeViews(v);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), InsertCustomerActivity.class));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ViewCustomerActivity.class));
            }
        });
        mButtonContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), InsertCustomerContactActivity.class));
            }
        });
        mButtonViewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ViewCustomerContactActivity.class));
            }
        });


        return v;
    }

    private void initializeViews(View v) {
        button = v.findViewById(R.id.button);
        button2 = v.findViewById(R.id.button2);
        mButtonContact = v.findViewById(R.id.button3);
        mButtonViewContact = v.findViewById(R.id.button4);
    }


}
