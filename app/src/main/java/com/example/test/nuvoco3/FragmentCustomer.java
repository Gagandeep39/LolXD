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

/**
 * Created by gagandeep on 15/12/17.
 */

public class FragmentCustomer extends Fragment {
    Button buttonComplaint;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmenttwo_layout, container, false);
        buttonComplaint = v.findViewById(R.id.buttonComplaint);
        buttonComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ComplaintActivity.class));
            }
        });




        return v;
    }


}
