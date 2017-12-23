package com.example.test.nuvoco3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.test.nuvoco3.customer.ComplaintActivity;
import com.example.test.nuvoco3.customer.ComplaintStatusActivity;

/**
 * Created by gagandeep on 15/12/17.
 */

public class FragmentCustomer extends Fragment {
    Button buttonComplaint, mButton1, mButton3, mButton4;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmentcustomer_layout, container, false);
        buttonComplaint = v.findViewById(R.id.buttonComplaint);
        mButton1 = v.findViewById(R.id.button);
        mButton3 = v.findViewById(R.id.button3);
        mButton4 = v.findViewById(R.id.button4);
        buttonComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ComplaintActivity.class));
            }
        });
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ComplaintStatusActivity.class));
            }
        });




        return v;
    }


}
